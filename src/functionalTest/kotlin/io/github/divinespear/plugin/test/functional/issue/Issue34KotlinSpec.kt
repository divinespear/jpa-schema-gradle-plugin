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
package io.github.divinespear.plugin.test.functional.issue

import io.github.divinespear.plugin.test.KotlinFunctionalSpec
import io.kotest.core.test.TestType
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.gradle.testkit.runner.TaskOutcome

class Issue34KotlinSpec : KotlinFunctionalSpec() {

  init {
    beforeTest {
      if (it.type === TestType.Test) {
        buildFile.appendText(
          """
            
            plugins {
              id("io.github.divinespear.jpa-schema-generate")
            }
            
            repositories {
              mavenCentral()
            }
            
          """.trimIndent()
        )
      }
    }


    "task" should {
      "work with default target" {
        buildFile.appendText(
          """
            
            dependencies {
              implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.0.0.RELEASE")
              runtimeOnly("com.h2database:h2:1.4.191")
              runtimeOnly(fileTree(projectDir.relativeTo(file("lib"))) { include("*.jar") })
            }
            
            generateSchema {
              vendor = "hibernate"
              packageToScan = setOf("io.github.divinespear.model")
              scriptAction = "drop-and-create"
              properties = mapOf(
                "hibernate.physical_naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
                "hibernate.id.new_generator_mappings" to "false"
              )
              databaseProductName = "H2"
              databaseMajorVersion = 1
              databaseMinorVersion = 4
              createOutputFileName = "h2-create.sql"
              dropOutputFileName = "h2-drop.sql"
            }
          """.trimIndent()
        )

        val result = runGenerateSchemaTask()
        result.task(":generateSchema") should {
          it?.outcome shouldBe TaskOutcome.SUCCESS
        }
      }
    }
  }
}
