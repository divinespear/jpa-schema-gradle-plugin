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

import static org.hamcrest.Matchers.*
import static org.junit.Assert.*

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.tooling.GradleConnector
import org.junit.Test

class JpaSchemaGeneratePluginTest {

    @Test
    void shouldPluginAddsTaskToProject() {
        def project = ProjectBuilder.builder().build()
        project.apply plugin: "jpa-schema-generate"

        // task
        assertThat(project.tasks.generateSchema, instanceOf(JpaSchemaGenerateTask))
        // extensions
        assertThat(project.generateSchema, instanceOf(SchemaGenerationConfig))
        assertThat(project.generateSchema.skip, is(false))
        assertThat(project.generateSchema.outputDirectory, is(new File(project.buildDir, "generated-schema")))
    }

    @Test
    void shouldGenerateScriptUsingEclipselink() {
        def testProjectHome = new File("build/test-classes/unit/eclipselink-simple-test")
        
        // execute plugin
        def connector = GradleConnector.newConnector()
        def connection = connector.forProjectDirectory(testProjectHome).connect()
        try {
            connection.newBuild()
                    .forTasks("generateSchema")
                    .withArguments("-PpluginVersion=" + System.getProperty("pluginVersion"))
                    .run()
        } finally {
            connection.close()
        }
        
        // output check
    }
}
