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

import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import java.nio.file.FileVisitOption
import java.nio.file.Files

abstract class IntegrationSpec extends Specification {

  @Rule
  TemporaryFolder testProjectDir = new TemporaryFolder(new File("build", "tmp"))
  String projectName
  File buildFile
  File settingsFile

  def setup() {
    projectName = testProjectDir.root.name

    settingsFile = testProjectDir.newFile("settings.gradle")
    settingsFile << "rootProject.name = '${projectName}'"

    buildFile = testProjectDir.newFile("build.gradle")
    buildFile << "// Running test for ${projectName}\n"
  }
}
