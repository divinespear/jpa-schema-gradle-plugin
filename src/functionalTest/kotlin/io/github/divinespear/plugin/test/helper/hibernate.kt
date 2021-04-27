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
package io.github.divinespear.plugin.test.helper

import io.github.divinespear.plugin.test.FunctionalSpec
import io.kotest.matchers.and
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.contain
import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.TaskOutcome

internal const val HIBERNATE_5_GROOVY_PROPERTIES =
  """[
    'hibernate.physical_naming_strategy' : 'org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy',
    'hibernate.id.new_generator_mappings': 'false'
  ]"""

internal const val HIBERNATE_5_KOTLIN_PROPERTIES =
  """mapOf(
    "hibernate.physical_naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
    "hibernate.id.new_generator_mappings" to "false"
  )"""

internal const val HIBERNATE_4_GROOVY_PROPERTIES =
  """[
    'hibernate.ejb.naming_strategy': 'org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy'
  ]"""

internal const val HIBERNATE_4_KOTLIN_PROPERTIES =
  """mapOf(
    "hibernate.ejb.naming_strategy" to "org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy"
  )"""

internal fun FunctionalSpec.runHibernateTask(script: String, resultCallback: (BuildResult) -> Unit) {
  buildFile.appendText(script)

  val result = runGenerateSchemaTask()

  result.task(":generateSchema") should {
    it?.outcome shouldBe TaskOutcome.SUCCESS
  }
  resultFileText("build/generated-schema/h2-create.sql") should
      (contain("create table key_value_store") and contain("create table many_column_table"))
  resultFileText("build/generated-schema/h2-drop.sql") should
      (contain("drop table key_value_store") and contain("drop table many_column_table"))

  resultCallback(result)
}

internal fun FunctionalSpec.runHibernateSpringTask(
  hibernateVersion: String,
  script: String,
  resultCallback: (BuildResult) -> Unit
) {
  propertiesFile.appendText("hibernate.version=$hibernateVersion\n")
  runHibernateTask(script, resultCallback)
}
