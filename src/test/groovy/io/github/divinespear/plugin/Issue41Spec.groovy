package io.github.divinespear.plugin

import io.github.divinespear.test.IntegrationSpec
import org.gradle.testkit.runner.TaskOutcome

class Issue41Spec extends IntegrationSpec {

  def setup() {
  }

  def 'should work with Hibernate 5.4.7 or higher'() {
    given:
    buildFile << """
plugins {
  id 'io.github.divinespear.jpa-schema-generate'
  id 'io.spring.dependency-management' version '1.0.9.RELEASE'
  id 'org.springframework.boot' version '2.2.5.RELEASE'
}

repositories {
  mavenCentral()
}

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
  //compile 'org.hibernate:hibernate-entitymanager:5.4.7.Final'
  runtime 'org.mariadb.jdbc:mariadb-java-client:2.+'
}

generateSchema {
  vendor = 'hibernate+spring'
  databaseProductName = 'MariaDB'
  databaseMajorVersion = 10
  databaseMinorVersion = 3
  databaseAction = 'none'
  scriptAction = 'create'
  format = true
  jdbcDriver = 'org.mariadb.jdbc.Driver'
  createOutputFileName = 'initial-create.sql'
  packageToScan = [ 'io.github.divinespear.model' ]
  properties = [
    'hibernate.dialect': 'org.hibernate.dialect.MariaDB103Dialect',
    'hibernate.physical_naming_strategy': 'org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy',
    'hibernate.implicit_naming_strategy': 'org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy'
  ]
}
"""
    when:
    def result = runSchemaGenerationTask()

    then:
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/initial-create.sql").text.with {
      it.contains("create table key_value_store")
      it.contains("create table many_column_table")
    }
  }
}
