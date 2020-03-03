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
@file:JvmName("HibernateDialectResolver")

package io.github.divinespear.plugin

import java.sql.DatabaseMetaData

internal fun resolveHibernateDialect(databaseName: String, majorVersion: Int, minorVersion: Int) = try {
  val classLoader = Thread.currentThread().contextClassLoader
  // create connection mock
  val mock = ConnectionMock(databaseName, majorVersion, minorVersion)
  // create DialectResolutionInfo
  val driTypeClass = classLoader.loadClass("org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo")
  val driClass = classLoader.loadClass("org.hibernate.engine.jdbc.dialect.spi.DatabaseMetaDataDialectResolutionInfoAdapter")
  val dri = driClass.getDeclaredConstructor(DatabaseMetaData::class.java).newInstance(mock.metaData)
  // get resolver
  val resolverClass = classLoader.loadClass("org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver")
  val resolver = resolverClass.getDeclaredConstructor().newInstance()
  // resolve dialect
  val resolveMethod = resolverClass.getDeclaredMethod("resolveDialect", driTypeClass)
  val found = resolveMethod.invoke(resolver, dri)
  found.javaClass.typeName
} catch (e: ReflectiveOperationException) {
  null
}
