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

import java.io.File
import java.net.URL
import java.util.*
import javax.persistence.spi.PersistenceUnitInfo
import javax.persistence.spi.PersistenceUnitTransactionType
import javax.sql.DataSource

open class JpaSchemaGenerationProperties(
  val name: String?,
  val options: MutableMap<String, Any?>
) {

  constructor(name: String?) : this(
    name, mutableMapOf(
      "properties" to mapOf<String, String>(),
      "packageToScan" to setOf<String>()
    )
  )

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

  fun buildPersistenceUnitInfo(classLoader: ClassLoader): PersistenceUnitInfo =
    createPersistenceUnitInfo(classLoader).let {
      return if (this.provider() === PERSISTENCE_PROVIDER_MAP["eclipselink"]) {
        createEclipselinkPersistenceUnitInfo(classLoader, it)
      } else {
        it
      }
    }

  private fun createPersistenceUnitInfo(classLoader: ClassLoader): PersistenceUnitInfo {
    val p = this
    val managerClass =
      classLoader.loadClass("org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager")
    return managerClass.getDeclaredConstructor().newInstance().apply {
      managerClass.getDeclaredMethod("setPersistenceXmlLocations", Array<String>::class.java)
        .invoke(this, emptyArray<String>())
      managerClass.getDeclaredMethod("setDefaultPersistenceUnitName", String::class.java)
        .invoke(this, p.persistenceUnitName!!)
      managerClass.getDeclaredMethod("setPackagesToScan", Array<String>::class.java)
        .invoke(this, p.packageToScan.toTypedArray())
      managerClass.getDeclaredMethod("afterPropertiesSet").invoke(this)
    }.let {
      managerClass.getDeclaredMethod("obtainDefaultPersistenceUnitInfo").invoke(it) as PersistenceUnitInfo
    }
  }

  private fun createEclipselinkPersistenceUnitInfo(
    classLoader: ClassLoader,
    origin: PersistenceUnitInfo
  ): PersistenceUnitInfo {
    val puiClass = classLoader.loadClass("org.eclipse.persistence.internal.jpa.deployment.SEPersistenceUnitInfo")
    return puiClass.getDeclaredConstructor().newInstance().apply {
      puiClass.getMethod("setPersistenceUnitName", String::class.java)
        .invoke(this, origin.persistenceUnitName)
      puiClass.getMethod("setTransactionType", PersistenceUnitTransactionType::class.java)
        .invoke(this, origin.transactionType)
      puiClass.getMethod("setJtaDataSource", DataSource::class.java)
        .invoke(this, origin.jtaDataSource)
      puiClass.getMethod("setNonJtaDataSource", DataSource::class.java)
        .invoke(this, origin.nonJtaDataSource)
      puiClass.getMethod("setMappingFileNames", java.util.List::class.java)
        .invoke(this, origin.mappingFileNames)
      puiClass.getMethod("setJarFileUrls", java.util.List::class.java)
        .invoke(this, origin.jarFileUrls)
      puiClass.getMethod("setPersistenceUnitRootUrl", URL::class.java)
        .invoke(this, origin.persistenceUnitRootUrl)
      puiClass.getMethod("setManagedClassNames", java.util.List::class.java)
        .invoke(this, origin.managedClassNames)
      puiClass.getMethod("setExcludeUnlistedClasses", Boolean::class.javaPrimitiveType)
        .invoke(this, origin.excludeUnlistedClasses())
      puiClass.getMethod("setProperties", Properties::class.java)
        .invoke(this, origin.properties)
      puiClass.getMethod("setSharedCacheMode", String::class.java)
        .invoke(this, origin.sharedCacheMode.toString())
      puiClass.getMethod("setValidationMode", String::class.java)
        .invoke(this, origin.validationMode.toString())
      puiClass.getMethod("setClassLoader", ClassLoader::class.java)
        .invoke(this, origin.classLoader)
    } as PersistenceUnitInfo
  }
}
