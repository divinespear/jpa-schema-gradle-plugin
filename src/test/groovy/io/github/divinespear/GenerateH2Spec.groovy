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

class GenerateH2Spec extends FunctionalSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWorkH2EclipseLink() {
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
                    h2script {
                        scriptAction = "drop-and-create"
                        databaseProductName = "H2"
                        databaseMajorVersion = 1
                        databaseMinorVersion = 3
                    }
                    h2database {
                        databaseAction = "drop-and-create"
                        jdbcDriver = "org.h2.Driver"
                        jdbcUrl = "jdbc:h2:nio:\${buildDir}/generated-schema/test"
                        jdbcUser = "sa"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT TIMESTAMP, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT NOT NULL, COLUMN00 VARCHAR, COLUMN01 VARCHAR, COLUMN02 VARCHAR, COLUMN03 VARCHAR, COLUMN04 VARCHAR, COLUMN05 VARCHAR, COLUMN06 VARCHAR, COLUMN07 VARCHAR, COLUMN08 VARCHAR, COLUMN09 VARCHAR, COLUMN10 VARCHAR, COLUMN11 VARCHAR, COLUMN12 VARCHAR, COLUMN13 VARCHAR, COLUMN14 VARCHAR, COLUMN15 VARCHAR, COLUMN16 VARCHAR, COLUMN17 VARCHAR, COLUMN18 VARCHAR, COLUMN19 VARCHAR, COLUMN20 VARCHAR, COLUMN21 VARCHAR, COLUMN22 VARCHAR, COLUMN23 VARCHAR, COLUMN24 VARCHAR, COLUMN25 VARCHAR, COLUMN26 VARCHAR, COLUMN27 VARCHAR, COLUMN28 VARCHAR, COLUMN29 VARCHAR, PRIMARY KEY (ID));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP SEQUENCE SEQ_GEN_SEQUENCE;") > -1
        file("build/generated-schema/test.h2.db").exists()
        def sql = Sql.newInstance("jdbc:h2:nio:" + file("build/generated-schema/test").toString(), "sa", null, "org.h2.Driver")
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

    def shouldWorkH2Hibernate() {
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
                    h2script {
                        scriptAction = "drop-and-create"
                        databaseProductName = "H2"
                        databaseMajorVersion = 1
                        databaseMinorVersion = 3
                    }
                    h2database {
                        databaseAction = "drop-and-create"
                        jdbcDriver = "org.h2.Driver"
                        jdbcUrl = "jdbc:h2:nio:\${buildDir}/generated-schema/test"
                        jdbcUser = "sa"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("create table key_value_store (stored_key varchar(128) not null, created_at timestamp, stored_value varchar(32768), primary key (stored_key));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create table many_column_table (id bigint generated by default as identity, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("drop table key_value_store if exists;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop table many_column_table if exists;") > -1
        file("build/generated-schema/test.h2.db").exists()
        def sql = Sql.newInstance("jdbc:h2:nio:" + file("build/generated-schema/test").toString(), "sa", null, "org.h2.Driver")
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
