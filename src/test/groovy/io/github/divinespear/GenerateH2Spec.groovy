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
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT TIMESTAMP, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT NOT NULL, COLUMN00 VARCHAR, COLUMN01 VARCHAR, COLUMN02 VARCHAR, COLUMN03 VARCHAR, COLUMN04 VARCHAR, COLUMN05 VARCHAR, COLUMN06 VARCHAR, COLUMN07 VARCHAR, COLUMN08 VARCHAR, COLUMN09 VARCHAR, COLUMN10 VARCHAR, COLUMN11 VARCHAR, COLUMN12 VARCHAR, COLUMN13 VARCHAR, COLUMN14 VARCHAR, COLUMN15 VARCHAR, COLUMN16 VARCHAR, COLUMN17 VARCHAR, COLUMN18 VARCHAR, COLUMN19 VARCHAR, COLUMN20 VARCHAR, COLUMN21 VARCHAR, COLUMN22 VARCHAR, COLUMN23 VARCHAR, COLUMN24 VARCHAR, COLUMN25 VARCHAR, COLUMN26 VARCHAR, COLUMN27 VARCHAR, COLUMN28 VARCHAR, COLUMN29 VARCHAR, PRIMARY KEY (ID));\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE;\r
DROP TABLE MANY_COLUMN_TABLE;\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
"""
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
    def shouldWorkEclipseLinkFormatted() {
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
                format = true
                scriptAction = "drop-and-create"
                databaseProductName = "H2"
                databaseMajorVersion = 1
                databaseMinorVersion = 3
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (\r
\tSTORED_KEY VARCHAR(128) NOT NULL,\r
\tCREATED_AT TIMESTAMP,\r
\tSTORED_VALUE VARCHAR(32768),\r
\tPRIMARY KEY (STORED_KEY)\r
);\r
\r
CREATE TABLE MANY_COLUMN_TABLE (\r
\tID BIGINT NOT NULL,\r
\tCOLUMN00 VARCHAR,\r
\tCOLUMN01 VARCHAR,\r
\tCOLUMN02 VARCHAR,\r
\tCOLUMN03 VARCHAR,\r
\tCOLUMN04 VARCHAR,\r
\tCOLUMN05 VARCHAR,\r
\tCOLUMN06 VARCHAR,\r
\tCOLUMN07 VARCHAR,\r
\tCOLUMN08 VARCHAR,\r
\tCOLUMN09 VARCHAR,\r
\tCOLUMN10 VARCHAR,\r
\tCOLUMN11 VARCHAR,\r
\tCOLUMN12 VARCHAR,\r
\tCOLUMN13 VARCHAR,\r
\tCOLUMN14 VARCHAR,\r
\tCOLUMN15 VARCHAR,\r
\tCOLUMN16 VARCHAR,\r
\tCOLUMN17 VARCHAR,\r
\tCOLUMN18 VARCHAR,\r
\tCOLUMN19 VARCHAR,\r
\tCOLUMN20 VARCHAR,\r
\tCOLUMN21 VARCHAR,\r
\tCOLUMN22 VARCHAR,\r
\tCOLUMN23 VARCHAR,\r
\tCOLUMN24 VARCHAR,\r
\tCOLUMN25 VARCHAR,\r
\tCOLUMN26 VARCHAR,\r
\tCOLUMN27 VARCHAR,\r
\tCOLUMN28 VARCHAR,\r
\tCOLUMN29 VARCHAR,\r
\tPRIMARY KEY (ID)\r
);\r
\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
\r
"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE;\r
\r
DROP TABLE MANY_COLUMN_TABLE;\r
\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
\r
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
        file("build/generated-schema/create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at timestamp, stored_value varchar(32768), primary key (stored_key));\r
create table many_column_table (id bigint generated by default as identity, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));\r
"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """drop table key_value_store if exists;\r
drop table many_column_table if exists;\r
"""
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

    def shouldWorkHibernateFormatted() {
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
                format = true
                scriptAction = "drop-and-create"
                databaseProductName = "H2"
                databaseMajorVersion = 1
                databaseMinorVersion = 3
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at timestamp,\r
\tstored_value varchar(32768),\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid bigint generated by default as identity,\r
\tcolumn00 varchar(255),\r
\tcolumn01 varchar(255),\r
\tcolumn02 varchar(255),\r
\tcolumn03 varchar(255),\r
\tcolumn04 varchar(255),\r
\tcolumn05 varchar(255),\r
\tcolumn06 varchar(255),\r
\tcolumn07 varchar(255),\r
\tcolumn08 varchar(255),\r
\tcolumn09 varchar(255),\r
\tcolumn10 varchar(255),\r
\tcolumn11 varchar(255),\r
\tcolumn12 varchar(255),\r
\tcolumn13 varchar(255),\r
\tcolumn14 varchar(255),\r
\tcolumn15 varchar(255),\r
\tcolumn16 varchar(255),\r
\tcolumn17 varchar(255),\r
\tcolumn18 varchar(255),\r
\tcolumn19 varchar(255),\r
\tcolumn20 varchar(255),\r
\tcolumn21 varchar(255),\r
\tcolumn22 varchar(255),\r
\tcolumn23 varchar(255),\r
\tcolumn24 varchar(255),\r
\tcolumn25 varchar(255),\r
\tcolumn26 varchar(255),\r
\tcolumn27 varchar(255),\r
\tcolumn28 varchar(255),\r
\tcolumn29 varchar(255),\r
\tprimary key (id)\r
);\r
\r
"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """drop table key_value_store if exists;\r
\r
drop table many_column_table if exists;\r
\r
"""
    }
}
