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

package io.github.divinespear

import java.sql.Array
import java.sql.Blob
import java.sql.CallableStatement
import java.sql.Clob
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.NClob
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.RowIdLifetime
import java.sql.SQLClientInfoException
import java.sql.SQLException
import java.sql.SQLWarning
import java.sql.SQLXML
import java.sql.Savepoint
import java.sql.Statement
import java.sql.Struct
import java.util.concurrent.Executor

class ConnectionMock implements Connection {

    private final String productName
    private final Integer majorVersion
    private final Integer minorVersion

    public ConnectionMock(String productName, Integer majorVersion, Integer minorVersion) {
        this.productName = productName
        this.majorVersion = majorVersion ?: 0
        this.minorVersion = minorVersion ?: 0
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        null
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        false
    }

    public Statement createStatement() throws SQLException {
        null
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        null
    }

    public CallableStatement prepareCall(String sql) throws SQLException {
        null
    }

    public String nativeSQL(String sql) throws SQLException {
        null
    }

    public void setAutoCommit(boolean autoCommit) throws SQLException {
    }

    public boolean getAutoCommit() throws SQLException {
        true
    }

    public void commit() throws SQLException {
    }

    public void rollback() throws SQLException {
    }

    public void close() throws SQLException {
    }

    public boolean isClosed() throws SQLException {
        false
    }

