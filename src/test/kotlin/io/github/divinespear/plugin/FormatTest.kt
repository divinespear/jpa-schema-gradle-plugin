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

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe

class FormatTest : WordSpec() {

  companion object {
    val LINE_SEPARATOR = System.getProperty("line.separator") ?: "\n";
  }

  init {
    "create table" should {
      "format well" {
        val source =
          "CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (CREATED_DATE DATETIME NULL,LAST_MODIFIED_DATE DATETIME NULL,RATE NUMERIC(28) NULL,VERSION NUMERIC(19) NOT NULL,REFERENCE_ID VARCHAR(255) NOT NULL,CREATED_BY VARCHAR(36) NULL,LAST_MODIFIED_BY VARCHAR(36) NULL,PRIMARY KEY (VERSION,REFERENCE_ID));";
        val expected = """CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (
${"\t"}CREATED_DATE DATETIME NULL,
${"\t"}LAST_MODIFIED_DATE DATETIME NULL,
${"\t"}RATE NUMERIC(28) NULL,
${"\t"}VERSION NUMERIC(19) NOT NULL,
${"\t"}REFERENCE_ID VARCHAR(255) NOT NULL,
${"\t"}CREATED_BY VARCHAR(36) NULL,
${"\t"}LAST_MODIFIED_BY VARCHAR(36) NULL,
${"\t"}PRIMARY KEY (VERSION,REFERENCE_ID)
);""".trimIndent()

        val actual = formatLine(source, LINE_SEPARATOR)
        actual shouldBe expected
      }
      "format well with options" {
        val source =
          "CREATE TEMPORARY TABLE SYSTEM_CURRENCY_RATE_HISTORY (CREATED_DATE DATETIME NULL,LAST_MODIFIED_DATE DATETIME NULL,RATE NUMERIC(28) NULL,VERSION NUMERIC(19) NOT NULL,REFERENCE_ID VARCHAR(255) NOT NULL,CREATED_BY VARCHAR(36) NULL,LAST_MODIFIED_BY VARCHAR(36) NULL,PRIMARY KEY (VERSION,REFERENCE_ID));"
        val expected = """CREATE TEMPORARY TABLE SYSTEM_CURRENCY_RATE_HISTORY (
${"\t"}CREATED_DATE DATETIME NULL,
${"\t"}LAST_MODIFIED_DATE DATETIME NULL,
${"\t"}RATE NUMERIC(28) NULL,
${"\t"}VERSION NUMERIC(19) NOT NULL,
${"\t"}REFERENCE_ID VARCHAR(255) NOT NULL,
${"\t"}CREATED_BY VARCHAR(36) NULL,
${"\t"}LAST_MODIFIED_BY VARCHAR(36) NULL,
${"\t"}PRIMARY KEY (VERSION,REFERENCE_ID)
);""".trimIndent()

        val actual = formatLine(source, LINE_SEPARATOR)
        actual shouldBe expected
      }
    }
  }
}
