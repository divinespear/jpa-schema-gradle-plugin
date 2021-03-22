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
package test.functional

import io.github.divinespear.plugin.JpaSchemaGenerationExtension
import io.github.divinespear.plugin.JpaSchemaGenerationProperties
import io.github.divinespear.plugin.JpaSchemaGenerationTask
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class PluginTest {
  @Rule
  @JvmField
  val testProjectDir: TemporaryFolder = TemporaryFolder()
  lateinit var project: Project

  @Before
  fun setup() {
    project = ProjectBuilder.builder().withProjectDir(testProjectDir.root).build()
    project.apply {
      plugin("io.github.divinespear.jpa-schema-generate")
    }
  }

  @Test
  fun `task should be registered`() {
    Assert.assertThat(
      project.tasks,
      Matchers.hasItem<JpaSchemaGenerationTask>(Matchers.instanceOf(JpaSchemaGenerationTask::class.java))
    )
  }

  @Test
  fun `extension should be registered`() {
    Assert.assertThat(
      project.extensions["generateSchema"],
      Matchers.instanceOf(JpaSchemaGenerationExtension::class.java)
    )
  }

  @Test
  fun `extension should has multiple targets`() {
    val extension = project.extensions["generateSchema"] as JpaSchemaGenerationExtension

    extension.apply {
      targets {
        create("script") {
          scriptAction = "drop-and-create"
          databaseProductName = "H2"
          databaseMajorVersion = 1
          databaseMinorVersion = 3
        }
        create("database") {}
      }
    }

    // expect 2 targets
    Assert.assertThat(extension.targets, Matchers.hasSize(2))
    // expect script target
    val scriptTarget = extension.targets.find { it.name === "script" }
    Assert.assertThat(scriptTarget, Matchers.notNullValue(JpaSchemaGenerationProperties::class.java))
    Assert.assertThat(scriptTarget?.scriptAction, Matchers.`is`("drop-and-create"))
    Assert.assertThat(scriptTarget?.databaseProductName, Matchers.`is`("H2"))
    Assert.assertThat(scriptTarget?.databaseMajorVersion, Matchers.`is`(1))
    Assert.assertThat(scriptTarget?.databaseMinorVersion, Matchers.`is`(3))
  }

  @Test
  fun `extension targets should be merged`() {
    val extension = project.extensions["generateSchema"] as JpaSchemaGenerationExtension

    extension.apply {
      format = true
      targets {
        create("a") {
          scriptAction = "drop-and-create"
          databaseProductName = "H2"
          databaseMajorVersion = 1
          databaseMinorVersion = 3
        }
        create("b") {
          format = false
          scriptAction = "drop-and-create"
          databaseProductName = "H2"
          databaseMajorVersion = 1
          databaseMinorVersion = 3
        }
      }
    }

    val targets = extension.targets.map { extension.extend(it) }
    Assert.assertThat(targets.find { it.name === "a" }?.format, Matchers.`is`(true))
    Assert.assertThat(targets.find { it.name === "b" }?.format, Matchers.`is`(false))
  }
}
