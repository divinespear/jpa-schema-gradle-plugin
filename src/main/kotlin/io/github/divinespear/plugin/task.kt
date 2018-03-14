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

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager
import java.net.URL
import java.net.URLClassLoader
import java.nio.file.Files
import java.sql.Driver
import java.sql.DriverManager
import javax.persistence.Persistence
import javax.persistence.spi.PersistenceProvider

open class JpaSchemaGenerationTask : DefaultTask() {

  private val extension: JpaSchemaGenerationExtension by lazy {
    project.extensions.getByName(EXTENSION_NAME) as JpaSchemaGenerationExtension
  }

  private fun targets(): Iterable<JpaSchemaGenerationProperties> {
    val list = extension.targets.map { extension.extend(it) }
    return if (list.isEmpty()) listOf(extension) else list
  }

  @TaskAction
  fun run() {
    targets().map { doRun(it) }
  }

  private fun doRun(target: JpaSchemaGenerationProperties) {
    // create output directory
    if (target.isScriptTarget()) {
      val outputDirectory = target.outputDirectory ?: extension.defaultOutputDirectory
      target.outputDirectory = outputDirectory
      Files.createDirectories(outputDirectory.toPath())
    }
    // classloader
    val taskClassLoader = project.classLoader(javaClass.classLoader, target.scanTestClasses == true)
    // register jdbc driver if not registered
    val driverClassName = target.jdbcDriver ?: ""
    if (driverClassName.isNotEmpty() && DriverManager.getDrivers().toList().none { it.javaClass.name == driverClassName }) {
      val driver = taskClassLoader.loadClass(driverClassName).newInstance() as Driver
      DriverManager.registerDriver(driver)
    }
    // generate
    val thread = Thread.currentThread()
    val originalClassLoader = thread.contextClassLoader
    try {
      thread.contextClassLoader = taskClassLoader
      val provider = target.provider()
      if (provider == null) {
        // with xml
        doGenerate(target)
      } else {
        // without xml
        doGenerate(provider, target)
      }
    } finally {
      thread.contextClassLoader = originalClassLoader
    }
    // post-process
    postProcess(target)
  }

  private fun doGenerate(target: JpaSchemaGenerationProperties) {
    Persistence.generateSchema(target.persistenceUnitName, target.persistenceProperties())
  }

  private fun doGenerate(providerClassName: String, target: JpaSchemaGenerationProperties) {
    if (target.packageToScan.isEmpty()) {
      error("packageToScan is required on non-xml mode.")
    }

    val persistenceUnitInfo = DefaultPersistenceUnitManager().apply {
      setPersistenceXmlLocations()
      setDefaultPersistenceUnitName(target.persistenceUnitName)
      setPackagesToScan(*target.packageToScan.toTypedArray())
      afterPropertiesSet()
    }.obtainDefaultPersistenceUnitInfo()

    @Suppress("UNCHECKED_CAST")
    val providerClass = Class.forName(providerClassName) as Class<PersistenceProvider>
    val providerConstructor = providerClass.getDeclaredConstructor().apply {
      isAccessible = true
    }
    val provider = providerConstructor.newInstance()
    provider.generateSchema(persistenceUnitInfo, target.persistenceProperties())
  }
}

private fun Project.classLoader(parent: ClassLoader, scanTestClasses: Boolean = false): ClassLoader {
  val classURLs = mutableSetOf<URL>()
  // source output dirs
  (properties["sourceSets"] as SourceSetContainer).forEach {
    if (!it.name.contains("test", ignoreCase = true) || scanTestClasses) {
      classURLs.addAll(it.output.classesDirs.map { it.toURI().toURL() })
      classURLs.add(it.output.resourcesDir.toURI().toURL())
    }
  }
  // dependencies
  configurations.getByName("runtimeClasspath").forEach {
    classURLs.add(it.toURI().toURL())
  }
  // log classloader targets
  logger.info(buildString {
    appendln("classpath:")
    appendln(classURLs.joinToString("\n  * ", prefix = "  * "))
  })
  return URLClassLoader(classURLs.toTypedArray(), parent)
}

