package io.github.divinespear.gradle.plugin.config


class Configuration {

    /**
     * target name
     */
    final String name

    /**
     * skip schema generation
     * <p>
     * default is <code>false</code>.
     *
     * @category required
     */
    boolean skip = false

    /**
     * generate as formatted
     * <p>
     * default is <code>false</code>.
     *
     * @category required
     */
    boolean format = false

    /**
     * scan test classes
     * <p>
     * default is <code>false</code>.
     *
     * @category required
     */
    boolean scanTestClasses = false

    /**
     * location of <code>persistence.xml</code> file
     * <p>
     * default is <code>META-INF/persistence.xml</code>.
     * <p>
     * Note: Hibernate <b>DOES NOT SUPPORT custom location.</b> ({@link SchemaExport} support it, 
     * but JPA 2.1 schema generator does NOT.)
     *
     * @category required
     * @category Eclipselink
     * @category DataNucleus
     */
    String persistenceXml

    /**
     * unit name of <code>persistence.xml</code>
     * <p>
     * default is <code>default</code>.
     *
     * @category required
     */
    String persistenceUnitName

    /**
     * schema generation action for database
     * <p>
     * support value is <code>none</code>, <code>create</code>, <code>drop</code>, <code>drop-and-create</code>, or
     * <code>create-or-extend-tables</code>.
     * <p>
     * default is <code>none</code>.
     * <p>
     * Note: <code>create-or-extend-tables</code> only support for EclipseLink with database target.
     *
     * @category required
     */
    String databaseAction

    /**
     * schema generation action for script
     * <p>
     * support value is <code>none</code>, <code>create</code>, <code>drop</code>, or <code>drop-and-create</code>.
     * <p>
     * default is <code>none</code>.
     *
     * @category required
     */
    String scriptAction

    /**
     * output directory for generated ddl scripts
     * <p>
     * REQUIRED for {@link #scriptAction} is one of <code>create</code>, <code>drop</code>, or
     * <code>drop-and-create</code>.
     * <p>
     * default is <code>${project.buildDir}/generated-schema</code>.
     */
    File outputDirectory

    /**
     * generated create script name
     * <p>
     * REQUIRED for {@link #scriptAction} is one of <code>create</code>, or <code>drop-and-create</code>.
     * <p>
     * default is <code>create.sql</code>.
     */
    String createOutputFileName

    /**
     * generated drop script name
     * <p>
     * REQUIRED for {@link #scriptAction} is one of <code>drop</code>, or <code>drop-and-create</code>.
     * <p>
     * default is <code>drop.sql</code>.
     */
    String dropOutputFileName

    /**
     * specifies whether the creation of database artifacts is to occur on the basis of the object/relational mapping
     * metadata, DDL script, or a combination of the two.
     * <p>
     * support value is <code>metadata</code>, <code>script</code>, <code>metadata-then-script</code>, or
     * <code>script-then-metadata</code>.
     * <p>
     * default is <code>metadata</code>.
     */
    String createSourceMode

    /**
     * create source file path.
     * <p>
     * REQUIRED for {@link #createSourceMode} is one of <code>script</code>, <code>metadata-then-script</code>, or
     * <code>script-then-metadata</code>.
     */
    File createSourceFile

    /**
     * specifies whether the dropping of database artifacts is to occur on the basis of the object/relational mapping
     * metadata, DDL script, or a combination of the two.
     * <p>
     * support value is <code>metadata</code>, <code>script</code>, <code>metadata-then-script</code>, or
     * <code>script-then-metadata</code>.
     * <p>
     * default is <code>metadata</code>.
     */
    String dropSourceMode

    /**
     * drop source file path.
     * <p>
     * REQUIRED for {@link #dropSourceMode} is one of <code>script</code>, <code>metadata-then-script</code>, or
     * <code>script-then-metadata</code>.
     */
    File dropSourceFile

    /**
     * jdbc driver class name
     * <p>
     * default is declared class name in persistence xml.
     * <p>
     * and Remember, <strike><a href="http://callofduty.wikia.com/wiki/No_Russian" target="_blank">No
     * Russian</a></strike> you MUST configure jdbc driver as plugin's dependency.
     */
    String jdbcDriver

