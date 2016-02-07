/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.github.divinespear.gradle.plugin

import groovy.sql.Sql

import java.sql.Types

import nebula.test.IntegrationSpec

class DataNucleusNoXmlSpec
        extends IntegrationSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWork() {
        given:
        buildFile << """

repositories {
    mavenCentral()
}

dependencies {
    compile 'org.datanucleus:javax.persistence:2.1.2'
    compile 'org.datanucleus:datanucleus-accessplatform-jpa-rdbms:4.2.3'
}

classes << {
    description "Enhance JPA model classes using DataNucleus Enhancer"

    // define the entity classes
    def entityFiles = fileTree(sourceSets.main.output.classesDir).matching {
        include 'io/github/divinespear/**/*.class'
    }

    println "Enhancing with DataNucleus the following files"
    entityFiles.getFiles().each {
        println it
    }

    // define Ant task for DataNucleus Enhancer
    ant.taskdef(
        name : 'datanucleusenhancer',
        classpath : sourceSets.main.runtimeClasspath.asPath,
        classname : 'org.datanucleus.enhancer.EnhancerTask'
    )

    // run the DataNucleus Enhancer as an Ant task
    ant.datanucleusenhancer(
        classpath: sourceSets.main.runtimeClasspath.asPath,
        verbose: true,
        api: "JPA") {
        entityFiles.addToAntBuilder(ant, 'fileset', FileCollection.AntType.FileSet)
    }
}

sourceSets {
    main {
        java {
            srcDir file("../../../../src/test/resources/unit/src")
        }
        resources {
            srcDir file("../../../../src/test/resources/unit/resources/datanucleus-noxml")
        }
        output.resourcesDir output.classesDir
    }
}

generateSchema {
    vendor = "datanucleus"
    packageToScan = ["io.github.divinespear"]
    scriptAction = "drop-and-create"
    targets {
        h2database {
            databaseAction = "drop-and-create"
            scriptAction = null
            jdbcDriver = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
            jdbcUser = "sa"
        }
    }
}
"""

        when:
        runTasksSuccessfully "generateSchema"

        then:
        // database
        file("build/generated-schema/test.mv.db").exists()
        def sql = Sql.newInstance("jdbc:h2:" + file("build/generated-schema/test").toString() + ";AUTO_SERVER=TRUE", "sa", null, "org.h2.Driver")
        try {
            sql.eachRow("SELECT * FROM KEY_VALUE_STORE", {
                /* metadata */
                it.getColumnLabel(1) == "STORED_KEY"
                it.getColumnLabel(2) == "STORED_VALUE"
                it.getColumnLabel(3) == "CREATED_AT"
            }, {
                /* data */
            })
            sql.eachRow("SELECT * FROM MANY_COLUMN_TABLE", {
                /* metadata */
                it.getColumnLabel(1) == "ID"
                [Types.BIGINT, Types.DECIMAL].contains(it.getColumnType(1))
                it.getColumnLabel(2) == "COLUMN00"
            }, {
                /* data */
            })
        } finally {
            sql.close()
        }
    }

}
