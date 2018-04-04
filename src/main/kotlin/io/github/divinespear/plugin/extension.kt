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

open class JpaSchemaGenerationProperties(val name: String?,
                                         val options: MutableMap<String, Any?>) {

  constructor(name: String?) : this(name, mutableMapOf("properties" to mapOf<String, String>(),
                                                       "packageToScan" to setOf<String>()))

  var skip: Boolean? by options
  var format: Boolean? by options
  var scanTestClasses: Boolean? by options

  var persistenceXml: String? by options
  var persistenceUnitName: String? by options

  var databaseAction: String? by options
  var scriptAction: String? by options

  var outputDirectory: File? by options
  var createOutputFileName: String? by options
  var dropOutputFileName: String? by options

  var createSourceMode: String? by options
  var createSourceFile: File? by options
  var dropSourceMode: String? by options
  var dropSourceFile: File? by options

  var jdbcDriver: String? by options
  var jdbcUrl: String? by options
  var jdbcUser: String? by options
  var jdbcPassword: String? by options

  var databaseProductName: String? by options
  var databaseMajorVersion: Int? by options
  var databaseMinorVersion: Int? by options

  var properties: Map<String, String> by options

  var vendor: String? by options
  var packageToScan: Set<String> by options

  var lineSeparator: String? by options

  val defaultCreateOutputFileName = if (name == null) "create.sql" else "$name-create.sql"
  val defaultDropOutputFileName = if (name == null) "drop.sql" else "$name-drop.sql"

  internal fun provider() = vendor?.let { PERSISTENCE_PROVIDER_MAP[it.toLowerCase()] } ?: vendor
  internal fun isDatabaseTarget() = !JAVAX_SCHEMA_GENERATION_NONE_ACTION.equals(databaseAction, true)
  internal fun isScriptTarget() = !JAVAX_SCHEMA_GENERATION_NONE_ACTION.equals(scriptAction, true)
}

open class JpaSchemaGenerationExtension : JpaSchemaGenerationProperties(null) {
  lateinit var defaultOutputDirectory: File
  lateinit var targets: NamedDomainObjectContainer<JpaSchemaGenerationProperties>

  fun targets(action: Action<NamedDomainObjectContainer<JpaSchemaGenerationProperties>>) {
    action.execute(targets)
  }

  fun extend(other: JpaSchemaGenerationProperties?) = JpaSchemaGenerationProperties(other?.name, merge(this, other))
}

private val MERGE_EXCLUDE_PROPERTIES = listOf("properties", "packageToScan")
private val PROPERTY_DEFAULT_VALUES = mapOf("skip" to false,
                                            "format" to false,
                                            "scanTestClasses" to false,
                                            "persistenceUnitName" to DEFAULT_PERSISTENCE_UNIT_NAME,
                                            "databaseAction" to JAVAX_SCHEMA_GENERATION_NONE_ACTION,
                                            "scriptAction" to JAVAX_SCHEMA_GENERATION_NONE_ACTION,
                                            "createSourceMode" to JAVAX_SCHEMA_GENERATION_METADATA_SOURCE,
                                            "dropSourceMode" to JAVAX_SCHEMA_GENERATION_METADATA_SOURCE).withDefault { _ -> null }

private fun merge(base: JpaSchemaGenerationExtension, target: JpaSchemaGenerationProperties? = null): MutableMap<String, Any?> {
  val map = mutableMapOf<String, Any?>()
  var properties = mapOf<String, String>()
  var packageToScan = setOf<String>()
  // merge extensions
  arrayOf(base, target).filterNotNull().forEach {
    map.putAll(it.options.filterKeys { !MERGE_EXCLUDE_PROPERTIES.contains(it) })
    properties += it.properties
    packageToScan += it.packageToScan
  }
  map["properties"] = properties
  map["packageToScan"] = packageToScan
  // default
  PROPERTY_DEFAULT_VALUES.forEach {
    if (map[it.key] == null) map[it.key] = it.value
  }
  if (map["outputDirectory"] == null) map["outputDirectory"] = base.defaultOutputDirectory
  if (map["createOutputFileName"] == null) map["createOutputFileName"] = (target ?: base).defaultCreateOutputFileName
  if (map["dropOutputFileName"] == null) map["dropOutputFileName"] = (target ?: base).defaultDropOutputFileName
  return map
}