    /**
     * jdbc connection url
     * <p>
     * default is declared connection url in persistence xml.
     */
    String jdbcUrl

    /**
     * jdbc connection username
     * <p>
     * default is declared username in persistence xml.
     */
    String jdbcUser

    /**
     * jdbc connection password
     * <p>
     * default is declared password in persistence xml.
     * <p>
     * If your account has no password (especially local file-base, like Apache Derby, H2, etc...), it can be omitted.
     */
    String jdbcPassword

    /**
     * database product name for emulate database connection. this should useful for script-only action.
     * <ul>
     * <li>specified if scripts are to be generated by the persistence provider and a connection to the target database
     * is not supplied.</li>
     * <li>The value of this property should be the value returned for the target database by
     * {@link DatabaseMetaData#getDatabaseProductName()}</li>
     * </ul>
     */
    String databaseProductName

    /**
     * database major version for emulate database connection. this should useful for script-only action.
     * <ul>
     * <li>specified if sufficient database version information is not included from
     * {@link DatabaseMetaData#getDatabaseProductName()}</li>
     * <li>The value of this property should be the value returned for the target database by
     * {@link DatabaseMetaData#getDatabaseMajorVersion()}</li>
     * </ul>
     */
    Integer databaseMajorVersion

    /**
     * database minor version for emulate database connection. this should useful for script-only action.
     * <ul>
     * <li>specified if sufficient database version information is not included from
     * {@link DatabaseMetaData#getDatabaseProductName()}</li>
     * <li>The value of this property should be the value returned for the target database by
     * {@link DatabaseMetaData#getDatabaseMinorVersion()}</li>
     * </ul>
     */
    Integer databaseMinorVersion

    /**
     * line separator for generated schema file.
     * <p>
     * support value is one of <code>CRLF</code> (windows default), <code>LF</code> (*nix, max osx), and <code>CR</code>
     * (classic mac), in case-insensitive.
     * <p>
     * default value is system property <code>line.separator</code>. if JVM cannot detect <code>line.separator</code>,
     * then use <code>LF</code> by <a href="http://git-scm.com/book/en/Customizing-Git-Git-Configuration">git
     * <code>core.autocrlf</code> handling</a>.
     */
    String lineSeparator

    /**
     * JPA vendor-specific properties
     * <p>
     * @since 0.2.0
     */
    Map properties = [:]

    /**
     * JPA vendor
     * <p>
     * this property is for xml-less work (like spring-boot)
     * <p>
     * support value is <code>eclipselink</code>, or <code>hibernate</code>.
     * <p>
     * @since 0.2.0
     */
    String vendor

    /**
     * Packages to scan entities.
     * <p>
     * @since 0.2.0
     */
    List packageToScan = []

    Configuration() {
        this(null)
    }

    Configuration(String name) {
        this.name = name
    }

    Configuration(String name, Configuration base, Configuration target) {
        this(name)

        this.skip = (target?.skip ?: false) ? target.skip : base.skip
        this.format = (target?.format ?: false) ? target.format : base.format
        this.scanTestClasses = (target?.scanTestClasses ?: false) ? target.scanTestClasses : base.scanTestClasses

        this.persistenceXml = target?.persistenceXml ?: base.persistenceXml
        this.persistenceUnitName = target?.persistenceUnitName ?: base.persistenceUnitName

        this.databaseAction = target?.databaseAction ?: base.databaseAction
        this.scriptAction = target?.scriptAction ?: base.scriptAction

        this.outputDirectory = target?.outputDirectory ?: base.outputDirectory
        this.createOutputFileName = target?.createOutputFileName ?: (name + "-" + base.createOutputFileName)
        this.dropOutputFileName = target?.dropOutputFileName ?: (name + "-" + base.dropOutputFileName)

        this.createSourceMode = target?.createSourceMode ?: base.createSourceMode
        this.createSourceFile = target?.createSourceFile ?: base.createSourceFile
        this.dropSourceMode = target?.dropSourceMode ?: base.dropSourceMode
        this.dropSourceFile = target?.dropSourceFile ?: base.dropSourceFile

        this.jdbcDriver = target?.jdbcDriver ?: base.jdbcDriver
        this.jdbcUrl = target?.jdbcUrl ?: base.jdbcUrl
        this.jdbcUser = target?.jdbcUser ?: base.jdbcUser
        this.jdbcPassword = target?.jdbcPassword ?: base.jdbcPassword

        this.databaseProductName = target?.databaseProductName ?: base.databaseProductName
        this.databaseMajorVersion = target?.databaseMajorVersion ?: base.databaseMajorVersion
        this.databaseMinorVersion = target?.databaseMinorVersion ?: base.databaseMinorVersion

        this.lineSeparator = target?.lineSeparator ?: base.lineSeparator
        if (base.properties != null) {
            this.properties.putAll(base.properties)
        }
        if (target?.properties != null) {
            this.properties.putAll(target?.properties)
        }

        this.vendor = target?.vendor ?: base.vendor
        this.packageToScan = (base.packageToScan.toSet() + target?.packageToScan.toSet()).toList()
    }

