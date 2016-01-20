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

class GenerateHibernateSpec
        extends IntegrationSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWork() {
        given:
        buildFile << """

sourceSets {
    main {
        java {
            srcDir file("../../../../src/test/resources/unit/hibernate/src")
        }
        resources {
            srcDir file("../../../../src/test/resources/unit/hibernate/resources")
        }
        output.resourcesDir output.classesDir
    }
}

generateSchema {
    scriptAction = "drop-and-create"
    targets {
        h2script {
            databaseProductName = "H2"
            databaseMajorVersion = 1
            databaseMinorVersion = 4
            createOutputFileName = "h2-create.sql"
            dropOutputFileName = "h2-drop.sql"
        }
        h2database {
            databaseAction = "drop-and-create"
            scriptAction = null
            jdbcDriver = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
            jdbcUser = "sa"
        }
        mysql {
            databaseProductName = "MySQL"
            databaseMajorVersion = 4
            databaseMinorVersion = 1
        }
        mysql5 {
            databaseProductName = "MySQL"
            databaseMajorVersion = 5
            databaseMinorVersion = 1
        }
        "mysql-myisam" {
            databaseProductName = "MySQL"
            databaseMajorVersion = 5
            databaseMinorVersion = 1
            properties = [
                "hibernate.dialect": "org.hibernate.dialect.MySQLMyISAMDialect"
            ]
        }
        "mysql-innodb" {
            databaseProductName = "MySQL"
            databaseMajorVersion = 4
            databaseMinorVersion = 1
            properties = [
                "hibernate.dialect": "org.hibernate.dialect.MySQLInnoDBDialect"
            ]
        }
        "mysql5-innodb" {
            databaseProductName = "MySQL"
            databaseMajorVersion = 5
            databaseMinorVersion = 1
            properties = [
                "hibernate.dialect": "org.hibernate.dialect.MySQL5InnoDBDialect"
            ]
        }
        postgres90 {
            databaseProductName = "PostgreSQL"
            databaseMajorVersion = 9
            databaseMinorVersion = 0
        }
        postgres82 {
            databaseProductName = "PostgreSQL"
            databaseMajorVersion = 8
            databaseMinorVersion = 2
        }
        postgres81 {
            databaseProductName = "PostgreSQL"
            databaseMajorVersion = 8
            databaseMinorVersion = 0
        }
        "oracle-11g" {
            databaseProductName = "Oracle"
            databaseMajorVersion = 11
            databaseMinorVersion = 0
        }
        "oracle-10g" {
            databaseProductName = "Oracle"
            databaseMajorVersion = 10
            databaseMinorVersion = 0
        }
        "oracle-9i" {
            databaseProductName = "Oracle"
            databaseMajorVersion = 9
            databaseMinorVersion = 0
        }
        "oracle-8i" {
            databaseProductName = "Oracle"
            databaseMajorVersion = 8
            databaseMinorVersion = 0
        }
        mssql2008 {
            databaseProductName = "Microsoft SQL Server"
            databaseMajorVersion = 10
        }
        mssql2005 {
            databaseProductName = "Microsoft SQL Server"
            databaseMajorVersion = 9
        }
        mssql2003 {
            databaseProductName = "Microsoft SQL Server"
            databaseMajorVersion = 8
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

        // ddl script
        def expect = file("../../../../src/test/resources/hibernate-normal-result.txt").text
        def actual = StringBuilder.newInstance()
        ["h2", "mysql", "mysql5", "mysql-myisam", "mysql-innodb", "mysql5-innodb", "postgres90", "postgres82", "postgres81", "oracle-11g", "oracle-10g", "oracle-9i", "oracle-8i", "mssql2008", "mssql2005", "mssql2003"].each {
            [it + "-create.sql", it + "-drop.sql"].each {
                file("build/generated-schema/" + it).exists()
                actual << "-- " + it + "\n"
                actual << file("build/generated-schema/" + it).text
            }
        }
        expect == actual.toString()
    }
    
    def shouldWorkFormatted() {
        given:
        buildFile << """

sourceSets {
    main {
        java {
            srcDir file("../../../../src/test/resources/unit/hibernate/src")
        }
        resources {
            srcDir file("../../../../src/test/resources/unit/hibernate/resources")
        }
        output.resourcesDir output.classesDir
    }
}

generateSchema {
    format = true
    scriptAction = "drop-and-create"
    targets {
        h2script {
            databaseProductName = "H2"
            databaseMajorVersion = 1
            databaseMinorVersion = 4
            createOutputFileName = "h2-create.sql"
            dropOutputFileName = "h2-drop.sql"
        }
        mysql {
            databaseProductName = "MySQL"
            databaseMajorVersion = 4
            databaseMinorVersion = 1
        }
        mysql5 {
            databaseProductName = "MySQL"
            databaseMajorVersion = 5
            databaseMinorVersion = 1
        }
        "mysql-myisam" {
            databaseProductName = "MySQL"
            databaseMajorVersion = 5
            databaseMinorVersion = 1
            properties = [
                "hibernate.dialect": "org.hibernate.dialect.MySQLMyISAMDialect"
            ] 
        }
        "mysql-innodb" {
            databaseProductName = "MySQL"
            databaseMajorVersion = 4
            databaseMinorVersion = 1
            properties = [
                "hibernate.dialect": "org.hibernate.dialect.MySQLInnoDBDialect"
            ]
        }
        "mysql5-innodb" {
            databaseProductName = "MySQL"
            databaseMajorVersion = 5
            databaseMinorVersion = 1
            properties = [
                "hibernate.dialect": "org.hibernate.dialect.MySQL5InnoDBDialect"
            ]
        }
        postgres90 {
            databaseProductName = "PostgreSQL"
            databaseMajorVersion = 9
            databaseMinorVersion = 0
        }
        postgres82 {
            databaseProductName = "PostgreSQL"
            databaseMajorVersion = 8
            databaseMinorVersion = 2
        }
        postgres81 {
            databaseProductName = "PostgreSQL"
            databaseMajorVersion = 8
            databaseMinorVersion = 0
        }
        "oracle-11g" {
            databaseProductName = "Oracle"
            databaseMajorVersion = 11
            databaseMinorVersion = 0
        }
        "oracle-10g" {
            databaseProductName = "Oracle"
            databaseMajorVersion = 10
            databaseMinorVersion = 0
        }
        "oracle-9i" {
            databaseProductName = "Oracle"
            databaseMajorVersion = 9
            databaseMinorVersion = 0
        }
        "oracle-8i" {
            databaseProductName = "Oracle"
            databaseMajorVersion = 8
            databaseMinorVersion = 0
        }
        mssql2008 {
            databaseProductName = "Microsoft SQL Server"
            databaseMajorVersion = 10
        }
        mssql2005 {
            databaseProductName = "Microsoft SQL Server"
            databaseMajorVersion = 9
        }
        mssql2003 {
            databaseProductName = "Microsoft SQL Server"
            databaseMajorVersion = 8
        }
    }
}
"""
        when:
        runTasksSuccessfully "generateSchema"

        then:
        // ddl script
        def expect = file("../../../../src/test/resources/hibernate-format-result.txt").text
        def actual = StringBuilder.newInstance()
        ["h2", "mysql", "mysql5", "mysql-myisam", "mysql-innodb", "mysql5-innodb", "postgres90", "postgres82", "postgres81", "oracle-11g", "oracle-10g", "oracle-9i", "oracle-8i", "mssql2008", "mssql2005", "mssql2003"].each {
            [it + "-create.sql", it + "-drop.sql"].each {
                file("build/generated-schema/" + it).exists()
                actual << "-- " + it + "\n"
                actual << file("build/generated-schema/" + it).text
            }
        }
        expect == actual.toString()
    }

}
