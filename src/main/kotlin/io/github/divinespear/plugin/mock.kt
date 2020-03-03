package io.github.divinespear.plugin

import java.sql.*
import java.util.*
import java.util.concurrent.Executor

class ConnectionMock(val productName: String,
                     val majorVersion: Int?,
                     val minorVersion: Int?) : Connection {

  override fun getMetaData(): DatabaseMetaData = DatabaseMetaDataMock(this)
  override fun getAutoCommit(): Boolean = true
  override fun setAutoCommit(autoCommit: Boolean) {}

  override fun prepareStatement(sql: String?): PreparedStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun prepareStatement(sql: String?, resultSetType: Int, resultSetConcurrency: Int): PreparedStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun prepareStatement(sql: String?, resultSetType: Int, resultSetConcurrency: Int, resultSetHoldability: Int): PreparedStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun prepareStatement(sql: String?, autoGeneratedKeys: Int): PreparedStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun prepareStatement(sql: String?, columnIndexes: IntArray?): PreparedStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun prepareStatement(sql: String?, columnNames: Array<out String>?): PreparedStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun rollback() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun rollback(savepoint: Savepoint?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getHoldability(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setNetworkTimeout(executor: Executor?, milliseconds: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun commit() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun <T : Any?> unwrap(iface: Class<T>?): T {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setTransactionIsolation(level: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun abort(executor: Executor?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun prepareCall(sql: String?): CallableStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun prepareCall(sql: String?, resultSetType: Int, resultSetConcurrency: Int): CallableStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun prepareCall(sql: String?, resultSetType: Int, resultSetConcurrency: Int, resultSetHoldability: Int): CallableStatement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getClientInfo(name: String?): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getClientInfo(): Properties {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setCatalog(catalog: String?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getWarnings(): SQLWarning {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getCatalog(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setHoldability(holdability: Int) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSchema(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isValid(timeout: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun close() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isClosed(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createNClob(): NClob {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createBlob(): Blob {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createArrayOf(typeName: String?, elements: Array<out Any>?): java.sql.Array {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setReadOnly(readOnly: Boolean) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isWrapperFor(iface: Class<*>?): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun nativeSQL(sql: String?): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createStruct(typeName: String?, attributes: Array<out Any>?): Struct {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setClientInfo(name: String?, value: String?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setClientInfo(properties: Properties?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun releaseSavepoint(savepoint: Savepoint?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createClob(): Clob {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isReadOnly(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createStatement(): Statement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createStatement(resultSetType: Int, resultSetConcurrency: Int): Statement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createStatement(resultSetType: Int, resultSetConcurrency: Int, resultSetHoldability: Int): Statement {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setSavepoint(): Savepoint {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setSavepoint(name: String?): Savepoint {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTypeMap(): MutableMap<String, Class<*>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun clearWarnings() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTransactionIsolation(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setSchema(schema: String?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getNetworkTimeout(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setTypeMap(map: MutableMap<String, Class<*>>?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun createSQLXML(): SQLXML {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}

class DatabaseMetaDataMock(private val connection: ConnectionMock) : DatabaseMetaData {

  override fun getConnection() = connection
  override fun getDriverName() = connection.productName
  override fun getDatabaseProductName() = connection.productName
  override fun getDatabaseMajorVersion(): Int = connection.majorVersion ?: 0
  override fun getDatabaseMinorVersion(): Int = connection.minorVersion ?: 0

  override fun supportsSubqueriesInQuantifieds(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsGetGeneratedKeys(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsCoreSQLGrammar(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxColumnsInIndex(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun insertsAreDetected(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsIntegrityEnhancementFacility(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getAttributes(catalog: String?, schemaPattern: String?, typeNamePattern: String?, attributeNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDatabaseProductVersion(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsOpenStatementsAcrossRollback(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxProcedureNameLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getCatalogTerm(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsCatalogsInDataManipulation(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxUserNameLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getJDBCMajorVersion(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTimeDateFunctions(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsStoredFunctionsUsingCallSyntax(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun autoCommitFailureClosesAllResultSets(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxColumnsInSelect(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getCatalogs(): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun storesLowerCaseQuotedIdentifiers(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsDataDefinitionAndDataManipulationTransactions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsCatalogsInTableDefinitions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxColumnsInOrderBy(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDriverMinorVersion(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun storesUpperCaseIdentifiers(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun nullsAreSortedLow(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSchemasInIndexDefinitions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxStatementLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsTransactions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsResultSetConcurrency(type: Int, concurrency: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isReadOnly(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun usesLocalFiles(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsResultSetType(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxConnections(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTables(catalog: String?, schemaPattern: String?, tableNamePattern: String?, types: Array<out String>?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsMultipleResultSets(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun dataDefinitionIgnoredInTransactions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getFunctions(catalog: String?, schemaPattern: String?, functionNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSearchStringEscape(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsGroupBy(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxTableNameLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun dataDefinitionCausesTransactionCommit(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsOpenStatementsAcrossCommit(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun ownInsertsAreVisible(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSchemaTerm(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isCatalogAtStart(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getFunctionColumns(catalog: String?, schemaPattern: String?, functionNamePattern: String?, columnNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsTransactionIsolationLevel(level: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun nullsAreSortedAtStart(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getPrimaryKeys(catalog: String?, schema: String?, table: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getProcedureTerm(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsANSI92IntermediateSQL(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsOuterJoins(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun <T : Any?> unwrap(iface: Class<T>?): T {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsLikeEscapeClause(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsPositionedUpdate(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsMixedCaseIdentifiers(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsLimitedOuterJoins(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSQLStateType(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSystemFunctions(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxRowSize(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsOpenCursorsAcrossRollback(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTableTypes(): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxTablesInSelect(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun nullsAreSortedHigh(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getURL(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsNamedParameters(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsConvert(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsConvert(fromType: Int, toType: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxStatements(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getProcedureColumns(catalog: String?, schemaPattern: String?, procedureNamePattern: String?, columnNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun allTablesAreSelectable(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getJDBCMinorVersion(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getCatalogSeparator(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSuperTypes(catalog: String?, schemaPattern: String?, typeNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxBinaryLiteralLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTypeInfo(): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getVersionColumns(catalog: String?, schema: String?, table: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsMultipleOpenResults(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deletesAreDetected(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsMinimumSQLGrammar(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxColumnsInGroupBy(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getNumericFunctions(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getExtraNameCharacters(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxCursorNameLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun nullsAreSortedAtEnd(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSchemasInDataManipulation(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSchemas(): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSchemas(catalog: String?, schemaPattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsCorrelatedSubqueries(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDefaultTransactionIsolation(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun locatorsUpdateCopy(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getColumns(catalog: String?, schemaPattern: String?, tableNamePattern: String?, columnNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getCrossReference(parentCatalog: String?, parentSchema: String?, parentTable: String?, foreignCatalog: String?, foreignSchema: String?, foreignTable: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun ownDeletesAreVisible(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun othersUpdatesAreVisible(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsStatementPooling(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun storesLowerCaseIdentifiers(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsCatalogsInIndexDefinitions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun ownUpdatesAreVisible(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getUDTs(catalog: String?, schemaPattern: String?, typeNamePattern: String?, types: IntArray?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getStringFunctions(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxColumnsInTable(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsColumnAliasing(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSchemasInProcedureCalls(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getClientInfoProperties(): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun usesLocalFilePerTable(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getIdentifierQuoteString(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsFullOuterJoins(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsOrderByUnrelated(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSchemasInTableDefinitions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsCatalogsInProcedureCalls(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getUserName(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getBestRowIdentifier(catalog: String?, schema: String?, table: String?, scope: Int, nullable: Boolean): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsTableCorrelationNames(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxIndexLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSubqueriesInExists(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxSchemaNameLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsANSI92EntryLevelSQL(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDriverVersion(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getPseudoColumns(catalog: String?, schemaPattern: String?, tableNamePattern: String?, columnNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsMixedCaseQuotedIdentifiers(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getProcedures(catalog: String?, schemaPattern: String?, procedureNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getDriverMajorVersion(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsANSI92FullSQL(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsAlterTableWithAddColumn(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsResultSetHoldability(holdability: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getColumnPrivileges(catalog: String?, schema: String?, table: String?, columnNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getImportedKeys(catalog: String?, schema: String?, table: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsUnionAll(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getRowIdLifetime(): RowIdLifetime {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun doesMaxRowSizeIncludeBlobs(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsGroupByUnrelated(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getIndexInfo(catalog: String?, schema: String?, table: String?, unique: Boolean, approximate: Boolean): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSubqueriesInIns(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsStoredProcedures(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getExportedKeys(catalog: String?, schema: String?, table: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsPositionedDelete(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsAlterTableWithDropColumn(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsExpressionsInOrderBy(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxCatalogNameLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsExtendedSQLGrammar(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun othersInsertsAreVisible(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun updatesAreDetected(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsDataManipulationTransactionsOnly(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSubqueriesInComparisons(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSavepoints(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSQLKeywords(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxColumnNameLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun nullPlusNonNullIsNull(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsGroupByBeyondSelect(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsCatalogsInPrivilegeDefinitions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun allProceduresAreCallable(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getSuperTables(catalog: String?, schemaPattern: String?, tableNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun generatedKeyAlwaysReturned(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun isWrapperFor(iface: Class<*>?): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun storesUpperCaseQuotedIdentifiers(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getMaxCharLiteralLength(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun othersDeletesAreVisible(type: Int): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsNonNullableColumns(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsUnion(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsDifferentTableCorrelationNames(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSchemasInPrivilegeDefinitions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsSelectForUpdate(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsMultipleTransactions(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun storesMixedCaseQuotedIdentifiers(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsOpenCursorsAcrossCommit(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun storesMixedCaseIdentifiers(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getTablePrivileges(catalog: String?, schemaPattern: String?, tableNamePattern: String?): ResultSet {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun supportsBatchUpdates(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getResultSetHoldability(): Int {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}