    boolean isScriptTarget() {
        return !JAVAX_SCHEMA_GENERATION_NONE_ACTION.equalsIgnoreCase(scriptAction)
    }

    boolean isDatabaseTarget() {
        return !JAVAX_SCHEMA_GENERATION_NONE_ACTION.equalsIgnoreCase(databaseAction)
    }

    static final String JAVAX_SCHEMA_GENERATION_NONE_ACTION = 'none'
    static final String JAVAX_SCHEMA_GENERATION_DATABASE_ACTION = 'javax.persistence.schema-generation.database.action'
    static final String JAVAX_SCHEMA_GENERATION_SCRIPTS_ACTION = 'javax.persistence.schema-generation.scripts.action'
    static final String JAVAX_SCHEMA_GENERATION_SCRIPTS_CREATE_TARGET = 'javax.persistence.schema-generation.scripts.create-target'
    static final String JAVAX_SCHEMA_GENERATION_SCRIPTS_DROP_TARGET = 'javax.persistence.schema-generation.scripts.drop-target'
    static final String JAVAX_SCHEMA_DATABASE_PRODUCT_NAME = 'javax.persistence.database-product-name'
    static final String JAVAX_SCHEMA_DATABASE_MAJOR_VERSION = 'javax.persistence.database-major-version'
    static final String JAVAX_SCHEMA_DATABASE_MINOR_VERSION = 'javax.persistence.database-minor-version'
    static final String JAVAX_JDBC_DRIVER = 'javax.persistence.jdbc.driver'
    static final String JAVAX_JDBC_URL = 'javax.persistence.jdbc.url'
    static final String JAVAX_JDBC_USER = 'javax.persistence.jdbc.user'
    static final String JAVAX_JDBC_PASSWORD = 'javax.persistence.jdbc.password'
    static final String JAVAX_SCHEMA_GENERATION_METADATA_SOURCE = 'metadata'
    static final String JAVAX_SCHEMA_GENERATION_CREATE_SOURCE = 'javax.persistence.schema-generation.create-source'
    static final String JAVAX_SCHEMA_GENERATION_DROP_SOURCE = 'javax.persistence.schema-generation.drop-source'
    static final String JAVAX_SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE = 'javax.persistence.schema-generation.create-script-source'
    static final String JAVAX_SCHEMA_GENERATION_DROP_SCRIPT_SOURCE = 'javax.persistence.schema-generation.drop-script-source'
    static final String JAVAX_SCHEMA_GEN_CONNECTION = 'javax.persistence.schema-generation-connection'
    static final String JAVAX_VALIDATION_MODE = 'javax.persistence.validation.mode'
    static final String JAVAX_TRANSACTION_TYPE = 'javax.persistence.transactionType'
    static final String JAVAX_JTA_DATASOURCE = 'javax.persistence.jtaDataSource'
    static final String JAVAX_NON_JTA_DATASOURCE = 'javax.persistence.nonJtaDataSource'
    
    static final String ECLIPSELINK_PERSISTENCE_XML = 'eclipselink.persistencexml'
    static final String ECLIPSELINK_WEAVING = 'eclipselink.weaving'
    
    static final String HIBERNATE_AUTODETECTION = 'hibernate.archive.autodetection'
    static final String HIBERNATE_DIALECT = 'hibernate.dialect'
    
    static final String DATANUCLEUS_PERSISTENCE_XML = 'datanucleus.persistenceXmlFilename'
}
