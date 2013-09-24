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

import java.sql.Driver
import java.sql.DriverManager

import javax.persistence.Persistence

import org.eclipse.persistence.config.PersistenceUnitProperties
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.hibernate.dialect.Dialect
import org.hibernate.engine.jdbc.dialect.internal.StandardDatabaseInfoDialectResolver
import org.hibernate.engine.jdbc.dialect.spi.DatabaseInfoDialectResolver.DatabaseInfo
import org.hibernate.jpa.AvailableSettings

class JpaSchemaGenerateTask extends DefaultTask {

    List<SchemaGenerationConfig> getTargets() {
        def List<SchemaGenerationConfig> list = []

        project.generateSchema.targets.all { target ->
            list.add(new SchemaGenerationConfig(target.name, project.generateSchema, target))
        }
        if (list.empty) {
            list.add(project.generateSchema)
        }

        return list
    }

    private ClassLoader getProjectClassLoader(boolean scanTestClasses) {
        def classfiles = [] as Set
        // compiled classpath
        classfiles << project.sourceSets.main.output.classesDir
        classfiles << project.sourceSets.main.output.resourcesDir
        // include test classpath
        if (scanTestClasses) {
            classfiles << project.sourceSets.test.output.classesDir
            classfiles << project.sourceSets.test.output.resourcesDir
        }
        // convert to url
        def classURLs = []
        classfiles.each {
            classURLs << it.toURI().toURL()
        }
        return new URLClassLoader(classURLs.toArray(new URL[0]), this.class.classLoader)
    }

    private Map<String, Object> persistenceProperties(SchemaGenerationConfig target) {
        def Map<String, Object> map = [:]

        /*
         * common
         */
        // mode
        map[PersistenceUnitProperties.SCHEMA_GENERATION_DATABASE_ACTION] = target.databaseAction.toLowerCase()
        map[PersistenceUnitProperties.SCHEMA_GENERATION_SCRIPTS_ACTION] = target.scriptAction.toLowerCase()
        // output files
        if (target.scriptTarget) {
            if (target.outputDirectory == null) {
                throw new IllegalArgumentException("outputDirectory is REQUIRED for script generation.")
            }
            def outc = new File(target.outputDirectory, target.createOutputFileName).toURI().toString()
            def outd = new File(target.outputDirectory, target.dropOutputFileName).toURI().toString()
            map[PersistenceUnitProperties.SCHEMA_GENERATION_SCRIPTS_CREATE_TARGET] = outc
            map[PersistenceUnitProperties.SCHEMA_GENERATION_SCRIPTS_DROP_TARGET] = outd
        }
        // database emulation options
        map[PersistenceUnitProperties.SCHEMA_DATABASE_PRODUCT_NAME] = target.databaseProductName
        map[PersistenceUnitProperties.SCHEMA_DATABASE_MAJOR_VERSION] = target.databaseMajorVersion?.toString()
        map[PersistenceUnitProperties.SCHEMA_DATABASE_MINOR_VERSION] = target.databaseMinorVersion?.toString()
        // database options
        map[PersistenceUnitProperties.JDBC_DRIVER] = target.jdbcDriver
        map[PersistenceUnitProperties.JDBC_URL] = target.jdbcUrl
        map[PersistenceUnitProperties.JDBC_USER] = target.jdbcUser
        map[PersistenceUnitProperties.JDBC_PASSWORD] = target.jdbcPassword
        // source selection
        map[PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_SOURCE] = target.createSourceMode
        if (target.createSourceFile == null) {
            if (!PersistenceUnitProperties.SCHEMA_GENERATION_METADATA_SOURCE.equals(target.createSourceMode)) {
                throw new IllegalArgumentException("create source file is required for mode " + target.createSourceMode)
            }
        } else {
            map[PersistenceUnitProperties.SCHEMA_GENERATION_CREATE_SCRIPT_SOURCE] = target.createSourceFile.toURI().toString()
        }
        map[PersistenceUnitProperties.SCHEMA_GENERATION_DROP_SOURCE] = target.dropSourceMode
        if (target.dropSourceFile == null) {
            if (!PersistenceUnitProperties.SCHEMA_GENERATION_METADATA_SOURCE.equals(target.dropSourceMode)) {
                throw new IllegalArgumentException("drop source file is required for mode " + target.dropSourceMode)
            }
        } else {
            map[PersistenceUnitProperties.SCHEMA_GENERATION_DROP_SCRIPT_SOURCE] = target.dropSourceFile.toURI().toString()
        }

        /*
         * EclipseLink specific
         */
        // persistence.xml
        map[PersistenceUnitProperties.ECLIPSELINK_PERSISTENCE_XML] = target.persistenceXml

        /*
         * Hibernate specific
         */
        // naming strategy
        map[AvailableSettings.NAMING_STRATEGY] = target.namingStrategy
        // auto-detect
        map[AvailableSettings.AUTODETECTION] = "class,hbm"
        // dialect (without jdbc connection)
        if (target.dialect == null && target.jdbcUrl == null) {
            DatabaseInfo databaseInfo = new DatabaseInfo() {
                        @Override
                        public String getDatabaseName() {
                            return target.databaseProductName
                        }

                        @Override
                        public int getDatabaseMajorVersion() {
                            return target.databaseMajorVersion
                        }

                        @Override
                        public int getDatabaseMinorVersion() {
                            return target.databaseMinorVersion
                        }
                    }
            Dialect detectedDialect = new StandardDatabaseInfoDialectResolver().resolve(databaseInfo)
            target.dialect = detectedDialect.getClass().getName()
        }
        if (target.dialect != null) {
            map[org.hibernate.cfg.AvailableSettings.DIALECT] = target.dialect
        }

        return map
    }

    @TaskAction
    void generate() {
        this.getTargets().each { target ->
            // create output directory
            if (target.outputDirectory != null) {
                target.outputDirectory.mkdirs()
            }
            // get classloader
            def classloader = this.getProjectClassLoader(target.scanTestClasses)
            // load JDBC driver if necessary
            if (target.jdbcDriver?.length() > 0) {
                try {
                    def driver = classLoader.loadClass(target.jdbcDriver).newInstance() as Driver
                    DriverManager.registerDriver(driver)
                } catch (Exception e) {
                    throw new GradleException("Dependency for driver-class " + target.jdbcDriver + " is missing!", e)
                }
            }
            // generate
            def thread = Thread.currentThread()
            def contextClassLoader = thread.getContextClassLoader() as ClassLoader
            try {
                thread.setContextClassLoader(classloader)
                Persistence.generateSchema(target.persistenceUnitName, this.persistenceProperties(target))
            } finally {
                thread.setContextClassLoader(contextClassLoader)
            }
        }
    }
}
