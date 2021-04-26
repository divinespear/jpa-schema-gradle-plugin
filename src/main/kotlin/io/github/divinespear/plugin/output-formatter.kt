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
@file:JvmName("JpaSchemaGenerationOutputFormatter")

package io.github.divinespear.plugin

import com.github.vertical_blank.sqlformatter.SqlFormatter
import com.github.vertical_blank.sqlformatter.core.FormatConfig
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

internal fun formatFile(file: Path, format: Boolean, lineSeparator: String) {
  // check readable or format option is disabled
  if (!format || !Files.isReadable(file)) {
    return
  }
  // do format
  val result = Files.newBufferedReader(file, Charsets.UTF_8).readLines().joinToString(lineSeparator)
    .let { format(it, lineSeparator) }
  // write result
  Files.newBufferedWriter(file, Charsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING).use {
    it.write(result)
  }
}

internal fun format(str: String, lineSeparator: String): String =
  SqlFormatter.format(str, FormatConfig.builder().linesBetweenQueries(2).build()).let {
    it.split(lineSeparator).joinToString(lineSeparator) { line -> formatLine(line) }
  }

private val REGEX_TEST_CREATE_INDEX = Regex("^create(\\s+\\S+)?\\s+index", RegexOption.IGNORE_CASE)
private val REGEX_FIX_CREATE_INDEX =
  Regex("^(create(?:\\s+\\S+)?\\s+index\\s+\\S+)(?:\\s+(?:asc|desc))?\\s+(on.*)$", RegexOption.IGNORE_CASE)

internal fun formatLine(str: String): String =
  if (REGEX_TEST_CREATE_INDEX.containsMatchIn(str)) {
    str.replace(REGEX_FIX_CREATE_INDEX, "$1 $2")
  } else {
    str
  }
