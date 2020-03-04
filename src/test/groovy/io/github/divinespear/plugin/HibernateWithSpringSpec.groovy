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
package io.github.divinespear.plugin

import io.github.divinespear.test.IntegrationGroovySpec
import org.gradle.testkit.runner.TaskOutcome

class HibernateWithSpringSpec extends IntegrationGroovySpec {

  def setup() {
    buildFile << """
plugins {
  id 'io.github.divinespear.jpa-schema-generate'
  id 'io.spring.dependency-management' version '1.0.4.RELEASE'
  id 'org.springframework.boot' version '1.5.10.RELEASE'
}

repositories {
  mavenCentral()
}
"""
  }

  def 'should work on hibernate 5.2, with spring, without xml'() {
    given:
    propertiesFile << """
hibernate.version=5.2.16.Final
"""
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/src/java")
    }
    resources {
      srcDir file("../../../src/test/resources/src/resources/empty")
    }
  }
}

dependencies {
  compile 'org.springframework.boot:spring-boot-starter-data-jpa'
  runtime 'com.h2database:h2'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  vendor = 'hibernate+spring'
  packageToScan = [ 'io.github.divinespear.model' ]
  scriptAction = "drop-and-create"
  properties = [
    'hibernate.physical_naming_strategy' : 'org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy',
    'hibernate.id.new_generator_mappings': 'false'
  ]
  targets {
    h2script {
      databaseProductName = "H2"
      databaseMajorVersion = 1
      databaseMinorVersion = 4
      createOutputFileName = "h2-create.sql"
      dropOutputFileName = "h2-drop.sql"
    }
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = runSchemaGenerationTask()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.2.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("create table key_value_store")
      it.contains("create table many_column_table")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("drop table key_value_store")
      it.contains("drop table many_column_table")
    }
  }

  def 'should work on hibernate 5.1, with spring, without xml'() {
    given:
    propertiesFile << """
hibernate.version=5.1.13.Final
"""
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/src/java")
    }
    resources {
      srcDir file("../../../src/test/resources/src/resources/empty")
    }
  }
}

dependencies {
  compile 'org.springframework.boot:spring-boot-starter-data-jpa'
  runtime 'com.h2database:h2'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  vendor = 'hibernate+spring'
  packageToScan = [ 'io.github.divinespear.model' ]
  scriptAction = "drop-and-create"
  properties = [
    'hibernate.physical_naming_strategy' : 'org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy',
    'hibernate.id.new_generator_mappings': 'false'
  ]
  targets {
    h2script {
      databaseProductName = "H2"
      databaseMajorVersion = 1
      databaseMinorVersion = 4
      createOutputFileName = "h2-create.sql"
      dropOutputFileName = "h2-drop.sql"
    }
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = runSchemaGenerationTask()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.1.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("create table key_value_store")
      it.contains("create table many_column_table")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("drop table key_value_store")
      it.contains("drop table many_column_table")
    }
  }

  def 'should work on hibernate 5.0, with spring, without xml'() {
    given:
    propertiesFile << """
hibernate.version=5.0.12.Final
"""
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/src/java")
    }
    resources {
      srcDir file("../../../src/test/resources/src/resources/empty")
    }
  }
}

dependencies {
  compile 'org.springframework.boot:spring-boot-starter-data-jpa'
  runtime 'com.h2database:h2'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  vendor = 'hibernate'
  packageToScan = [ 'io.github.divinespear.model' ]
  scriptAction = "drop-and-create"
  properties = [
    'hibernate.physical_naming_strategy' : 'org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy',
    'hibernate.id.new_generator_mappings': 'false'
  ]
  targets {
    h2script {
      databaseProductName = "H2"
      databaseMajorVersion = 1
      databaseMinorVersion = 4
      createOutputFileName = "h2-create.sql"
      dropOutputFileName = "h2-drop.sql"
    }
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = runSchemaGenerationTask()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.0.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("create table key_value_store")
      it.contains("create table many_column_table")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("drop table key_value_store")
      it.contains("drop table many_column_table")
    }
  }

  def 'should work on hibernate 4.3, with spring, without xml'() {
    given:
    propertiesFile << """
hibernate.version=4.3.11.Final
"""
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/src/java")
    }
    resources {
      srcDir file("../../../src/test/resources/src/resources/empty")
    }
  }
}

dependencies {
  compile 'org.springframework.boot:spring-boot-starter-data-jpa'
  runtime 'com.h2database:h2'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  vendor = 'hibernate'
  packageToScan = [ 'io.github.divinespear.model' ]
  scriptAction = "drop-and-create"
  properties = [
    'hibernate.ejb.naming_strategy': 'org.springframework.boot.orm.jpa.hibernate.SpringNamingStrategy'
  ]
  targets {
    h2script {
      databaseProductName = "H2"
      databaseMajorVersion = 1
      databaseMinorVersion = 4
      createOutputFileName = "h2-create.sql"
      dropOutputFileName = "h2-drop.sql"
    }
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = runSchemaGenerationTask()

    then:
    result.output.contains("org.hibernate/hibernate-core/4.3.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("create table key_value_store")
      it.contains("create table many_column_table")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("drop table key_value_store")
      it.contains("drop table many_column_table")
    }
  }
}
