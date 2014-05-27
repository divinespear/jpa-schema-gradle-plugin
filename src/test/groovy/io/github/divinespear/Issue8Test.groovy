package io.github.divinespear

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class Issue8Test {

    JpaSchemaGenerateTask getTask() {
        def project = ProjectBuilder.builder().build()
        project.apply plugin: "jpa-schema-generate"

        assertThat(project.tasks.generateSchema, instanceOf(JpaSchemaGenerateTask))
        return project.tasks.generateSchema;
    }

    @Test
    void shouldFormatCreateTable() {
        def from = "CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (CREATED_DATE DATETIME NULL,LAST_MODIFIED_DATE DATETIME NULL,RATE NUMERIC(28) NULL,VERSION NUMERIC(19) NOT NULL,REFERENCE_ID VARCHAR(255) NOT NULL,CREATED_BY VARCHAR(36) NULL,LAST_MODIFIED_BY VARCHAR(36) NULL,PRIMARY KEY (VERSION,REFERENCE_ID));"
        def expected = """CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (
\tCREATED_DATE DATETIME NULL,
\tLAST_MODIFIED_DATE DATETIME NULL,
\tRATE NUMERIC(28) NULL,
\tVERSION NUMERIC(19) NOT NULL,
\tREFERENCE_ID VARCHAR(255) NOT NULL,
\tCREATED_BY VARCHAR(36) NULL,
\tLAST_MODIFIED_BY VARCHAR(36) NULL,
\tPRIMARY KEY (VERSION,REFERENCE_ID)
);"""
        def task = getTask()
        assertThat(task.format(from, System.lineSeparator()), is(expected))
    }

    @Test
    void shouldFormatCreateTableEx() {
        def from = "CREATE TEMPORARY TABLE SYSTEM_CURRENCY_RATE_HISTORY (CREATED_DATE DATETIME NULL,LAST_MODIFIED_DATE DATETIME NULL,RATE NUMERIC(28) NULL,VERSION NUMERIC(19) NOT NULL,REFERENCE_ID VARCHAR(255) NOT NULL,CREATED_BY VARCHAR(36) NULL,LAST_MODIFIED_BY VARCHAR(36) NULL,PRIMARY KEY (VERSION,REFERENCE_ID));"
        def expected = """CREATE TEMPORARY TABLE SYSTEM_CURRENCY_RATE_HISTORY (
\tCREATED_DATE DATETIME NULL,
\tLAST_MODIFIED_DATE DATETIME NULL,
\tRATE NUMERIC(28) NULL,
\tVERSION NUMERIC(19) NOT NULL,
\tREFERENCE_ID VARCHAR(255) NOT NULL,
\tCREATED_BY VARCHAR(36) NULL,
\tLAST_MODIFIED_BY VARCHAR(36) NULL,
\tPRIMARY KEY (VERSION,REFERENCE_ID)
);"""
        def task = getTask()
        assertThat(task.format(from, System.lineSeparator()), is(expected))
    }

    @Test
    void shouldFormatCreateIndex() {
        def from = "CREATE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED ON USER_ACCOUNT (ENABLED,DELETED);"
        def expected = """CREATE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED
\tON USER_ACCOUNT (ENABLED,DELETED);"""
        def task = getTask()
        assertThat(task.format(from, System.lineSeparator()), is(expected))
    }

    @Test
    void shouldFormatCreateIndexEx() {
        def from = "CREATE UNIQUE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED ON USER_ACCOUNT (ENABLED,DELETED);"
        def expected = """CREATE UNIQUE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED
\tON USER_ACCOUNT (ENABLED,DELETED);"""
        def task = getTask()
        assertThat(task.format(from, System.lineSeparator()), is(expected))
    }

    @Test
    void shouldFormatAlterTable() {
        def from = "ALTER TABLE PRODUCT_CATEGORY ADD CONSTRAINT PRODUCTCATEGORYPRENTID FOREIGN KEY (PARENT_ID) REFERENCES PRODUCT_CATEGORY (ID);"
        def expected = """ALTER TABLE PRODUCT_CATEGORY
\tADD CONSTRAINT PRODUCTCATEGORYPRENTID FOREIGN KEY (PARENT_ID)
\tREFERENCES PRODUCT_CATEGORY (ID);"""
        def task = getTask()
        assertThat(task.format(from, System.lineSeparator()), is(expected))
    }
}
