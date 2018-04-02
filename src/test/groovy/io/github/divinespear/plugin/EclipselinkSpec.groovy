package io.github.divinespear.plugin

import io.github.divinespear.test.IntegrationSpec
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Ignore

class EclipselinkSpec extends IntegrationSpec {

  def setup() {
    buildFile << """
plugins {
  id 'io.github.divinespear.jpa-schema-generate'
}

repositories {
  mavenCentral()
}
"""
  }

  def 'should work on eclipselink 2.7, with xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/eclipselink")
    }
  }
}

dependencies {
  compile 'org.eclipse.persistence:org.eclipse.persistence.jpa:[2.7,2.8)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  scriptAction = "drop-and-create"
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
    result.output.contains("org.eclipse.persistence/org.eclipse.persistence.jpa/2.7.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("CREATE TABLE KEY_VALUE_STORE")
      it.contains("CREATE TABLE MANY_COLUMN_TABLE")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("DROP TABLE KEY_VALUE_STORE")
      it.contains("DROP TABLE MANY_COLUMN_TABLE")
    }
  }

  def 'should work on eclipselink 2.7, without xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/empty")
    }
  }
}

dependencies {
  compile 'org.eclipse.persistence:org.eclipse.persistence.jpa:[2.7,2.8)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  vendor = 'eclipselink'
  packageToScan = [ 'io.github.divinespear.model' ]
  scriptAction = "drop-and-create"
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
    result.output.contains("org.eclipse.persistence/org.eclipse.persistence.jpa/2.7.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("CREATE TABLE KEY_VALUE_STORE")
      it.contains("CREATE TABLE MANY_COLUMN_TABLE")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("DROP TABLE KEY_VALUE_STORE")
      it.contains("DROP TABLE MANY_COLUMN_TABLE")
    }
  }

  def 'should work on eclipselink 2.6, with xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/eclipselink")
    }
  }
}

dependencies {
  compile 'org.eclipse.persistence:org.eclipse.persistence.jpa:[2.6,2.7)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  scriptAction = "drop-and-create"
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
    result.output.contains("org.eclipse.persistence/org.eclipse.persistence.jpa/2.6.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("CREATE TABLE KEY_VALUE_STORE")
      it.contains("CREATE TABLE MANY_COLUMN_TABLE")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("DROP TABLE KEY_VALUE_STORE")
      it.contains("DROP TABLE MANY_COLUMN_TABLE")
    }
  }

  def 'should work on eclipselink 2.6, without xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/empty")
    }
  }
}

dependencies {
  compile 'org.eclipse.persistence:org.eclipse.persistence.jpa:[2.6,2.7)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  vendor = 'eclipselink'
  packageToScan = [ 'io.github.divinespear.model' ]
  scriptAction = "drop-and-create"
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
    result.output.contains("org.eclipse.persistence/org.eclipse.persistence.jpa/2.6.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("CREATE TABLE KEY_VALUE_STORE")
      it.contains("CREATE TABLE MANY_COLUMN_TABLE")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("DROP TABLE KEY_VALUE_STORE")
      it.contains("DROP TABLE MANY_COLUMN_TABLE")
    }
  }

  def 'should work on eclipselink 2.5, with xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/eclipselink")
    }
  }
}

dependencies {
  compile 'org.eclipse.persistence:org.eclipse.persistence.jpa:[2.5,2.6)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  scriptAction = "drop-and-create"
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
    result.output.contains("org.eclipse.persistence/org.eclipse.persistence.jpa/2.5.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("CREATE TABLE KEY_VALUE_STORE")
      it.contains("CREATE TABLE MANY_COLUMN_TABLE")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("DROP TABLE KEY_VALUE_STORE")
      it.contains("DROP TABLE MANY_COLUMN_TABLE")
    }
  }

  @Ignore("eclipselink 2.5 throws ClassCastException.")
  def 'should work on eclipselink 2.5, without xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/empty")
    }
  }
}

dependencies {
  compile 'org.eclipse.persistence:org.eclipse.persistence.jpa:[2.5,2.6)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  vendor = 'eclipselink'
  packageToScan = [ 'io.github.divinespear.model' ]
  scriptAction = "drop-and-create"
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
    result.output.contains("org.eclipse.persistence/org.eclipse.persistence.jpa/2.5.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("CREATE TABLE KEY_VALUE_STORE")
      it.contains("CREATE TABLE MANY_COLUMN_TABLE")
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("DROP TABLE KEY_VALUE_STORE")
      it.contains("DROP TABLE MANY_COLUMN_TABLE")
    }
  }

}
