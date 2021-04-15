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
import io.kotest.matchers.and
import io.kotest.matchers.file.exist
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.contain
import org.gradle.testkit.runner.TaskOutcome
import java.io.File

class Issue29Kotlin : KotlinFunctionalSpec() {

  private fun script(version: String) = """
    plugins {
      `java-library`
      `kotlin-dsl`
      id("io.github.divinespear.jpa-schema-generate")
      id("io.spring.dependency-management") version ("1.0.10.RELEASE")
      id("org.springframework.boot") version ("$version")
    }
    
    repositories {
      mavenCentral()
    }
    
    dependencies {
      implementation("org.springframework.boot:spring-boot-starter-data-jpa")
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
      targets {
        create("h2script") {
          databaseProductName = "H2"
          databaseMajorVersion = 1
          databaseMinorVersion = 4
          createOutputFileName = "h2-create.sql"
          dropOutputFileName = "h2-drop.sql"
        }
      }
    }
    """.trimIndent()

  init {
    beforeTest {
      val mainJavaDir = testProjectDir.resolve("src/main/java").apply {
        mkdirs()
      }
      val resourceJavaDir = File("src/functionalTest/resources/java")
      resourceJavaDir.copyRecursively(mainJavaDir, true)
    }

    "spring-boot 1.5.10" should {
      "have spring dependencies" {
        buildFile.writeText(script("1.5.10.RELEASE"))

        val result = runGenerateSchemaTask()
        result.task(":generateSchema") should {
          it?.outcome shouldBe TaskOutcome.SUCCESS
        }
        result.output should (contain("org.springframework/spring-aspects/") and contain("org.springframework/spring-context/"))
        resultFile("build/generated-schema/h2-create.sql") should {
          exist()
          it.readText() should (contain("create table key_value_store") and contain("create table many_column_table"))
        }
        resultFile("build/generated-schema/h2-drop.sql") should {
          exist()
          it.readText() should (contain("drop table key_value_store") and contain("drop table many_column_table"))
        }
      }
    }

    "spring-boot 2.0.0" should {
      "have spring dependencies" {
        buildFile.writeText(script("2.0.0.RELEASE"))

        val result = runGenerateSchemaTask()
        result.task(":generateSchema").should {
          it?.outcome shouldBe TaskOutcome.SUCCESS
        }
        result.output should (contain("org.springframework/spring-aspects/") and contain("org.springframework/spring-context/"))
        resultFile("build/generated-schema/h2-create.sql") should {
          exist()
          it.readText() should (contain("create table key_value_store") and contain("create table many_column_table"))
        }
        resultFile("build/generated-schema/h2-drop.sql") should {
          exist()
          it.readText() should (contain("drop table key_value_store") and contain("drop table many_column_table"))
        }
      }
    }
  }
}
