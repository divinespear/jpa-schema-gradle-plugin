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

import io.kotest.core.spec.style.FunSpec
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import java.io.File

abstract class FunctionalSpec(private val suffix: String = "gradle") : FunSpec() {
  lateinit var testProjectDir: File
  lateinit var testkitDir: File
  lateinit var propertiesFile: File
  lateinit var settingsFile: File
  lateinit var buildFile: File

  init {
    beforeTest {
      testProjectDir = createTempDir("test-", "", File("build", "tmp"))
      testkitDir = createTempDir("testkit-", "", testProjectDir)
      propertiesFile = testProjectDir.resolve("gradle.properties").apply {
        writeText("")
      }
      settingsFile = testProjectDir.resolve("settings.${suffix}").apply {
        writeText(
          """
          rootProject.name = "${testProjectDir.name}"
        """.trimIndent()
        )
      }
      buildFile = testProjectDir.resolve("build.${suffix}").apply {
        writeText(
          """
          // Running test for ${testProjectDir.name}
        """.trimIndent()
        )
      }
    }

    afterTest {
      testProjectDir.deleteRecursively()
    }
  }

  fun runTask(vararg args: String): BuildResult =
    GradleRunner.create()
      .withPluginClasspath()
      .withProjectDir(testProjectDir)
      .withDebug(true)
      .withArguments(args.toList()).build()

  fun runGenerateSchemaTask() = runTask("generateSchema", "--info", "--stacktrace")

  fun resultFile(path: String): File = testProjectDir.toPath().resolve(path).toFile()
}

abstract class GroovyFunctionalSpec : FunctionalSpec()

abstract class KotlinFunctionalSpec : FunctionalSpec("gradle.kts")
