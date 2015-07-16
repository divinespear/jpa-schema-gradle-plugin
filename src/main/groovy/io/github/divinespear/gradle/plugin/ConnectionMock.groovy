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

package io.github.divinespear.gradle.plugin

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

final class ConnectionMock implements Connection {

    private final String productName
    private final Integer majorVersion
    private final Integer minorVersion

    public ConnectionMock(String productName, Integer majorVersion, Integer minorVersion) {
        this.productName = productName
        this.majorVersion = majorVersion ?: 0
        this.minorVersion = minorVersion ?: 0
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        null
    }
    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        false
    }
    @Override
    public Statement createStatement() throws SQLException {
        null
    }
    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        null
    }
    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        null
    }
    @Override
    public String nativeSQL(String sql) throws SQLException {
        null
    }
    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        
    }
    @Override
    public boolean getAutoCommit() throws SQLException {
        false
    }
    @Override
    public void commit() throws SQLException {
        
    }
    @Override
    public void rollback() throws SQLException {
        
    }
    @Override
    public void close() throws SQLException {
        
    }
    @Override
    public boolean isClosed() throws SQLException {
        false
    }
    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        null
    }
    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        
    }
    @Override
    public boolean isReadOnly() throws SQLException {
        false
    }
    @Override
    public void setCatalog(String catalog) throws SQLException {
        
    }
    @Override
    public String getCatalog() throws SQLException {
        null
    }
    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        
    }
    @Override
    public int getTransactionIsolation() throws SQLException {
        0
    }
    @Override
    public SQLWarning getWarnings() throws SQLException {
        null
    }
    @Override
    public void clearWarnings() throws SQLException {
        
    }
    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
        null
    }
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
            throws SQLException {
        null
    }
    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        null
    }
    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        null
    }
    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        
    }
    @Override
    public void setHoldability(int holdability) throws SQLException {
        
    }
    @Override
    public int getHoldability() throws SQLException {
        0
    }
    @Override
    public Savepoint setSavepoint() throws SQLException {
        null
    }
    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        null
    }
    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        
    }
    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        
    }
    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
            throws SQLException {
        null
    }
    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
                                              int resultSetHoldability) throws SQLException {
        null
    }
    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
                                         int resultSetHoldability) throws SQLException {
        null
    }
    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
        null
    }
    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
        null
    }
    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
        null
    }
    @Override
    public Clob createClob() throws SQLException {
        null
    }
    @Override
    public Blob createBlob() throws SQLException {
        null
    }
    @Override
    public NClob createNClob() throws SQLException {
        null
    }
    @Override
    public SQLXML createSQLXML() throws SQLException {
        null
    }
    @Override
    public boolean isValid(int timeout) throws SQLException {
        false
    }
    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        
    }
    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        
    }
    @Override
    public String getClientInfo(String name) throws SQLException {
        null
    }
    @Override
    public Properties getClientInfo() throws SQLException {
        null
    }
    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
        null
    }
    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
        null
    }
    @Override
    public void setSchema(String schema) throws SQLException {
        
    }
    @Override
    public String getSchema() throws SQLException {
        null
    }
    @Override
    public void abort(Executor executor) throws SQLException {
        
    }
    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
        
    }
    @Override
    public int getNetworkTimeout() throws SQLException {
        0
    }

}

final class DatabaseMetaDataMock implements DatabaseMetaData {
    
    private ConnectionMock connection;
    
    DatabaseMetaDataMock(ConnectionMock connection) {
        this.connection = connection;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
         null
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
         false
    }

    @Override
    public boolean allProceduresAreCallable() throws SQLException {
         false
    }

    @Override
    public boolean allTablesAreSelectable() throws SQLException {
         false
    }

    @Override
    public String getURL() throws SQLException {
        null
    }

    @Override
    public String getUserName() throws SQLException {
        null
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        false
    }

    @Override
    public boolean nullsAreSortedHigh() throws SQLException {
        false
    }

    @Override
    public boolean nullsAreSortedLow() throws SQLException {
        false
    }

    @Override
    public boolean nullsAreSortedAtStart() throws SQLException {
        false
    }

    @Override
    public boolean nullsAreSortedAtEnd() throws SQLException {
        false
    }

    @Override
    public String getDatabaseProductName() throws SQLException {
        connection.productName
    }

    @Override
    public String getDatabaseProductVersion() throws SQLException {
        null
    }

