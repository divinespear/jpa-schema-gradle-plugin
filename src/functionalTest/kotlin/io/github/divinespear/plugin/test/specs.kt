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
package io.github.divinespear.plugin.test

import io.kotest.core.spec.style.WordSpec
import io.kotest.core.test.TestStatus
import io.kotest.core.test.TestType
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.File

abstract class FunctionalSpec(private val suffix: String = "gradle") : WordSpec() {
  lateinit var testProjectDir: File
  lateinit var testkitDir: File
  lateinit var propertiesFile: File
  lateinit var settingsFile: File
  lateinit var buildFile: File

  init {
    beforeTest {
      if (it.type !== TestType.Test) {
        return@beforeTest
      }
      // create temporary project root and gradle files
      testProjectDir = createTempDir("test-", "", File("build", "tmp"))
      testkitDir = createTempDir("testkit-", "", testProjectDir)
      propertiesFile = testProjectDir.resolve("gradle.properties").apply {
        writeText("")
      }
      settingsFile = testProjectDir.resolve("settings.${suffix}").apply {
        writeText("rootProject.name = \"${testProjectDir.name}\"\n")
      }
      buildFile = testProjectDir.resolve("build.${suffix}").apply {
        writeText("// Running test for ${testProjectDir.name}\n\n")
      }

      // copy example entities for test
      val mainJavaDir = testProjectDir.resolve("src/main/java").apply {
        mkdirs()
      }
      val resourceJavaDir = File("src/functionalTest/resources/java")
      resourceJavaDir.copyRecursively(mainJavaDir, true)
    }

    afterTest {
      if (it.a.type === TestType.Test) {
        if (it.b.status === TestStatus.Success || it.b.status === TestStatus.Ignored) {
          testProjectDir.deleteRecursively()
        }
      }
    }
  }

  fun runTask(vararg args: String): BuildResult =
    GradleRunner.create()
      .withPluginClasspath()
      .withProjectDir(testProjectDir)
      .withArguments(*args).build()

  fun runGenerateSchemaTask() = runTask("generateSchema", "--info", "--stacktrace")

  fun resultFile(path: String): File = testProjectDir.toPath().resolve(path).toFile()
}

abstract class GroovyFunctionalSpec : FunctionalSpec()

abstract class KotlinFunctionalSpec : FunctionalSpec("gradle.kts")
