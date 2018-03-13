package io.github.divinespear.gradle.plugin

import nebula.test.ProjectSpec

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

class JpaSchemaGeneratePluginTest extends ProjectSpec {
//
//    def setup() {
//        project.apply plugin: "jpa-schema-generate"
//    }
//
//    @Test
//    void shouldPluginAddsTaskToProject() {
//        // task
//        assertThat(project.tasks.generateSchema, instanceOf(JpaSchemaGenerateTask))
//        // extensions
//        assertThat(project.generateSchema, instanceOf(Configuration))
//        assertThat(project.generateSchema.skip, is(false))
//        assertThat(project.generateSchema.outputDirectory, is(new File(project.buildDir, "generated-schema")))
//        assertThat(project.generateSchema.targets, emptyIterableOf(Configuration))
//    }
//
//    @Test
//    void shouldConfigGenerateSchemaTargets() {
//        project.generateSchema {
//            targets {
//                script {
//                    scriptAction = "drop-and-create"
//                    databaseProductName = "H2"
//                    databaseMajorVersion = 1
//                    databaseMinorVersion = 3
//                }
//                database {
//                }
//            }
//        }
//
//        assertThat(project.generateSchema.targets.size(), is(2))
//        def schemaTargets = project.tasks.generateSchema.targets
//        assertThat(schemaTargets.size(), is(2))
//    }
//
//
//    static final String linesep = System.properties["line.separator"]?:"\n"
//
//    /* issue 8 */
//
//    @Test
//    void "issue #8 - shouldFormatCreateTable"() {
//        def from = "CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (CREATED_DATE DATETIME NULL,LAST_MODIFIED_DATE DATETIME NULL,RATE NUMERIC(28) NULL,VERSION NUMERIC(19) NOT NULL,REFERENCE_ID VARCHAR(255) NOT NULL,CREATED_BY VARCHAR(36) NULL,LAST_MODIFIED_BY VARCHAR(36) NULL,PRIMARY KEY (VERSION,REFERENCE_ID));"
//        def expected = """CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (
//\tCREATED_DATE DATETIME NULL,
//\tLAST_MODIFIED_DATE DATETIME NULL,
//\tRATE NUMERIC(28) NULL,
//\tVERSION NUMERIC(19) NOT NULL,
//\tREFERENCE_ID VARCHAR(255) NOT NULL,
//\tCREATED_BY VARCHAR(36) NULL,
//\tLAST_MODIFIED_BY VARCHAR(36) NULL,
//\tPRIMARY KEY (VERSION,REFERENCE_ID)
//);"""
//        def task = project.tasks.generateSchema
//        assertThat(task.format(from, linesep), is(expected))
//    }
//
//    @Test
//    void "issue #8 - shouldFormatCreateTableEx"() {
//        def from = "CREATE TEMPORARY TABLE SYSTEM_CURRENCY_RATE_HISTORY (CREATED_DATE DATETIME NULL,LAST_MODIFIED_DATE DATETIME NULL,RATE NUMERIC(28) NULL,VERSION NUMERIC(19) NOT NULL,REFERENCE_ID VARCHAR(255) NOT NULL,CREATED_BY VARCHAR(36) NULL,LAST_MODIFIED_BY VARCHAR(36) NULL,PRIMARY KEY (VERSION,REFERENCE_ID));"
//        def expected = """CREATE TEMPORARY TABLE SYSTEM_CURRENCY_RATE_HISTORY (
//\tCREATED_DATE DATETIME NULL,
//\tLAST_MODIFIED_DATE DATETIME NULL,
//\tRATE NUMERIC(28) NULL,
//\tVERSION NUMERIC(19) NOT NULL,
//\tREFERENCE_ID VARCHAR(255) NOT NULL,
//\tCREATED_BY VARCHAR(36) NULL,
//\tLAST_MODIFIED_BY VARCHAR(36) NULL,
//\tPRIMARY KEY (VERSION,REFERENCE_ID)
//);"""
//        def task = project.tasks.generateSchema
//        assertThat(task.format(from, linesep), is(expected))
//    }
//
//    @Test
//    void "issue #8 - shouldFormatCreateIndex"() {
//        def from = "CREATE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED ON USER_ACCOUNT (ENABLED,DELETED);"
//        def expected = """CREATE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED
//\tON USER_ACCOUNT (ENABLED,DELETED);"""
//        def task = project.tasks.generateSchema
//        assertThat(task.format(from, linesep), is(expected))
//    }
//
//    @Test
//    void "issue #8 - shouldFormatCreateIndexEx"() {
//        def from = "CREATE UNIQUE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED ON USER_ACCOUNT (ENABLED,DELETED);"
//        def expected = """CREATE UNIQUE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED
//\tON USER_ACCOUNT (ENABLED,DELETED);"""
//        def task = project.tasks.generateSchema
//        assertThat(task.format(from, linesep), is(expected))
//    }
//
//    @Test
//    void "issue #8 - shouldFormatAlterTable"() {
//        def from = "ALTER TABLE PRODUCT_CATEGORY ADD CONSTRAINT PRODUCTCATEGORYPRENTID FOREIGN KEY (PARENT_ID) REFERENCES PRODUCT_CATEGORY (ID);"
//        def expected = """ALTER TABLE PRODUCT_CATEGORY
//\tADD CONSTRAINT PRODUCTCATEGORYPRENTID FOREIGN KEY (PARENT_ID)
//\tREFERENCES PRODUCT_CATEGORY (ID);"""
//        def task = project.tasks.generateSchema
//        assertThat(task.format(from, linesep), is(expected))
//    }
//
//    /* issue 9 */
//
//    @Test
//    void "issue #9 - shouldOverrideCreateIndex"() {
//        def from = "CREATE INDEX INDEX_SYSTEM_CURRENCY_RATE_VERSION DESC ON SYSTEM_CURRENCY_RATE (VERSION DESC);"
//        def expected = """CREATE INDEX INDEX_SYSTEM_CURRENCY_RATE_VERSION
//\tON SYSTEM_CURRENCY_RATE (VERSION DESC);"""
//        def task = project.tasks.generateSchema
//        assertThat(task.format(from, linesep), is(expected))
//    }
}
