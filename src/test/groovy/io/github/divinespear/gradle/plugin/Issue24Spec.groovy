package io.github.divinespear.gradle.plugin

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

import groovy.sql.Sql

import java.sql.Types

import nebula.test.IntegrationSpec

class Issue24Spec extends IntegrationSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWorkHibernate() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/resources/issue-24")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                skip = false
                format = true
                scanTestClasses = false
                persistenceUnitName = "PostgreSqlDS"
                databaseAction = "none"
                scriptAction = "drop-and-create"
                outputDirectory = new File(projectDir.absolutePath + '/build/classes/main/META-INF', 'sql')
                createOutputFileName = "create.sql"
                dropOutputFileName = "drop.sql"
                createSourceMode = "metadata"
                dropSourceMode = "metadata"
                databaseProductName = "PostgreSQL"
                databaseMajorVersion = 9
                databaseMinorVersion = 5
                properties = [
                    "hibernate.namingStrategy": "org.hibernate.cfg.ImprovedNamingStrategy"
                ]
            }
        """
        when:
        runTasks "generateSchema"
        then:
        file("build/classes/main/META-INF/sql/create.sql").exists()
        file("build/classes/main/META-INF/sql/drop.sql").exists()
    }
}
