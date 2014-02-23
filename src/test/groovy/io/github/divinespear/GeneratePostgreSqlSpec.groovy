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

import org.gradle.test.FunctionalSpec

class GeneratePostgreSqlSpec extends FunctionalSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWorkEclipseLink() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/eclipselink/src")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/eclipselink/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                targets {
                    postgresscript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "PostgreSQL"
                        databaseMajorVersion = 9
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT TIMESTAMP, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT NOT NULL, COLUMN00 VARCHAR(255), COLUMN01 VARCHAR(255), COLUMN02 VARCHAR(255), COLUMN03 VARCHAR(255), COLUMN04 VARCHAR(255), COLUMN05 VARCHAR(255), COLUMN06 VARCHAR(255), COLUMN07 VARCHAR(255), COLUMN08 VARCHAR(255), COLUMN09 VARCHAR(255), COLUMN10 VARCHAR(255), COLUMN11 VARCHAR(255), COLUMN12 VARCHAR(255), COLUMN13 VARCHAR(255), COLUMN14 VARCHAR(255), COLUMN15 VARCHAR(255), COLUMN16 VARCHAR(255), COLUMN17 VARCHAR(255), COLUMN18 VARCHAR(255), COLUMN19 VARCHAR(255), COLUMN20 VARCHAR(255), COLUMN21 VARCHAR(255), COLUMN22 VARCHAR(255), COLUMN23 VARCHAR(255), COLUMN24 VARCHAR(255), COLUMN25 VARCHAR(255), COLUMN26 VARCHAR(255), COLUMN27 VARCHAR(255), COLUMN28 VARCHAR(255), COLUMN29 VARCHAR(255), PRIMARY KEY (ID));\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE;\r
DROP TABLE MANY_COLUMN_TABLE CASCADE;\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
"""
    }

    def shouldWorkHibernate() {
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
                namingStrategy = "org.hibernate.cfg.ImprovedNamingStrategy"
                targets {
                    script82above {
                        scriptAction = "drop-and-create"
                        databaseProductName = "PostgreSQL"
                        databaseMajorVersion = 9
                        databaseMinorVersion = 0
                        createOutputFileName = "82-create.sql"
                        dropOutputFileName = "82-drop.sql"
                    }
                    script81below {
                        scriptAction = "drop-and-create"
                        databaseProductName = "PostgreSQL"
                        databaseMajorVersion = 8
                        databaseMinorVersion = 0
                        createOutputFileName = "81-create.sql"
                        dropOutputFileName = "81-drop.sql"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        // script82above
        file("build/generated-schema/82-create.sql").exists()
        file("build/generated-schema/82-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at timestamp, stored_value varchar(32768), primary key (stored_key));\r
create table many_column_table (id int8 not null, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));\r
create sequence hibernate_sequence;\r
"""
        file("build/generated-schema/82-drop.sql").exists()
        file("build/generated-schema/82-drop.sql").text == """drop table if exists key_value_store cascade;\r
drop table if exists many_column_table cascade;\r
drop sequence hibernate_sequence;\r
"""
        // script81below
    }
}
