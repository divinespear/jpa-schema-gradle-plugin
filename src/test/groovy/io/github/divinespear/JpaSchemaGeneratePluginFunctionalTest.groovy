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

class JpaSchemaGeneratePluginFunctionalTest extends FunctionalSpec {
    
    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }
        
    def shouldWorkBasicEclipseLinkGeneration() {
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
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT NOT NULL, COLUMN00 VARCHAR, COLUMN01 VARCHAR, COLUMN02 VARCHAR, COLUMN03 VARCHAR, COLUMN04 VARCHAR, COLUMN05 VARCHAR, COLUMN06 VARCHAR, COLUMN07 VARCHAR, COLUMN08 VARCHAR, COLUMN09 VARCHAR, COLUMN10 VARCHAR, COLUMN11 VARCHAR, COLUMN12 VARCHAR, COLUMN13 VARCHAR, COLUMN14 VARCHAR, COLUMN15 VARCHAR, COLUMN16 VARCHAR, COLUMN17 VARCHAR, COLUMN18 VARCHAR, COLUMN19 VARCHAR, COLUMN20 VARCHAR, COLUMN21 VARCHAR, COLUMN22 VARCHAR, COLUMN23 VARCHAR, COLUMN24 VARCHAR, COLUMN25 VARCHAR, COLUMN26 VARCHAR, COLUMN27 VARCHAR, COLUMN28 VARCHAR, COLUMN29 VARCHAR, PRIMARY KEY (ID));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP SEQUENCE SEQ_GEN_SEQUENCE") > -1
        file("build/generated-schema/test.h2.db").exists()
    }
}
