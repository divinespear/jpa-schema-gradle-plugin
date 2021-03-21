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
import org.gradle.api.plugins.JavaPlugin

class JpaSchemaGenerationPlugin : Plugin<Project> {

  override fun apply(project: Project) {
    // always enable java plugin
    project.plugins.apply(JavaPlugin::class.java)
    // extension
    project.extensions.create(EXTENSION_NAME, JpaSchemaGenerationExtension::class.java).apply {
      defaultOutputDirectory = project.buildDir.resolve("generated-schema")
      targets = project.container(JpaSchemaGenerationProperties::class.java)
    }
    // task
    project.tasks.create(PLUGIN_NAME, JpaSchemaGenerationTask::class.java) {
      this.dependsOn(project.tasks.getByPath("classes"))
    }
    // dependencies
    val springVersion = "[5.0,6.0)"
    project.configurations.create(CONFIGURATION_NAME)
    project.dependencies.add(CONFIGURATION_NAME, "org.springframework:spring-orm:$springVersion")
    project.dependencies.add(CONFIGURATION_NAME, "org.springframework:spring-context:$springVersion")
    project.dependencies.add(CONFIGURATION_NAME, "org.springframework:spring-aspects:$springVersion")
  }
}
