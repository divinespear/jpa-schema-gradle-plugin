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
@file:JvmName("JpaSchemaGenerationConstants")

package io.github.divinespear.plugin

internal const val PLUGIN_NAME = "generateSchema"
internal const val EXTENSION_NAME = PLUGIN_NAME
internal const val CONFIGURATION_NAME = PLUGIN_NAME

/* jpa common */
internal const val JAVAX_SCHEMA_GENERATION_NONE_ACTION = "none"
internal const val JAVAX_SCHEMA_GENERATION_DATABASE_ACTION = "javax.persistence.schema-generation.database.action"
internal const val JAVAX_SCHEMA_GENERATION_SCRIPTS_ACTION = "javax.persistence.schema-generation.scripts.action"
internal const val JAVAX_SCHEMA_GENERATION_SCRIPTS_CREATE_TARGET = "javax.persistence.schema-generation.scripts.create-target"
internal const val JAVAX_SCHEMA_GENERATION_SCRIPTS_DROP_TARGET = "javax.persistence.schema-generation.scripts.drop-target"
internal const val JAVAX_SCHEMA_DATABASE_PRODUCT_NAME = "javax.persistence.database-product-name"
internal const val JAVAX_SCHEMA_DATABASE_MAJOR_VERSION = "javax.persistence.database-major-version"
internal const val JAVAX_SCHEMA_DATABASE_MINOR_VERSION = "javax.persistence.database-minor-version"
internal const val JAVAX_JDBC_DRIVER = "javax.persistence.jdbc.driver"
internal const val JAVAX_JDBC_URL = "javax.persistence.jdbc.url"
internal const val JAVAX_JDBC_USER = "javax.persistence.jdbc.user"
internal const val JAVAX_JDBC_PASSWORD = "javax.persistence.jdbc.password"
internal const val JAVAX_SCHEMA_GENERATION_METADATA_SOURCE = "metadata"
internal const val JAVAX_SCHEMA_GENERATION_CREATE_SOURCE = "javax.persistence.schema-generation.create-source"
internal const val JAVAX_SCHEMA_GENERATION_DROP_SOURCE = "javax.persistence.schema-generation.drop-source"
internal const val JAVAX_SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE = "javax.persistence.schema-generation.create-script-source"
internal const val JAVAX_SCHEMA_GENERATION_DROP_SCRIPT_SOURCE = "javax.persistence.schema-generation.drop-script-source"
internal const val JAVAX_SCHEMA_GEN_CONNECTION = "javax.persistence.schema-generation-connection"
internal const val JAVAX_VALIDATION_MODE = "javax.persistence.validation.mode"
internal const val JAVAX_TRANSACTION_TYPE = "javax.persistence.transactionType"
internal const val JAVAX_JTA_DATASOURCE = "javax.persistence.jtaDataSource"
internal const val JAVAX_NON_JTA_DATASOURCE = "javax.persistence.nonJtaDataSource"

/* eclipse specific */
internal const val ECLIPSELINK_PERSISTENCE_XML = "eclipselink.persistencexml"
internal const val ECLIPSELINK_WEAVING = "eclipselink.weaving"

/* hibernate specific */
internal const val HIBERNATE_AUTODETECTION = "hibernate.archive.autodetection"
internal const val HIBERNATE_DIALECT = "hibernate.dialect"

/* values */
internal const val DEFAULT_PERSISTENCE_UNIT_NAME = "default"
internal const val JAVAX_TRANSACTION_TYPE_RESOURCE_LOCAL = "RESOURCE_LOCAL"

/* predefined providers */
internal val PERSISTENCE_PROVIDER_MAP = mapOf("eclipselink" to "org.eclipse.persistence.jpa.PersistenceProvider",
                                              "hibernate" to "org.hibernate.jpa.HibernatePersistenceProvider",
                                              "hibernate+spring" to "org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider")

/* line separator */
internal val LINE_SEPARATOR_MAP = mapOf("CRLF" to "\r\n", "LF" to "\n", "CR" to "\r")
