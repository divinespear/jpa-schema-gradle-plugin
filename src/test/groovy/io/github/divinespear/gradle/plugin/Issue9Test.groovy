package io.github.divinespear.gradle.plugin

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*
import nebula.test.ProjectSpec

import org.junit.Test

class Issue9Test extends ProjectSpec {

    static final String linesep = System.properties["line.separator"]?:"\n"

    JpaSchemaGenerateTask getTask() {
        project.apply plugin: "jpa-schema-generate"

        assertThat(project.tasks.generateSchema, instanceOf(JpaSchemaGenerateTask))
        return project.tasks.generateSchema;
    }

    @Test
    void shouldOverrideCreateIndex() {
        def from = "CREATE INDEX INDEX_SYSTEM_CURRENCY_RATE_VERSION DESC ON SYSTEM_CURRENCY_RATE (VERSION DESC);"
        def expected = """CREATE INDEX INDEX_SYSTEM_CURRENCY_RATE_VERSION
\tON SYSTEM_CURRENCY_RATE (VERSION DESC);"""
        def task = getTask()
        assertThat(task.format(from, linesep), is(expected))
    }
}