    public DatabaseMetaData getMetaData() throws SQLException {
        new DatabaseMetaData() {
                    public <T> T unwrap(Class<T> iface) throws SQLException {
                        null
                    }

                    public boolean isWrapperFor(Class<?> iface) throws SQLException {
                        false
                    }

                    public boolean allProceduresAreCallable() throws SQLException {
                        false
                    }

                    public boolean allTablesAreSelectable() throws SQLException {
                        false
                    }

                    public String getURL() throws SQLException {
                        null
                    }

                    public String getUserName() throws SQLException {
                        null
                    }
                    public boolean isReadOnly() throws SQLException {
                        false
                    }
                    public boolean nullsAreSortedHigh() throws SQLException {
                        false
                    }
                    public boolean nullsAreSortedLow() throws SQLException {
                        false
                    }
                    public boolean nullsAreSortedAtStart() throws SQLException {
                        false
                    }
                    public boolean nullsAreSortedAtEnd() throws SQLException {
                        false
                    }
                    public String getDatabaseProductName() throws SQLException {
                        productName
                    }
                    public String getDatabaseProductVersion() throws SQLException {
                        null
                    }
                    public String getDriverName() throws SQLException {
                        null
                    }
                    public String getDriverVersion() throws SQLException {
                        null
                    }
                    public int getDriverMajorVersion() {
                        0
                    }
                    public int getDriverMinorVersion() {
                        0
                    }
                    public boolean usesLocalFiles() throws SQLException {
                        false
                    }
                    public boolean usesLocalFilePerTable() throws SQLException {
                        false
                    }
                    public boolean supportsMixedCaseIdentifiers() throws SQLException {
                        false
                    }
                    public boolean storesUpperCaseIdentifiers() throws SQLException {
                        false
                    }
                    public boolean storesLowerCaseIdentifiers() throws SQLException {
                        false
                    }
                    public boolean storesMixedCaseIdentifiers() throws SQLException {
                        false
                    }
                    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
                        false
                    }
                    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
                        false
                    }
                    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
                        false
                    }
                    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
                        false
                    }
                    public String getIdentifierQuoteString() throws SQLException {
                        null
                    }
                    public String getSQLKeywords() throws SQLException {
                        null
                    }
                    public String getNumericFunctions() throws SQLException {
                        null
                    }
                    public String getStringFunctions() throws SQLException {
                        null
                    }
                    public String getSystemFunctions() throws SQLException {
                        null
                    }
                    public String getTimeDateFunctions() throws SQLException {
                        null
                    }
                    public String getSearchStringEscape() throws SQLException {
                        null
                    }
                    public String getExtraNameCharacters() throws SQLException {
                        null
                    }
                    public boolean supportsAlterTableWithAddColumn() throws SQLException {
                        false
                    }
                    public boolean supportsAlterTableWithDropColumn() throws SQLException {
                        false
                    }
                    public boolean supportsColumnAliasing() throws SQLException {
                        false
                    }
                    public boolean nullPlusNonNullIsNull() throws SQLException {
                        false
                    }
                    public boolean supportsConvert() throws SQLException {
                        false
                    }
                    public boolean supportsConvert(int fromType,
                            int toType) throws SQLException {
                        false
                    }
                    public boolean supportsTableCorrelationNames() throws SQLException {
                        false
                    }
                    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
                        false
                    }
                    public boolean supportsExpressionsInOrderBy() throws SQLException {
                        false
                    }
                    public boolean supportsOrderByUnrelated() throws SQLException {
                        false
                    }
                    public boolean supportsGroupBy() throws SQLException {
                        false
                    }
                    public boolean supportsGroupByUnrelated() throws SQLException {
                        false
                    }
                    public boolean supportsGroupByBeyondSelect() throws SQLException {
                        false
                    }
                    public boolean supportsLikeEscapeClause() throws SQLException {
                        false
                    }
                    public boolean supportsMultipleResultSets() throws SQLException {
                        false
                    }
                    public boolean supportsMultipleTransactions() throws SQLException {
                        false
                    }
                    public boolean supportsNonNullableColumns() throws SQLException {
                        false
                    }
                    public boolean supportsMinimumSQLGrammar() throws SQLException {
                        false
                    }
                    public boolean supportsCoreSQLGrammar() throws SQLException {
                        false
                    }
                    public boolean supportsExtendedSQLGrammar() throws SQLException {
                        false
                    }
                    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
                        false
                    }
                    public boolean supportsANSI92IntermediateSQL() throws SQLException {
                        false
                    }
                    public boolean supportsANSI92FullSQL() throws SQLException {
                        false
                    }
                    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
                        false
                    }
                    public boolean supportsOuterJoins() throws SQLException {
                        false
                    }
                    public boolean supportsFullOuterJoins() throws SQLException {
                        false
                    }
                    public boolean supportsLimitedOuterJoins() throws SQLException {
                        false
                    }
                    public String getSchemaTerm() throws SQLException {
                        null
                    }
                    public String getProcedureTerm() throws SQLException {
                        null
                    }
                    public String getCatalogTerm() throws SQLException {
                        null
                    }
                    public boolean isCatalogAtStart() throws SQLException {
                        false
                    }
                    public String getCatalogSeparator() throws SQLException {
                        null
                    }
                    public boolean supportsSchemasInDataManipulation() throws SQLException {
                        false
                    }
                    public boolean supportsSchemasInProcedureCalls() throws SQLException {
                        false
                    }
                    public boolean supportsSchemasInTableDefinitions() throws SQLException {
                        false
                    }
                    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
                        false
                    }
                    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
                        false
                    }
                    public boolean supportsCatalogsInDataManipulation() throws SQLException {
                        false
                    }
                    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
                        false
                    }
                    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
                        false
                    }
                    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
                        false
                    }
                    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
                        false
                    }
                    public boolean supportsPositionedDelete() throws SQLException {
                        false
                    }
                    public boolean supportsPositionedUpdate() throws SQLException {
                        false
                    }
                    public boolean supportsSelectForUpdate() throws SQLException {
                        false
                    }
                    public boolean supportsStoredProcedures() throws SQLException {
                        false
                    }
                    public boolean supportsSubqueriesInComparisons() throws SQLException {
                        false
                    }
                    public boolean supportsSubqueriesInExists() throws SQLException {
                        false
                    }
                    public boolean supportsSubqueriesInIns() throws SQLException {
                        false
                    }
                    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
                        false
                    }
                    public boolean supportsCorrelatedSubqueries() throws SQLException {
                        false
                    }
                    public boolean supportsUnion() throws SQLException {
                        false
                    }
                    public boolean supportsUnionAll() throws SQLException {
                        false
                    }
                    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
                        false
                    }
                    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
                        false
                    }
                    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
                        false
                    }
                    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
                        false
                    }
                    public int getMaxBinaryLiteralLength() throws SQLException {
                        0
                    }
                    public int getMaxCharLiteralLength() throws SQLException {
                        0
                    }
                    public int getMaxColumnNameLength() throws SQLException {
                        0
                    }
                    public int getMaxColumnsInGroupBy() throws SQLException {
                        0
                    }
                    public int getMaxColumnsInIndex() throws SQLException {
                        0
                    }
                    public int getMaxColumnsInOrderBy() throws SQLException {
                        0
                    }
                    public int getMaxColumnsInSelect() throws SQLException {
                        0
                    }
                    public int getMaxColumnsInTable() throws SQLException {
                        0
                    }
                    public int getMaxConnections() throws SQLException {
                        0
                    }
                    public int getMaxCursorNameLength() throws SQLException {
                        0
                    }
                    public int getMaxIndexLength() throws SQLException {
                        0
                    }
                    public int getMaxSchemaNameLength() throws SQLException {
                        0
                    }
                    public int getMaxProcedureNameLength() throws SQLException {
                        0
                    }
                    public int getMaxCatalogNameLength() throws SQLException {
                        0
                    }
                    public int getMaxRowSize() throws SQLException {
                        0
                    }
                    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
                        false
                    }
                    public int getMaxStatementLength() throws SQLException {
                        0
                    }
                    public int getMaxStatements() throws SQLException {
                        0
                    }
                    public int getMaxTableNameLength() throws SQLException {
                        0
                    }
                    public int getMaxTablesInSelect() throws SQLException {
                        0
                    }
                    public int getMaxUserNameLength() throws SQLException {
                        0
                    }
                    public int getDefaultTransactionIsolation() throws SQLException {
                        0
                    }
                    public boolean supportsTransactions() throws SQLException {
                        false
                    }
                    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
                        false
                    }
                    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
                        false
                    }
                    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
                        false
                    }
                    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
                        false
                    }
                    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
                        false
                    }
                    public ResultSet getProcedures(String catalog,
                            String schemaPattern,
                            String procedureNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getProcedureColumns(String catalog,
                            String schemaPattern,
                            String procedureNamePattern,
                            String columnNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getTables(String catalog,
                            String schemaPattern,
                            String tableNamePattern,
                            String[] types) throws SQLException {
                        null
                    }
                    public ResultSet getSchemas() throws SQLException {
                        null
                    }
                    public ResultSet getCatalogs() throws SQLException {
                        null
                    }
                    public ResultSet getTableTypes() throws SQLException {
                        null
                    }
                    public ResultSet getColumns(String catalog,
                            String schemaPattern,
                            String tableNamePattern,
                            String columnNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getColumnPrivileges(String catalog,
                            String schema,
                            String table,
                            String columnNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getTablePrivileges(String catalog,
                            String schemaPattern,
                            String tableNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getBestRowIdentifier(String catalog,
                            String schema,
                            String table,
                            int scope,
                            boolean nullable) throws SQLException {
                        null
                    }
                    public ResultSet getVersionColumns(String catalog,
                            String schema,
                            String table) throws SQLException {
                        null
                    }
                    public ResultSet getPrimaryKeys(String catalog,
                            String schema,
                            String table) throws SQLException {
                        null
                    }
                    public ResultSet getImportedKeys(String catalog,
                            String schema,
                            String table) throws SQLException {
                        null
                    }
                    public ResultSet getExportedKeys(String catalog,
                            String schema,
                            String table) throws SQLException {
                        null
                    }
                    public ResultSet getCrossReference(String parentCatalog,
                            String parentSchema,
                            String parentTable,
                            String foreignCatalog,
                            String foreignSchema,
                            String foreignTable) throws SQLException {
                        null
                    }
                    public ResultSet getTypeInfo() throws SQLException {
                        null
                    }
                    public ResultSet getIndexInfo(String catalog,
                            String schema,
                            String table,
                            boolean unique,
                            boolean approximate) throws SQLException {
                        null
                    }
                    public boolean supportsResultSetType(int type) throws SQLException {
                        false
                    }
                    public boolean supportsResultSetConcurrency(int type,
                            int concurrency) throws SQLException {
                        false
                    }
                    public boolean ownUpdatesAreVisible(int type) throws SQLException {
                        false
                    }
                    public boolean ownDeletesAreVisible(int type) throws SQLException {
                        false
                    }
                    public boolean ownInsertsAreVisible(int type) throws SQLException {
                        false
                    }
                    public boolean othersUpdatesAreVisible(int type) throws SQLException {
                        false
                    }
                    public boolean othersDeletesAreVisible(int type) throws SQLException {
                        false
                    }
                    public boolean othersInsertsAreVisible(int type) throws SQLException {
                        false
                    }
                    public boolean updatesAreDetected(int type) throws SQLException {
                        false
                    }
                    public boolean deletesAreDetected(int type) throws SQLException {
                        false
                    }
                    public boolean insertsAreDetected(int type) throws SQLException {
                        false
                    }
                    public boolean supportsBatchUpdates() throws SQLException {
                        false
                    }
                    public ResultSet getUDTs(String catalog,
                            String schemaPattern,
                            String typeNamePattern,
                            int[] types) throws SQLException {
                        null
                    }
                    public Connection getConnection() throws SQLException {
                        null
                    }
                    public boolean supportsSavepoints() throws SQLException {
                        false
                    }
                    public boolean supportsNamedParameters() throws SQLException {
                        false
                    }
                    public boolean supportsMultipleOpenResults() throws SQLException {
                        false
                    }
                    public boolean supportsGetGeneratedKeys() throws SQLException {
                        false
                    }
                    public ResultSet getSuperTypes(String catalog,
                            String schemaPattern,
                            String typeNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getSuperTables(String catalog,
                            String schemaPattern,
                            String tableNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getAttributes(String catalog,
                            String schemaPattern,
                            String typeNamePattern,
                            String attributeNamePattern) throws SQLException {
                        null
                    }
                    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
                        false
                    }
                    public int getResultSetHoldability() throws SQLException {
                        0
                    }
                    public int getDatabaseMajorVersion() throws SQLException {
                        majorVersion
                    }
                    public int getDatabaseMinorVersion() throws SQLException {
                        minorVersion
                    }
                    public int getJDBCMajorVersion() throws SQLException {
                        0
                    }
                    public int getJDBCMinorVersion() throws SQLException {
                        0
                    }
                    public int getSQLStateType() throws SQLException {
                        0
                    }
                    public boolean locatorsUpdateCopy() throws SQLException {
                        false
                    }
                    public boolean supportsStatementPooling() throws SQLException {
                        false
                    }
                    public RowIdLifetime getRowIdLifetime() throws SQLException {
                        null
                    }
                    public ResultSet getSchemas(String catalog,
                            String schemaPattern) throws SQLException {
                        null
                    }
                    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
                        false
                    }
                    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
                        false
                    }
                    public ResultSet getClientInfoProperties() throws SQLException {
                        null
                    }
                    public ResultSet getFunctions(String catalog,
                            String schemaPattern,
                            String functionNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getFunctionColumns(String catalog,
                            String schemaPattern,
                            String functionNamePattern,
                            String columnNamePattern) throws SQLException {
                        null
                    }
                    public ResultSet getPseudoColumns(String catalog,
                            String schemaPattern,
                            String tableNamePattern,
                            String columnNamePattern) throws SQLException {
                        null
                    }
                    public boolean generatedKeyAlwaysReturned() throws SQLException {
                        false
                    }
                }
    }

    public void setReadOnly(boolean readOnly) throws SQLException {
    }

    public boolean isReadOnly() throws SQLException {
        false
    }

    public void setCatalog(String catalog) throws SQLException {
    }

    public String getCatalog() throws SQLException {
        null
    }

    public void setTransactionIsolation(int level) throws SQLException {
    }

    public int getTransactionIsolation() throws SQLException {
        0
    }

    public SQLWarning getWarnings() throws SQLException {
        null
    }

    public void clearWarnings() throws SQLException {
    }

    public Statement createStatement(int resultSetType,
            int resultSetConcurrency) throws SQLException {
        null
    }

    public PreparedStatement prepareStatement(String sql,
            int resultSetType,
            int resultSetConcurrency) throws SQLException {
        null
    }

    public CallableStatement prepareCall(String sql,
            int resultSetType,
            int resultSetConcurrency) throws SQLException {
        null
    }

    public Map<String, Class<?>> getTypeMap() throws SQLException {
        null
    }

    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    }

    public void setHoldability(int holdability) throws SQLException {
    }

    public int getHoldability() throws SQLException {
        0
    }

    public Savepoint setSavepoint() throws SQLException {
        null
    }

    public Savepoint setSavepoint(String name) throws SQLException {
        null
    }

    public void rollback(Savepoint savepoint) throws SQLException {
    }

    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    }

    public Statement createStatement(int resultSetType,
            int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        null
    }

    public PreparedStatement prepareStatement(String sql,
            int resultSetType,
            int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        null
    }

    public CallableStatement prepareCall(String sql,
            int resultSetType,
            int resultSetConcurrency,
            int resultSetHoldability) throws SQLException {
        null
    }

    public PreparedStatement prepareStatement(String sql,
            int autoGeneratedKeys) throws SQLException {
        null
    }

    public PreparedStatement prepareStatement(String sql,
            int[] columnIndexes) throws SQLException {
        null
    }

    public PreparedStatement prepareStatement(String sql,
            String[] columnNames) throws SQLException {
        null
    }

    public Clob createClob() throws SQLException {
        null
    }

    public Blob createBlob() throws SQLException {
        null
    }

    public NClob createNClob() throws SQLException {
        null
    }

    public SQLXML createSQLXML() throws SQLException {
        null
    }

    public boolean isValid(int timeout) throws SQLException {
        false
    }

    public void setClientInfo(String name,
            String value) throws SQLClientInfoException {
    }

    public void setClientInfo(Properties properties) throws SQLClientInfoException {
    }

    public String getClientInfo(String name) throws SQLException {
        null
    }

    public Properties getClientInfo() throws SQLException {
        null
    }

    public Array createArrayOf(String typeName,
            Object[] elements) throws SQLException {
        null
    }

    public Struct createStruct(String typeName,
            Object[] attributes) throws SQLException {
        null
    }

    public void setSchema(String schema) throws SQLException {
    }

    public String getSchema() throws SQLException {
        null
    }

    public void abort(Executor executor) throws SQLException {
    }

    public void setNetworkTimeout(Executor executor,
            int milliseconds) throws SQLException {
    }

    int getNetworkTimeout() throws SQLException {
        0
    }
}
