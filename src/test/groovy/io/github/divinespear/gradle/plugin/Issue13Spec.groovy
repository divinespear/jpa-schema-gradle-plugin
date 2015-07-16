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

class Issue13Spec extends IntegrationSpec {

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
                        srcDir file("../../../../src/test/resources/unit/issue13/resources")
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
        runTasks "generateSchema"
        then:
        file("build/generated-schema/h2script-create.sql").exists()
        file("build/generated-schema/h2script-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT TIMESTAMP, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));
CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT NOT NULL, COLUMN00 VARCHAR, COLUMN01 VARCHAR, COLUMN02 VARCHAR, COLUMN03 VARCHAR, COLUMN04 VARCHAR, COLUMN05 VARCHAR, COLUMN06 VARCHAR, COLUMN07 VARCHAR, COLUMN08 VARCHAR, COLUMN09 VARCHAR, COLUMN10 VARCHAR, COLUMN11 VARCHAR, COLUMN12 VARCHAR, COLUMN13 VARCHAR, COLUMN14 VARCHAR, COLUMN15 VARCHAR, COLUMN16 VARCHAR, COLUMN17 VARCHAR, COLUMN18 VARCHAR, COLUMN19 VARCHAR, COLUMN20 VARCHAR, COLUMN21 VARCHAR, COLUMN22 VARCHAR, COLUMN23 VARCHAR, COLUMN24 VARCHAR, COLUMN25 VARCHAR, COLUMN26 VARCHAR, COLUMN27 VARCHAR, COLUMN28 VARCHAR, COLUMN29 VARCHAR, PRIMARY KEY (ID));
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;
"""
        file("build/generated-schema/h2script-drop.sql").exists()
        file("build/generated-schema/h2script-drop.sql").text == """DROP TABLE KEY_VALUE_STORE;
DROP TABLE MANY_COLUMN_TABLE;
DROP SEQUENCE SEQ_GEN_SEQUENCE;
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
}
