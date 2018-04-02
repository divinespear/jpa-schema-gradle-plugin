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

internal fun resolveHibernateDialect(databaseName: String, databaseMajorVersion: Int, databaseMinorVersion: Int) = DIALECT_INFO_LIST.find { it.matcher.invoke(databaseName) }?.dialect?.invoke(databaseMajorVersion, databaseMinorVersion)?.let { DIALECT_CLASSNAME_PREFIX + it }


private const val DIALECT_CLASSNAME_PREFIX = "org.hibernate.dialect."

private data class HibernateDialectInfo(val matcher: (String) -> Boolean,
                                        val dialect: (Int, Int) -> String)

private val CUBRID = HibernateDialectInfo({ "CUBRID".equals(it, true) },
                                          { _, _ -> "CUBRIDDialect" })
private val HSQL = HibernateDialectInfo({ "HSQL Database Engine".equals(it, true) },
                                        { _, _ -> "HSQLDialect" })
private val H2 = HibernateDialectInfo({ "H2".equals(it, true) },
                                      { _, _ -> "H2Dialect" })
private val MYSQL = HibernateDialectInfo({ "MySQL".equals(it, true) },
                                         { major, _ ->
                                           when {
                                             major >= 5 -> "MySQL5Dialect"
                                             else -> "MySQLDialect"
                                           }
                                         })
private val POSTGRES = HibernateDialectInfo({ "PostgreSQL".equals(it, true) },
                                            { major, minor ->
                                              (major * 10 + minor).let {
                                                when {
                                                  it >= 94 -> "PostgreSQL94Dialect"
                                                  it >= 92 -> "PostgreSQL92Dialect"
                                                  it >= 90 -> "PostgreSQL9Dialect"
                                                  it >= 82 -> "PostgreSQL82Dialect"
                                                  else -> "PostgreSQL81Dialect"
                                                }
                                              }
                                            })
private val POSTGRES_PLUS = HibernateDialectInfo({ "EnterpriseDB".equals(it, true) },
                                                 { _, _ -> "PostgresPlusDialect" })
private val DERBY = HibernateDialectInfo({ "Apache Derby".equals(it, true) },
                                         { major, minor ->
                                           (major * 10 + minor).let {
                                             when {
                                               it >= 107 -> "DerbyTenSevenDialect"
                                               it == 106 -> "DerbyTenSixDialect"
                                               it == 105 -> "DerbyTenFiveDialect"
                                               else -> "DerbyDialect"
                                             }
                                           }
                                         })
private val INGRES = HibernateDialectInfo({ "ingres".equals(it, true) },
                                          { major, minor ->
                                            (major * 10 + minor).let {
                                              when {
                                                it >= 100 -> "Ingres10Dialect"
                                                it >= 92 -> "Ingres9Dialect"
                                                else -> "IngresDialect"
                                              }
                                            }
                                          })
private val MSSQL = HibernateDialectInfo({ it.startsWith("Microsoft SQL Server", true) },
                                         { major, _ ->
                                           if (major > 11) "SQLServer2012Dialect" else when (major) {
                                             11 -> "SQLServer2012Dialect"
                                             10 -> "SQLServer2008Dialect"
                                             9 -> "SQLServer2005Dialect"
                                             else -> "SQLServerDialect"
                                           }
                                         })
private val SYBASE = HibernateDialectInfo({ "Sybase SQL Server".equals(it, true) || "Adaptive Server Enterprise".equals(it, true) },
                                          { _, _ -> "SybaseASE15Dialect" })
private val SYBASE_ANY = HibernateDialectInfo({ it.startsWith("Adaptive Server Anywhere", true) },
                                              { _, _ -> "SybaseAnywhereDialect" })
private val INFOMIX = HibernateDialectInfo({ "Informix Dynamic Server".equals(it, true) },
                                           { _, _ -> "InformixDialect" })
private val DB2_UDB_AS400 = HibernateDialectInfo({ "DB2 UDB for AS/400".equals(it, true) },
                                                 { _, _ -> "DB2400Dialect" })
private val DB2_UDB = HibernateDialectInfo({ it.startsWith("DB2/", true) },
                                           { _, _ -> "DB2Dialect" })
private val ORACLE = HibernateDialectInfo({ "Oracle".equals(it, true) },
                                          { major, _ ->
                                            when (major) {
                                              12 -> "Oracle12cDialect"
                                              11, 10 -> "Oracle10gDialect"
                                              9 -> "Oracle9iDialect"
                                              else -> "Oracle8iDialect"
                                            }
                                          })
private val SAP_HANA = HibernateDialectInfo({ "HDB".equals(it, true) },
                                            { _, _ -> "HANAColumnStoreDialect" })
private val FIREBIRD = HibernateDialectInfo({ it.startsWith("Firebird", true) },
                                            { _, _ -> "FirebirdDialect" })

private val DIALECT_INFO_LIST = listOf(CUBRID, HSQL, H2, MYSQL, POSTGRES, POSTGRES_PLUS, DERBY, INGRES, MSSQL, SYBASE, SYBASE_ANY, INFOMIX, DB2_UDB_AS400, DB2_UDB, ORACLE, SAP_HANA, FIREBIRD)
