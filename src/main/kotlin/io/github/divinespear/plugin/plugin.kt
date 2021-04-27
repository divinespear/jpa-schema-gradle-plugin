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
package io.github.divinespear.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaLibraryPlugin
import org.gradle.kotlin.dsl.*

class JpaSchemaGenerationPlugin : Plugin<Project> {

  companion object {
    private const val SPRING_VERSION = "[5.0,6.0)"
    private val SPRING_DEPENDENCIES = listOf(
      "org.springframework:spring-orm:${SPRING_VERSION}",
      "org.springframework:spring-context:${SPRING_VERSION}",
      "org.springframework:spring-aspects:${SPRING_VERSION}"
    )
  }

  override fun apply(project: Project) {
    project.run {
      plugins.apply(JavaLibraryPlugin::class)

      configurations {
        register(CONFIGURATION_NAME)
      }

      dependencies {
        SPRING_DEPENDENCIES.forEach {
          CONFIGURATION_NAME(it)
        }
      }

      extensions.create<JpaSchemaGenerationExtension>(EXTENSION_NAME).apply {
        defaultOutputDirectory = project.buildDir.resolve("generated-schema")
        targets = project.container(JpaSchemaGenerationProperties::class)
      }

      tasks {
        register(PLUGIN_NAME, JpaSchemaGenerationTask::class) {
          group = "help"
          dependsOn(project.tasks.getByPath("classes"))
        }
      }
    }
  }
}
