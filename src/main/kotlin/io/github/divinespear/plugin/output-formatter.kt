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

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardOpenOption

internal fun formatFile(file: Path, format: Boolean, lineSeparator: String) {
  // check readable
  if (!Files.isReadable(file)) {
    return
  }
  // do format
  val result = Files.newBufferedReader(file, Charsets.UTF_8).readLines().joinToString(lineSeparator) {
    buildString {
      val result = if (format) formatLine(it, lineSeparator) else it
      append(result)
      if (!result.endsWith(";")) append(";")
      if (format) append(lineSeparator)
    }
  }
  // write result
  Files.newBufferedWriter(file, Charsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING).use {
    it.write(result)
  }
}

private val REGEX_TEST_CREATE_TABLE = Regex("^create(\\s+\\S+)?\\s+(?:table|view)", RegexOption.IGNORE_CASE)
private val REGEX_TEST_CREATE_INDEX = Regex("^create(\\s+\\S+)?\\s+index", RegexOption.IGNORE_CASE)
private val REGEX_TEST_ALTER_TABLE = Regex("^alter\\s+table", RegexOption.IGNORE_CASE)

internal fun formatLine(str: String, lineSeparator: String): String {
  val aligned = str.replace(Regex("^([^(]+\\()"), "\$1\r\n\t")
      .replace(Regex("\\)[^()]*\$"), "\r\n\$0")
      .replace(Regex("((?:[^(),\\s]+|\\S\\([^)]+\\)[^),]*),)\\s*"), "\$1\r\n\t")
  val result = when {
    REGEX_TEST_CREATE_TABLE.containsMatchIn(aligned) -> buildString {
      var completed = true
      aligned.split("\r\n").forEach {
        if (Regex("^\\S").containsMatchIn(it)) {
          if (!completed) append(lineSeparator)
          append(it)
          append(lineSeparator)
        } else if (completed) {
          if (Regex("^\\s*[^(]+(?:[^(),\\s]+|\\S\\([^)]+\\)[^),]*),\\s*\$").containsMatchIn(it)) {
            append(it)
            append(lineSeparator)
          } else {
            append(it)
            completed = false
          }
        } else {
          append(it.trim())
          if (Regex("[^)]+\\).*\$").containsMatchIn(it)) {
            append(lineSeparator)
            completed = true
          }
        }
      }
    }
    REGEX_TEST_CREATE_INDEX.containsMatchIn(aligned) -> buildString {
      var completed = true
      aligned.replace(Regex("^(create(\\s+\\S+)?\\s+index\\s+\\S+)\\s*", RegexOption.IGNORE_CASE), "\$1\r\n\t").split("\r\n").forEach {
        if (this.isEmpty()) {
          append(it)
          append(lineSeparator)
        } else if (completed) {
          if (Regex("^\\s*[^(]+(?:[^(),\\s]+|\\S\\([^)]+\\)[^),]*),\\s*\$").containsMatchIn(it)) {
            append(it)
            append(lineSeparator)
          } else {
            append(it)
            completed = false
          }
        } else {
          append(it.trim())
          if (Regex("[^)]+\\).*\$").containsMatchIn(it)) {
            append(lineSeparator)
            completed = true
          }
        }
      }
    }.replace(Regex("(asc|desc)\\s*(on)", RegexOption.IGNORE_CASE), "\$2")
    REGEX_TEST_ALTER_TABLE.containsMatchIn(aligned) -> buildString {
      var completed = true
      aligned.replace(Regex("^(alter\\s+table\\s+\\S+)\\s*", RegexOption.IGNORE_CASE), "\$1\r\n\t")
          .replace(Regex("\\)\\s*(references)", RegexOption.IGNORE_CASE), ")\r\n\t\$1")
          .split("\r\n").forEach {
            if (this.isEmpty()) {
              append(it)
              append(lineSeparator)
            } else if (completed) {
              if (Regex("^\\s*[^(]+(?:[^(),\\s]+|\\S\\([^)]+\\)[^),]*),\\s*\$").containsMatchIn(it)) {
                append(it)
                append(lineSeparator)
              } else {
                append(it)
                completed = false
              }
            } else {
              append(it.trim())
              if (Regex("[^)]+\\).*\$").containsMatchIn(it)) {
                append(lineSeparator)
                completed = true
              }
            }
          }
    }
    else -> str.trim() + lineSeparator
  }
  return result.trim()
}
