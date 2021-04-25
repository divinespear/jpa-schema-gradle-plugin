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

internal fun FunctionalSpec.runEclipseTask(script: String, resultCallback: (BuildResult) -> Unit) {
  buildFile.appendText(script);

  val result = runGenerateSchemaTask()

  result.task(":generateSchema") should {
    it?.outcome shouldBe TaskOutcome.SUCCESS
  }
  resultFileText("build/generated-schema/h2-create.sql") should
      (contain("CREATE TABLE KEY_VALUE_STORE") and contain("CREATE TABLE MANY_COLUMN_TABLE"))
  resultFileText("build/generated-schema/h2-drop.sql") should
      (contain("DROP TABLE KEY_VALUE_STORE") and contain("DROP TABLE MANY_COLUMN_TABLE"))

  resultCallback(result)
}
