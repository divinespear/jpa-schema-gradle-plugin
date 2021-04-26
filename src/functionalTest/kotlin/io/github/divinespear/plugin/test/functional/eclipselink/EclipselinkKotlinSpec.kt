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
package io.github.divinespear.plugin.test.functional.eclipselink

import io.github.divinespear.plugin.test.KotlinFunctionalSpec
import io.github.divinespear.plugin.test.helper.runEclipseTask
import io.kotest.matchers.should
import io.kotest.matchers.string.contain
import org.apache.tools.ant.util.JavaEnvUtils

class EclipselinkKotlinSpec : KotlinFunctionalSpec() {

  private fun script(version: String) =
    """

      plugins {
        id("io.github.divinespear.jpa-schema-generate")
      }
      
      repositories {
        mavenCentral()
      }
      
      dependencies {
        compile("org.eclipse.persistence:org.eclipse.persistence.jpa:$version")
        compile("org.springframework.boot:spring-boot:1.5.10.RELEASE")
        runtime("com.h2database:h2:1.4.191")
        runtimeOnly(fileTree(projectDir.relativeTo(file("lib"))) { include("*.jar") })
      }
      
      generateSchema {
        vendor = "eclipselink"
        packageToScan = setOf("io.github.divinespear.model")
        scriptAction = "drop-and-create"
        targets {
          create("h2script") {
            databaseProductName = "H2"
            databaseMajorVersion = 1
            databaseMinorVersion = 4
            createOutputFileName = "h2-create.sql"
            dropOutputFileName = "h2-drop.sql"
          }
          create("h2database") {
            databaseAction = "drop-and-create"
            scriptAction = null
            jdbcDriver = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:${'$'}{buildDir}/generated-schema/test"
            jdbcUser = "sa"
          }
        }
      }
    """.trimIndent()

  init {
    "task without persistence.xml" should {
      "work on eclipselink 2.7" {
        runEclipseTask(script("[2.7,2.8)")) {
          it.output should contain("org.eclipse.persistence/org.eclipse.persistence.jpa/2.7.")
        }
      }

      "work on eclipselink 2.6".config(enabledIf = {
        !JavaEnvUtils.isAtLeastJavaVersion("12")
      }) {
        runEclipseTask(script("[2.6,2.7)")) {
          it.output should contain("org.eclipse.persistence/org.eclipse.persistence.jpa/2.6.")
        }
      }

      "work on eclipselink 2.5" {
        runEclipseTask(script("[2.5,2.6)")) {
          it.output should contain("org.eclipse.persistence/org.eclipse.persistence.jpa/2.5.")
        }
      }
    }
  }
}