private fun JpaSchemaGenerationProperties.persistenceProperties(): Map<String, Any?> {
  val map = mutableMapOf<String, Any?>()

  map[JAVAX_SCHEMA_GENERATION_DATABASE_ACTION] = (databaseAction ?: JAVAX_SCHEMA_GENERATION_NONE_ACTION).toLowerCase()
  map[JAVAX_SCHEMA_GENERATION_SCRIPTS_ACTION] = (scriptAction ?: JAVAX_SCHEMA_GENERATION_NONE_ACTION).toLowerCase()

  if (isScriptTarget()) {
    val directory = outputDirectory?.toPath() ?: error("outputDirectory is required for script target")
    val createFileName = createOutputFileName ?: defaultCreateOutputFileName
    val dropFileName = dropOutputFileName ?: defaultDropOutputFileName
    map[JAVAX_SCHEMA_GENERATION_SCRIPTS_CREATE_TARGET] = directory.resolve(createFileName).toUri().toString()
    map[JAVAX_SCHEMA_GENERATION_SCRIPTS_DROP_TARGET] = directory.resolve(dropFileName).toUri().toString()
  }

  if (isDatabaseTarget() && jdbcUrl == null) {
    error("jdbcUrl is required for database target")
  }

  map[JAVAX_SCHEMA_DATABASE_PRODUCT_NAME] = databaseProductName
  map[JAVAX_SCHEMA_DATABASE_MAJOR_VERSION] = databaseMajorVersion?.toString()
  map[JAVAX_SCHEMA_DATABASE_MINOR_VERSION] = databaseMinorVersion?.toString()

  map[JAVAX_JDBC_DRIVER] = jdbcDriver
  map[JAVAX_JDBC_URL] = jdbcUrl
  map[JAVAX_JDBC_USER] = jdbcUser
  map[JAVAX_JDBC_PASSWORD] = jdbcPassword

  map[JAVAX_SCHEMA_GENERATION_CREATE_SOURCE] = createSourceMode
  val createSourceFile = createSourceFile
  if (createSourceFile == null) {
    if (JAVAX_SCHEMA_GENERATION_METADATA_SOURCE != createSourceMode) {
      error("create source file is required for mode $createSourceMode")
    }
  } else {
    map[JAVAX_SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE] = createSourceFile.toPath().toAbsolutePath().toUri().toString()
  }
  map[JAVAX_SCHEMA_GENERATION_DROP_SOURCE] = dropSourceMode
  val dropSourceFile = dropSourceFile
  if (dropSourceFile == null) {
    if (JAVAX_SCHEMA_GENERATION_METADATA_SOURCE != dropSourceMode) {
      error("drop source file is required for mode $dropSourceMode")
    }
  } else {
    map[JAVAX_SCHEMA_GENERATION_DROP_SCRIPT_SOURCE] = dropSourceFile.toPath().toAbsolutePath().toUri().toString()
  }

  map[ECLIPSELINK_PERSISTENCE_XML] = persistenceXml
  map[ECLIPSELINK_WEAVING] = "false"

  map[HIBERNATE_AUTODETECTION] = "class,hbm"
  if ((jdbcUrl ?: "").isEmpty() && (properties[HIBERNATE_DIALECT] ?: "").isEmpty()) {
    val info = object : DialectResolutionInfo {
      override fun getDriverName(): String? = null
      override fun getDriverMajorVersion() = 0
      override fun getDriverMinorVersion() = 0
      override fun getDatabaseName() = databaseProductName
      override fun getDatabaseMajorVersion() = this@persistenceProperties.databaseMajorVersion ?: 0
      override fun getDatabaseMinorVersion() = this@persistenceProperties.databaseMinorVersion ?: 0
    }
    val dialect = StandardDialectResolver.INSTANCE.resolveDialect(info)
    map[HIBERNATE_DIALECT] = dialect.javaClass.name
  }

  map.putAll(properties)

  // issue-3: pass mock connection
  if (!isDatabaseTarget() && jdbcUrl.isNullOrEmpty()) {
    map[JAVAX_SCHEMA_GEN_CONNECTION] = ConnectionMock(databaseProductName!!, databaseMajorVersion, databaseMinorVersion)
  }
  // issue-5: pass "none" for avoid validation while schema generating
  map[JAVAX_VALIDATION_MODE] = "none"

  return map.filterValues {
    // issue-24: remove null value before reset JTA
    it != null
  }.toMutableMap().apply {
    // issue-13: disable JTA and datasources
    this[JAVAX_TRANSACTION_TYPE] = JAVAX_TRANSACTION_TYPE_RESOURCE_LOCAL
    this[JAVAX_JTA_DATASOURCE] = null
    this[JAVAX_NON_JTA_DATASOURCE] = null
  }
}

private fun postProcess(target: JpaSchemaGenerationProperties) {
  // do format output
  val outputDirectory = target.outputDirectory ?: return
  listOfNotNull(target.createOutputFileName, target.dropOutputFileName).map {
    outputDirectory.toPath().resolve(it)
  }.forEach {
    val lineSeparator = LINE_SEPARATOR_MAP[target.lineSeparator?.toUpperCase()] ?: System.getProperty("line.separator", "\n")
    formatFile(it, target.format == true, lineSeparator)
  }
}
