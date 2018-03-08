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

open class JpaSchemaGenerationProperties(val name: String?) {

  var skip: Boolean? = null
  var format: Boolean? = null
  var scanTestClasses: Boolean? = null

  var persistenceXml: String? = null
  var persistenceUnitName: String? = null

  var databaseAction: String? = null
  var scriptAction: String? = null

  lateinit var outputDirectory: File
  var createOutputFileName: String? = null
  var dropOutputFileName: String? = null

  var createSourceMode: String? = null
  var createSourceFile: File? = null
  var dropSourceMode: String? = null
  var dropSourceFile: File? = null

  var jdbcDriver: String? = null
  var jdbcUrl: String? = null
  var jdbcUser: String? = null
  var jdbcPassword: String? = null

  var databaseProductName: String? = null
  var databaseMajorVersion: Int? = null
  var databaseMinorVersion: Int? = null

  var properties: Map<String, String> = mapOf()

  var vendor: String? = null
  var packageToScan: List<String> = listOf()

  var lineSeparator: String? = null

  val defaultCreateOutputFileName = if (name == null) "create.sql" else "$name-create.sql"
  val defaultDropOutputFileName = if (name == null) "drop.sql" else "$name-drop.sql"
}

open class JpaSchemaGenerationExtension : JpaSchemaGenerationProperties(null) {

  init {
    skip = false
    format = false
    scanTestClasses = false
    persistenceXml = ECLIPSELINK_PERSISTENCE_XML
    persistenceUnitName = DEFAULT_PERSISTENCE_UNIT_NAME
    databaseAction = JAVAX_SCHEMA_GENERATION_NONE_ACTION
    scriptAction = JAVAX_SCHEMA_GENERATION_NONE_ACTION
    createSourceMode = JAVAX_SCHEMA_GENERATION_METADATA_SOURCE
    dropSourceMode = JAVAX_SCHEMA_GENERATION_METADATA_SOURCE
  }

  lateinit var targets: NamedDomainObjectContainer<JpaSchemaGenerationProperties>

  fun targets(action: Action<NamedDomainObjectContainer<JpaSchemaGenerationProperties>>) {
    action.execute(targets)
  }
}
