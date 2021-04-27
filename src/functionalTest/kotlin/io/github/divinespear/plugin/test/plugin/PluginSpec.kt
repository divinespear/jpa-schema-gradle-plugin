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
package io.github.divinespear.plugin.test.plugin

import io.github.divinespear.plugin.JpaSchemaGenerationExtension
import io.github.divinespear.plugin.JpaSchemaGenerationProperties
import io.github.divinespear.plugin.JpaSchemaGenerationTask
import io.kotest.core.spec.style.WordSpec
import io.kotest.inspectors.forAtLeastOne
import io.kotest.matchers.collections.haveSize
import io.kotest.matchers.instanceOf
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.testfixtures.ProjectBuilder
import java.io.File

class PluginSpec : WordSpec() {
  init {
    lateinit var testProjectDir: File
    lateinit var project: Project

    beforeTest {
      testProjectDir = createTempDir("test-", "", File("build", "tmp"))
      project = ProjectBuilder.builder().withProjectDir(testProjectDir).build()
      project.apply {
        plugin("io.github.divinespear.jpa-schema-generate")
      }
    }

    afterTest {
      testProjectDir.deleteRecursively()
    }

    "task" should {
      "be registered" {
        project.tasks.forAtLeastOne {
          it should instanceOf(JpaSchemaGenerationTask::class)
        }
      }
    }

    "extension" should {
      "be registered" {
        val extension = project.extensions["generateSchema"]
        extension should instanceOf(JpaSchemaGenerationExtension::class)
      }

      "has multiple targets" {
        project.configure<JpaSchemaGenerationExtension> {
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

        val task = project.tasks["generateSchema"] as JpaSchemaGenerationTask
        val targets = task.targets()
        // expect 2 targets
        targets should {
          haveSize<JpaSchemaGenerationProperties>(2)
        }
        // expect script target
        targets.forAtLeastOne {
          it.name shouldBe "script"
          it.scriptAction shouldBe "drop-and-create"
          it.databaseProductName shouldBe "H2"
          it.databaseMajorVersion shouldBe 1
          it.databaseMinorVersion shouldBe 3
        }
      }

      "be merged" {
        project.configure<JpaSchemaGenerationExtension> {
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

        val task = project.tasks["generateSchema"] as JpaSchemaGenerationTask
        val targets = task.targets()
        targets.forAtLeastOne {
          it.name shouldBe "a"
          it.format shouldBe true
        }
        targets.forAtLeastOne {
          it.name shouldBe "b"
          it.format shouldBe false
        }
      }
    }
  }
}
