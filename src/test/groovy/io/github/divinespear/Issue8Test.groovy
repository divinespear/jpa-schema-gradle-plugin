package io.github.divinespear

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test;

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
        def expected = """CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (\r
\tCREATED_DATE DATETIME NULL,\r
\tLAST_MODIFIED_DATE DATETIME NULL,\r
\tRATE NUMERIC(28) NULL,\r
\tVERSION NUMERIC(19) NOT NULL,\r
\tREFERENCE_ID VARCHAR(255) NOT NULL,\r
\tCREATED_BY VARCHAR(36) NULL,\r
\tLAST_MODIFIED_BY VARCHAR(36) NULL,\r
\tPRIMARY KEY (VERSION,REFERENCE_ID)\r
);"""
        def task = getTask()
        assertThat(task.format(from), is(expected))
    }
    
    @Test
    void shouldFormatCreateIndex() {
        def from = "CREATE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED ON USER_ACCOUNT (ENABLED,DELETED);"
        def expected = """CREATE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED\r
\tON USER_ACCOUNT (ENABLED,DELETED);"""
        def task = getTask()
        assertThat(task.format(from), is(expected))
    }
}
