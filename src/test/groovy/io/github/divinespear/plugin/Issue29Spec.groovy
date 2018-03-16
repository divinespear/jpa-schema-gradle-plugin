package io.github.divinespear.plugin

import io.github.divinespear.test.IntegrationSpec
import org.gradle.testkit.runner.TaskOutcome

class Issue29Spec extends IntegrationSpec {

  def setup() {
  }

  def 'should have spring dependencies - spring-boot 1.5.10'() {
    given:
    buildFile << """
plugins {
  id 'io.github.divinespear.jpa-schema-generate'
  id 'org.springframework.boot' version '1.5.10.RELEASE'
}

repositories {
  mavenCentral()
}

sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src-spring")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/empty")
    }
  }
}

dependencies {
  compile 'org.springframework.boot:spring-boot-starter-data-jpa'
  runtime 'com.h2database:h2:1.4.191'
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
  }
}
"""
    when:
    def result = runSchemaGenerationTask()

    then:
    result.output.with {
      it.contains("org.springframework/spring-aspects/")
      it.contains("org.springframework/spring-context/")
    }
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

  def 'should have spring dependencies - spring-boot 2.0.0'() {
    given:
    buildFile << """
plugins {
  id 'io.github.divinespear.jpa-schema-generate'
  id 'io.spring.dependency-management' version '1.0.4.RELEASE'
  id 'org.springframework.boot' version '2.0.0.RELEASE'
}

repositories {
  mavenCentral()
}

sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src-spring")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/empty")
    }
  }
}

dependencies {
  compile 'org.springframework.boot:spring-boot-starter-data-jpa'
  runtime 'com.h2database:h2:1.4.191'
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
  }
}
"""
    when:
    def result = runSchemaGenerationTask()

    then:
    result.output.with {
      it.contains("org.springframework/spring-aspects/")
      it.contains("org.springframework/spring-context/")
    }
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
