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

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static org.junit.Assert.assertThat

class JpaSchemaGenerationPluginSpec extends Specification {

  @Rule
  TemporaryFolder testProjectDir = new TemporaryFolder()
  Project project

  def setup() {
    project = ProjectBuilder.builder().withProjectDir(testProjectDir.root).build()
    project.apply plugin: 'io.github.divinespear.jpa-schema-generate'
  }

  def 'extension should be registered'() {
    expect:
    assertThat(project.generateSchema, instanceOf(JpaSchemaGenerationExtension))
  }

  def 'extension should has multiple targets'() {
    when:
    project.generateSchema {
      targets {
        script {
          scriptAction = "drop-and-create"
          databaseProductName = "H2"
          databaseMajorVersion = 1
          databaseMinorVersion = 3
        }
        database {
        }
      }
    }

    then:
    JpaSchemaGenerationExtension extension = project.generateSchema
    assertThat(extension.targets, hasSize(2))
    JpaSchemaGenerationProperties scriptTarget = extension.targets.find { it.name == "script" }
    assertThat(scriptTarget.databaseProductName, is("H2"))
    assertThat(scriptTarget.databaseMajorVersion, is(1))
    assertThat(scriptTarget.databaseMinorVersion, is(3))
  }

  def 'extension targets should be merged'() {
    when:
    project.generateSchema {
      format = true
      targets {
        a {
          scriptAction = "drop-and-create"
          databaseProductName = "H2"
          databaseMajorVersion = 1
          databaseMinorVersion = 3
        }
        b {
          format = false
          scriptAction = "drop-and-create"
          databaseProductName = "H2"
          databaseMajorVersion = 1
          databaseMinorVersion = 3
        }
      }
    }

    then:
    JpaSchemaGenerationExtension extension = project.generateSchema
    List<JpaSchemaGenerationProperties> targets = extension.targets.collect { extension.extend(it) }
    assertThat(targets.find { it.name == "a" }.format, is(true))
    assertThat(targets.find { it.name == "b" }.format, is(false))
  }

  def 'task should be registered'() {
    expect:
    assertThat(project.tasks, hasItem(instanceOf(JpaSchemaGenerationTask)))
  }
}
