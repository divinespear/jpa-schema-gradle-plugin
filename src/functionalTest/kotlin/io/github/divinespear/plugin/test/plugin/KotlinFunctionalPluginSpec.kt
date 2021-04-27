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

import io.github.divinespear.plugin.test.KotlinFunctionalSpec
import io.kotest.matchers.should
import io.kotest.matchers.string.contain

class KotlinFunctionalPluginSpec : KotlinFunctionalSpec() {
  init {
    "task" should {
      "be registered" {
        buildFile.writeText(
          """
        plugins {
          id("io.github.divinespear.jpa-schema-generate")
          id("org.springframework.boot") version ("1.5.10.RELEASE")
        }

        repositories {
          mavenCentral()
        }
        """.trimIndent()
        )

        val result = runTask("tasks")
        result.output should contain("generateSchema")
      }
    }

    "task and extension" should {
      "appears in kotlinDslAccessorsReport" {
        buildFile.writeText(
          """
        plugins {
          `kotlin-dsl`
          id("io.github.divinespear.jpa-schema-generate")
          id("org.springframework.boot") version ("1.5.10.RELEASE")
        }

        repositories {
          mavenCentral()
        }
        """.trimIndent()
        )

        val result = runTask("kotlinDslAccessorsReport")
        result.output should (contain("generateSchema") and
            contain("JpaSchemaGenerationExtension") and
            contain("JpaSchemaGenerationTask"))
      }
    }
  }
}