    @Override
    public String getDriverName() throws SQLException {
        null
    }

    @Override
    public String getDriverVersion() throws SQLException {
        null
    }

    @Override
    public int getDriverMajorVersion() {
        0
    }

    @Override
    public int getDriverMinorVersion() {
        0
    }

    @Override
    public boolean usesLocalFiles() throws SQLException {
        false
    }

    @Override
    public boolean usesLocalFilePerTable() throws SQLException {
        false
    }

    @Override
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        false
    }

    @Override
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        false
    }

    @Override
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        false
    }

    @Override
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        false
    }

    @Override
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        false
    }

    @Override
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        false
    }

    @Override
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        false
    }

    @Override
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        false
    }

    @Override
    public String getIdentifierQuoteString() throws SQLException {
        null
    }

    @Override
    public String getSQLKeywords() throws SQLException {
        null
    }

    @Override
    public String getNumericFunctions() throws SQLException {
        null
    }

    @Override
    public String getStringFunctions() throws SQLException {
        null
    }

    @Override
    public String getSystemFunctions() throws SQLException {
        null
    }

    @Override
    public String getTimeDateFunctions() throws SQLException {
        null
    }

    @Override
    public String getSearchStringEscape() throws SQLException {
        null
    }

    @Override
    public String getExtraNameCharacters() throws SQLException {
        null
    }

    @Override
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        false
    }

    @Override
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        false
    }

    @Override
    public boolean supportsColumnAliasing() throws SQLException {
        false
    }

    @Override
    public boolean nullPlusNonNullIsNull() throws SQLException {
        false
    }

    @Override
    public boolean supportsConvert() throws SQLException {
        false
    }

    @Override
    public boolean supportsConvert(int fromType, int toType) throws SQLException {
        false
    }

    @Override
    public boolean supportsTableCorrelationNames() throws SQLException {
        false
    }

    @Override
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        false
    }

    @Override
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        false
    }

    @Override
    public boolean supportsOrderByUnrelated() throws SQLException {
        false
    }

    @Override
    public boolean supportsGroupBy() throws SQLException {
        false
    }

    @Override
    public boolean supportsGroupByUnrelated() throws SQLException {
        false
    }

    @Override
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        false
    }

    @Override
    public boolean supportsLikeEscapeClause() throws SQLException {
        false
    }

    @Override
    public boolean supportsMultipleResultSets() throws SQLException {
        false
    }

    @Override
    public boolean supportsMultipleTransactions() throws SQLException {
        false
    }

    @Override
    public boolean supportsNonNullableColumns() throws SQLException {
        false
    }

    @Override
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        false
    }

    @Override
    public boolean supportsCoreSQLGrammar() throws SQLException {
        false
    }

    @Override
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        false
    }

    @Override
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        false
    }

    @Override
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        false
    }

    @Override
    public boolean supportsANSI92FullSQL() throws SQLException {
        false
    }

    @Override
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        false
    }

    @Override
    public boolean supportsOuterJoins() throws SQLException {
        false
    }

    @Override
    public boolean supportsFullOuterJoins() throws SQLException {
        false
    }

    @Override
    public boolean supportsLimitedOuterJoins() throws SQLException {
        false
    }

    @Override
    public String getSchemaTerm() throws SQLException {
        null
    }

    @Override
    public String getProcedureTerm() throws SQLException {
        null
    }

    @Override
    public String getCatalogTerm() throws SQLException {
        null
    }

    @Override
    public boolean isCatalogAtStart() throws SQLException {
        false
    }

    @Override
    public String getCatalogSeparator() throws SQLException {
        null
    }

    @Override
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        false
    }

    @Override
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        false
    }

    @Override
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        false
    }

    @Override
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        false
    }

    @Override
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        false
    }

    @Override
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        false
    }

    @Override
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        false
    }

    @Override
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        false
    }

    @Override
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        false
    }

    @Override
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        false
    }

    @Override
    public boolean supportsPositionedDelete() throws SQLException {
        false
    }

    @Override
    public boolean supportsPositionedUpdate() throws SQLException {
        false
    }

    @Override
    public boolean supportsSelectForUpdate() throws SQLException {
        false
    }

    @Override
    public boolean supportsStoredProcedures() throws SQLException {
        false
    }

    @Override
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        false
    }

    @Override
    public boolean supportsSubqueriesInExists() throws SQLException {
        false
    }

    @Override
    public boolean supportsSubqueriesInIns() throws SQLException {
        false
    }

    @Override
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        false
    }

    @Override
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        false
    }

    @Override
    public boolean supportsUnion() throws SQLException {
        false
    }

    @Override
    public boolean supportsUnionAll() throws SQLException {
        false
    }

    @Override
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        false
    }

    @Override
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        false
    }

    @Override
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        false
    }

    @Override
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        false
    }

    @Override
    public int getMaxBinaryLiteralLength() throws SQLException {
        0
    }

    @Override
    public int getMaxCharLiteralLength() throws SQLException {
        0
    }

    @Override
    public int getMaxColumnNameLength() throws SQLException {
        0
    }

    @Override
    public int getMaxColumnsInGroupBy() throws SQLException {
        0
    }

    @Override
    public int getMaxColumnsInIndex() throws SQLException {
        0
    }

    @Override
    public int getMaxColumnsInOrderBy() throws SQLException {
        0
    }

    @Override
    public int getMaxColumnsInSelect() throws SQLException {
        0
    }

    @Override
    public int getMaxColumnsInTable() throws SQLException {
        0
    }

    @Override
    public int getMaxConnections() throws SQLException {
        0
    }

    @Override
    public int getMaxCursorNameLength() throws SQLException {
        0
    }

    @Override
    public int getMaxIndexLength() throws SQLException {
        0
    }

    @Override
    public int getMaxSchemaNameLength() throws SQLException {
        0
    }

    @Override
    public int getMaxProcedureNameLength() throws SQLException {
        0
    }

    @Override
    public int getMaxCatalogNameLength() throws SQLException {
        0
    }

    @Override
    public int getMaxRowSize() throws SQLException {
        0
    }

    @Override
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        false
    }

    @Override
    public int getMaxStatementLength() throws SQLException {
        0
    }

    @Override
    public int getMaxStatements() throws SQLException {
        0
    }

    @Override
    public int getMaxTableNameLength() throws SQLException {
        0
    }

    @Override
    public int getMaxTablesInSelect() throws SQLException {
        0
    }

    @Override
    public int getMaxUserNameLength() throws SQLException {
        0
    }

    @Override
    public int getDefaultTransactionIsolation() throws SQLException {
        0
    }

    @Override
    public boolean supportsTransactions() throws SQLException {
        false
    }

    @Override
    public boolean supportsTransactionIsolationLevel(int level) throws SQLException {
        false
    }

    @Override
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        false
    }

    @Override
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        false
    }

    @Override
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        false
    }

    @Override
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        false
    }

    @Override
    public ResultSet getProcedures(String catalog, String schemaPattern, String procedureNamePattern)
            throws SQLException {
        null
    }

    @Override
    public ResultSet getProcedureColumns(String catalog, String schemaPattern, String procedureNamePattern,
                                         String columnNamePattern) throws SQLException {
        null
    }

    @Override
    public ResultSet getTables(String catalog, String schemaPattern, String tableNamePattern, String[] types)
            throws SQLException {
        null
    }

    @Override
    public ResultSet getSchemas() throws SQLException {
        null
    }

    @Override
    public ResultSet getCatalogs() throws SQLException {
        null
    }

    @Override
    public ResultSet getTableTypes() throws SQLException {
        null
    }

    @Override
    public ResultSet getColumns(String catalog, String schemaPattern, String tableNamePattern, String columnNamePattern)
            throws SQLException {
        null
    }

    @Override
    public ResultSet getColumnPrivileges(String catalog, String schema, String table, String columnNamePattern)
            throws SQLException {
        null
    }

    @Override
    public ResultSet getTablePrivileges(String catalog, String schemaPattern, String tableNamePattern)
            throws SQLException {
        null
    }

    @Override
    public ResultSet getBestRowIdentifier(String catalog, String schema, String table, int scope, boolean nullable)
            throws SQLException {
        null
    }

    @Override
    public ResultSet getVersionColumns(String catalog, String schema, String table) throws SQLException {
        null
    }

    @Override
    public ResultSet getPrimaryKeys(String catalog, String schema, String table) throws SQLException {
        null
    }

    @Override
    public ResultSet getImportedKeys(String catalog, String schema, String table) throws SQLException {
        null
    }

    @Override
    public ResultSet getExportedKeys(String catalog, String schema, String table) throws SQLException {
        null
    }

    @Override
    public ResultSet getCrossReference(String parentCatalog, String parentSchema, String parentTable,
                                       String foreignCatalog, String foreignSchema, String foreignTable)
                                               throws SQLException {
        null
    }

    @Override
    public ResultSet getTypeInfo() throws SQLException {
        null
    }

    @Override
    public ResultSet getIndexInfo(String catalog, String schema, String table, boolean unique, boolean approximate)
            throws SQLException {
        null
    }

    @Override
    public boolean supportsResultSetType(int type) throws SQLException {
        false
    }

    @Override
    public boolean supportsResultSetConcurrency(int type, int concurrency) throws SQLException {
        false
    }

    @Override
    public boolean ownUpdatesAreVisible(int type) throws SQLException {
        false
    }

    @Override
    public boolean ownDeletesAreVisible(int type) throws SQLException {
        false
    }

    @Override
    public boolean ownInsertsAreVisible(int type) throws SQLException {
        false
    }

    @Override
    public boolean othersUpdatesAreVisible(int type) throws SQLException {
        false
    }

    @Override
    public boolean othersDeletesAreVisible(int type) throws SQLException {
        false
    }

    @Override
    public boolean othersInsertsAreVisible(int type) throws SQLException {
        false
    }

    @Override
    public boolean updatesAreDetected(int type) throws SQLException {
        false
    }

    @Override
    public boolean deletesAreDetected(int type) throws SQLException {
        false
    }

    @Override
    public boolean insertsAreDetected(int type) throws SQLException {
        false
    }

    @Override
    public boolean supportsBatchUpdates() throws SQLException {
        false
    }

    @Override
    public ResultSet getUDTs(String catalog, String schemaPattern, String typeNamePattern, int[] types)
            throws SQLException {
        null
    }

    @Override
    public Connection getConnection() throws SQLException {
        null
    }

    @Override
    public boolean supportsSavepoints() throws SQLException {
        false
    }

    @Override
    public boolean supportsNamedParameters() throws SQLException {
        false
    }

    @Override
    public boolean supportsMultipleOpenResults() throws SQLException {
        false
    }

    @Override
    public boolean supportsGetGeneratedKeys() throws SQLException {
        false
    }

    @Override
    public ResultSet getSuperTypes(String catalog, String schemaPattern, String typeNamePattern) throws SQLException {
        null
    }

    @Override
    public ResultSet getSuperTables(String catalog, String schemaPattern, String tableNamePattern) throws SQLException {
        null
    }

    @Override
    public ResultSet getAttributes(String catalog, String schemaPattern, String typeNamePattern,
                                   String attributeNamePattern) throws SQLException {
        null
    }

    @Override
    public boolean supportsResultSetHoldability(int holdability) throws SQLException {
        false
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        0
    }

    @Override
    public int getDatabaseMajorVersion() throws SQLException {
        connection.majorVersion ?: 0
    }

    @Override
    public int getDatabaseMinorVersion() throws SQLException {
        connection.minorVersion ?: 0
    }

    @Override
    public int getJDBCMajorVersion() throws SQLException {
        0
    }

    @Override
    public int getJDBCMinorVersion() throws SQLException {
        0
    }

    @Override
    public int getSQLStateType() throws SQLException {
        0
    }

    @Override
    public boolean locatorsUpdateCopy() throws SQLException {
        false
    }

    @Override
    public boolean supportsStatementPooling() throws SQLException {
        false
    }

    @Override
    public RowIdLifetime getRowIdLifetime() throws SQLException {
        null
    }

    @Override
    public ResultSet getSchemas(String catalog, String schemaPattern) throws SQLException {
        null
    }

    @Override
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        false
    }

    @Override
    public boolean autoCommitFailureClosesAllResultSets() throws SQLException {
        false
    }

    @Override
    public ResultSet getClientInfoProperties() throws SQLException {
        null
    }

    @Override
    public ResultSet getFunctions(String catalog, String schemaPattern, String functionNamePattern)
            throws SQLException {
        null
    }

    @Override
    public ResultSet getFunctionColumns(String catalog, String schemaPattern, String functionNamePattern,
                                        String columnNamePattern) throws SQLException {
        null
    }

    @Override
    public ResultSet getPseudoColumns(String catalog, String schemaPattern, String tableNamePattern,
                                      String columnNamePattern) throws SQLException {
        null
    }

    @Override
    public boolean generatedKeyAlwaysReturned() throws SQLException {
        false
    }
    
}