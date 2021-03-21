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

import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import java.io.File

open class JpaSchemaGenerationExtension : JpaSchemaGenerationProperties(null) {
  lateinit var defaultOutputDirectory: File
  lateinit var targets: NamedDomainObjectContainer<JpaSchemaGenerationProperties>

  fun targets(action: Action<NamedDomainObjectContainer<JpaSchemaGenerationProperties>>) {
    action.execute(targets)
  }

  fun extend(other: JpaSchemaGenerationProperties?) = JpaSchemaGenerationProperties(other?.name, merge(this, other))
}

private val MERGE_EXCLUDE_PROPERTIES = listOf("properties", "packageToScan")
private val PROPERTY_DEFAULT_VALUES = mapOf(
  "skip" to false,
  "format" to false,
  "scanTestClasses" to false,
  "persistenceUnitName" to DEFAULT_PERSISTENCE_UNIT_NAME,
  "databaseAction" to JAVAX_SCHEMA_GENERATION_NONE_ACTION,
  "scriptAction" to JAVAX_SCHEMA_GENERATION_NONE_ACTION,
  "createSourceMode" to JAVAX_SCHEMA_GENERATION_METADATA_SOURCE,
  "dropSourceMode" to JAVAX_SCHEMA_GENERATION_METADATA_SOURCE
)

private fun merge(
  base: JpaSchemaGenerationExtension,
  target: JpaSchemaGenerationProperties? = null
): MutableMap<String, Any?> {
  val map = mutableMapOf<String, Any?>()
  val properties = mutableMapOf<String, String>()
  val packageToScan = mutableSetOf<String>()
  // merge extensions
  arrayOf(base, target).filterNotNull().forEach {
    map.putAll(it.options.filterKeys { key -> !MERGE_EXCLUDE_PROPERTIES.contains(key) })
    properties += it.properties
    packageToScan += it.packageToScan
  }
  map["properties"] = properties.toMap()
  map["packageToScan"] = packageToScan.toSet()
  // default
  PROPERTY_DEFAULT_VALUES.forEach {
    if (map[it.key] == null) map[it.key] = it.value
  }
  if (map["outputDirectory"] == null) map["outputDirectory"] = base.defaultOutputDirectory
  if (map["createOutputFileName"] == null) map["createOutputFileName"] = (target ?: base).defaultCreateOutputFileName
  if (map["dropOutputFileName"] == null) map["dropOutputFileName"] = (target ?: base).defaultDropOutputFileName
  return map.withDefault { null }
}
