package io.github.divinespear

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

import org.gradle.test.FunctionalSpec

class GenerateSqlServerSpec extends FunctionalSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWorkSqlServerEclipseLink() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/eclipselink-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                targets {
                    mssqlscript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Microsoft SQL Server"
                        databaseMajorVersion = 10
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT DATETIME NULL, STORED_VALUE VARCHAR(32768) NULL, PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID NUMERIC(19) IDENTITY NOT NULL, COLUMN00 VARCHAR(255) NULL, COLUMN01 VARCHAR(255) NULL, COLUMN02 VARCHAR(255) NULL, COLUMN03 VARCHAR(255) NULL, COLUMN04 VARCHAR(255) NULL, COLUMN05 VARCHAR(255) NULL, COLUMN06 VARCHAR(255) NULL, COLUMN07 VARCHAR(255) NULL, COLUMN08 VARCHAR(255) NULL, COLUMN09 VARCHAR(255) NULL, COLUMN10 VARCHAR(255) NULL, COLUMN11 VARCHAR(255) NULL, COLUMN12 VARCHAR(255) NULL, COLUMN13 VARCHAR(255) NULL, COLUMN14 VARCHAR(255) NULL, COLUMN15 VARCHAR(255) NULL, COLUMN16 VARCHAR(255) NULL, COLUMN17 VARCHAR(255) NULL, COLUMN18 VARCHAR(255) NULL, COLUMN19 VARCHAR(255) NULL, COLUMN20 VARCHAR(255) NULL, COLUMN21 VARCHAR(255) NULL, COLUMN22 VARCHAR(255) NULL, COLUMN23 VARCHAR(255) NULL, COLUMN24 VARCHAR(255) NULL, COLUMN25 VARCHAR(255) NULL, COLUMN26 VARCHAR(255) NULL, COLUMN27 VARCHAR(255) NULL, COLUMN28 VARCHAR(255) NULL, COLUMN29 VARCHAR(255) NULL, PRIMARY KEY (ID));") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE;") > -1
    }

    // Hibernate throws
    // org.hibernate.MappingException: org.hibernate.dialect.SQLServer2008Dialect does not support sequences
}
