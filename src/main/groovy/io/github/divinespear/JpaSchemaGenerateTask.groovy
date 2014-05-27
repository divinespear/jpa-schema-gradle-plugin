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

import java.sql.Driver
import java.sql.DriverManager

import javax.persistence.Persistence

import org.eclipse.persistence.config.PersistenceUnitProperties
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.hibernate.engine.jdbc.dialect.internal.StandardDialectResolver
import org.hibernate.engine.jdbc.dialect.spi.DialectResolutionInfo
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

    ClassLoader getProjectClassLoader(boolean scanTestClasses) {
        def classfiles = [] as Set
        // compiled classpath
        classfiles += [
            project.sourceSets.main.output.classesDir,
            project.sourceSets.main.output.resourcesDir
        ]
        // include test classpath
        if (scanTestClasses) {
            classfiles += [
                project.sourceSets.test.output.classesDir,
                project.sourceSets.test.output.resourcesDir
            ]
        }
        // convert to url
        def classURLs = []
        classfiles.each {
            classURLs << it.toURI().toURL()
        }

        // dependency artifacts to url
        project.configurations.runtime.each {
            classURLs << it.toURI().toURL()
        }

        // logs
        classURLs.each {
            logger.info("  * classpath: " + it)
        }

        return new URLClassLoader(classURLs.toArray(new URL[0]), this.class.classLoader)
    }

    Map<String, Object> persistenceProperties(SchemaGenerationConfig target) {
        Map<String, Object> map = [:]

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
        if (target.dialect == null && (target.jdbcUrl ?: "").empty) {
            DialectResolutionInfo info = new DialectResolutionInfo() {
                        String getDriverName() { null };
                        int getDriverMajorVersion() { 0};
                        int getDriverMinorVersion() { 0 };
                        String getDatabaseName() { target.databaseProductName };
                        int getDatabaseMajorVersion() { target.databaseMajorVersion ?: 0 };
                        int getDatabaseMinorVersion() { target.databaseMinorVersion ?: 0 };
                    }
            def detectedDialect = StandardDialectResolver.INSTANCE.resolveDialect(info)
            target.dialect = detectedDialect.getClass().getName()
        }
        if (target.dialect != null) {
            map[org.hibernate.cfg.AvailableSettings.DIALECT] = target.dialect
        }
        // issue-3: pass mock connection
        if (!target.databaseTarget && (target.jdbcUrl ?: "").empty) {
            map[AvailableSettings.SCHEMA_GEN_CONNECTION] =  new ConnectionMock(target.databaseProductName, target.databaseMajorVersion, target.databaseMinorVersion)
        }
        // issue-5: pass "none" for avoid validation while schema generating
        map[AvailableSettings.VALIDATION_MODE] = "none"

        return map
    }

    private Map<String, String> LINE_SEPARAOR_MAP = ["CRLF": "\r\n", "LF": "\n", "CR": "\r"]

    void postProcess(SchemaGenerationConfig target) {
        if (target.outputDirectory == null) {
            return
        }

        final def linesep = LINE_SEPARAOR_MAP[target.lineSeparator?.toUpperCase()]?: (System.properties["line.separator"]?:"\n")

        def files = [
            new File(target.outputDirectory, target.createOutputFileName),
            new File(target.outputDirectory, target.dropOutputFileName)
        ]
        files.each { file ->
            if (file.exists()) {
                def tmp = File.createTempFile("script-", null, target.outputDirectory)
                try {
                    file.withReader { reader ->
                        def line = null
                        while ((line = reader.readLine()) != null) {
                            line.replaceAll(/(?i)((?:create|drop|alter)\s+(?:table|view|sequence))/, ";\$1").split(";").each {
                                def s = it?.trim() ?: ""
                                if (!s.empty) {
                                    tmp << (target.format ? format(s, linesep) : s) + ";" + linesep + (target.format ? linesep : "")
                                }
                            }
                        }
                    }
                } finally {
                    file.delete()
                    tmp.renameTo(file)
                }
            }
        }
    }

    String format(String s, String linesep) {
        s = s.replaceAll(/^([^(]+\()/, "\$1\r\n\t").replaceAll(/\)[^()]*$/, "\r\n\$0").replaceAll(/((?:[^(),\s]+|\S\([^)]+\)[^),]*),)\s*/, "\$1\r\n\t")
        def result = ""
        def completed = true
        if (s =~ /(?i)^create(\s+\S+)?\s+(?:table|view)/) {
            // create table/view
            s.split("\r\n").each {
                if (it =~ /^\S/) {
                    if (!completed) {
                        result += linesep
                    }
                    result += (it + linesep)
                } else if (completed) {
                    if (it =~ /^\s*[^(]+(?:[^(),\s]+|\S\([^)]+\)[^),]*),\s*$/) {
                        result += (it + linesep)
                    } else {
                        result += it
                        completed = false
                    }
                } else {
                    result += it.trim()
                    if (it =~ /[^)]+\).*$/) {
                        result += linesep
                        completed = true
                    }
                }
            }
        } else if (s =~  /(?i)^create(\s+\S+)?\s+index/) {
            // create index
            s.replaceAll(/(?i)^(create(\s+\S+)?\s+index\s+\S+)\s*/, '\$1\r\n\t').split("\r\n").each {
                if (result.isEmpty()) {
                    result += (it + linesep)
                } else if (completed) {
                    if (it =~ /^\s*[^(]+(?:[^(),\s]+|\S\([^)]+\)[^),]*),\s*$/) {
                        result += (it + linesep)
                    } else {
                        result += it
                        completed = false
                    }
                } else {
                    result += it.trim()
                    if (it =~ /[^)]+\).*$/) {
                        result += linesep
                        completed = true
                    }
                }
            }
            result = result.replaceAll(/(?i)(asc|desc)\s*(on)/, '\$2')
        } else if (s =~  /(?i)^alter\s+table/) {
            // alter table
            s.replaceAll(/(?i)^(alter\s+table\s+\S+)\s*/, '\$1\r\n\t').replaceAll(/(?i)\)\s*(references)/, ')\r\n\t\$1').split("\r\n").each {
                if (result.isEmpty()) {
                    result += (it + linesep)
                } else if (completed) {
                    if (it =~ /^\s*[^(]+(?:[^(),\s]+|\S\([^)]+\)[^),]*),\s*$/) {
                        result += (it + linesep)
                    } else {
                        result += it
                        completed = false
                    }
                } else {
                    result += it.trim()
                    if (it =~ /[^)]+\).*$/) {
                        result += linesep
                        completed = true
                    }
                }
            }
        } else {
            result = s
        }
        result.trim()
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
                def driver = classloader.loadClass(target.jdbcDriver).newInstance() as Driver
                DriverManager.registerDriver(driver)
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
            // post-process
            this.postProcess(target)
        }
    }
}
