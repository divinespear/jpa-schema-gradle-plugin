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

package io.github.divinespear.test

import org.gradle.testkit.runner.GradleRunner
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

abstract class IntegrationSpec extends Specification {

  @Rule
  TemporaryFolder testProjectDir = new TemporaryFolder(new File("build", "tmp"))
  String projectName
  File buildFile
  File settingsFile
  File propertiesFile

  def setup() {
    projectName = testProjectDir.root.name
    propertiesFile = testProjectDir.newFile("gradle.properties")
    settingsFile = setupSettingsFile()
    buildFile = setupBuildFile()
  }

  abstract File setupSettingsFile()
  abstract File setupBuildFile()

  def runSchemaGenerationTask() {
    GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info", "--stacktrace").withPluginClasspath().withDebug(true).build()
  }

  def getResultFile(String dir) {
    testProjectDir.root.toPath().resolve(dir).toFile()
  }
}
