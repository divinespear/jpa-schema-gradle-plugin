package io.github.divinespear

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

import org.eclipse.persistence.config.PersistenceUnitProperties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

class JpaSchemaGeneratePlugin implements Plugin<Project> {

    void apply(Project project) {
        project.plugins.apply(JavaPlugin)
        // tasks
        def task = project.task("generateSchema", type: JpaSchemaGenerateTask)
        task.dependsOn(project.tasks.classes)
        // plugin extensions
        project.extensions.create("generateSchema", SchemaGenerationConfig)
        project.generateSchema {
            skip = false

            persistenceXml = PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML_DEFAULT
            persistenceUnitName = "default"

            databaseAction = PersistenceUnitProperties.SCHEMA_GENERATION_NONE_ACTION
            scriptAction = PersistenceUnitProperties.SCHEMA_GENERATION_NONE_ACTION

            outputDirectory = new File(project.buildDir, "generated-schema")
            createOutputFileName = "create.sql"
            dropOutputFileName = "drop.sql"

            createSourceMode = PersistenceUnitProperties.SCHEMA_GENERATION_METADATA_SOURCE
            dropSourceMode = PersistenceUnitProperties.SCHEMA_GENERATION_METADATA_SOURCE
        }
        project.generateSchema.extensions.targets = project.container(SchemaGenerationConfig)
    }
}

class SchemaGenerationConfig {
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
     * Note for Hibernate: <b>current version (4.3.0.beta3) DOES NOT SUPPORT custom location.</b> ({@link SchemaExport}
     * support it, but JPA 2.1 schema generator does NOT.)
     * 
     * @category required
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
     * naming strategy that implements {@link org.hibernate.cfg.NamingStrategy}
     * <p>
     * this is Hibernate-only option.
     * 
     * @category Hibernate
     */
    String namingStrategy

    /**
     * dialect class
     * <p>
     * use this parameter if you want use custom dialect class. default is detect from JDBC connection or using
     * {@link #databaseProductName}, {@link #databaseMajorVersion}, and {@link #databaseMinorVersion}.
     * <p>
     * this is Hibernate-only option.
     * 
     * @category Hibernate
     */
    String dialect

    SchemaGenerationConfig() {
        this(null)
    }

    SchemaGenerationConfig(String name) {
        this.name = name
    }

    SchemaGenerationConfig(String name, SchemaGenerationConfig base, SchemaGenerationConfig target) {
        this(name)

        this.skip = (target?.skip ?: false) ? target.skip : base.skip
        this.scanTestClasses = (target?.scanTestClasses ?: false) ? target.scanTestClasses : base.scanTestClasses

        this.persistenceXml = target?.persistenceXml ?: base.persistenceXml
        this.persistenceUnitName = target?.persistenceUnitName ?: base.persistenceUnitName

        this.databaseAction = target?.databaseAction ?: base.databaseAction
        this.scriptAction = target?.scriptAction ?: base.scriptAction

        this.outputDirectory = target?.outputDirectory ?: base.outputDirectory
        this.createOutputFileName = target?.createOutputFileName ?: base.createOutputFileName
        this.dropOutputFileName = target?.dropOutputFileName ?: base.dropOutputFileName

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

        this.namingStrategy = target?.namingStrategy ?: base.namingStrategy
        this.dialect = target?.dialect ?: base.dialect
    }

    boolean isScriptTarget() {
        return !PersistenceUnitProperties.SCHEMA_GENERATION_NONE_ACTION.equalsIgnoreCase(scriptAction)
    }
}
