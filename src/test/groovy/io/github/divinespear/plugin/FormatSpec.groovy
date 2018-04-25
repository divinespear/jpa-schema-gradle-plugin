package io.github.divinespear.plugin

import io.github.divinespear.test.IntegrationSpec
import org.gradle.testkit.runner.TaskOutcome
import spock.lang.Specification

import static io.github.divinespear.plugin.JpaSchemaGenerationOutputFormatter.formatLine

class FormatSpec extends Specification {

  static final String LINE_SEPARATOR = System.properties["line.separator"] ?: "\n"

  def 'should format create table'() {
    setup:
    def source
    def expected

    when: "create table"
    source = "CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (CREATED_DATE DATETIME NULL,LAST_MODIFIED_DATE DATETIME NULL,RATE NUMERIC(28) NULL,VERSION NUMERIC(19) NOT NULL,REFERENCE_ID VARCHAR(255) NOT NULL,CREATED_BY VARCHAR(36) NULL,LAST_MODIFIED_BY VARCHAR(36) NULL,PRIMARY KEY (VERSION,REFERENCE_ID));"
    expected = """CREATE TABLE SYSTEM_CURRENCY_RATE_HISTORY (
\tCREATED_DATE DATETIME NULL,
\tLAST_MODIFIED_DATE DATETIME NULL,
\tRATE NUMERIC(28) NULL,
\tVERSION NUMERIC(19) NOT NULL,
\tREFERENCE_ID VARCHAR(255) NOT NULL,
\tCREATED_BY VARCHAR(36) NULL,
\tLAST_MODIFIED_BY VARCHAR(36) NULL,
\tPRIMARY KEY (VERSION,REFERENCE_ID)
);"""
    then:
    formatLine(source, LINE_SEPARATOR) == expected

    when: "create table with option"
    source = "CREATE TEMPORARY TABLE SYSTEM_CURRENCY_RATE_HISTORY (CREATED_DATE DATETIME NULL,LAST_MODIFIED_DATE DATETIME NULL,RATE NUMERIC(28) NULL,VERSION NUMERIC(19) NOT NULL,REFERENCE_ID VARCHAR(255) NOT NULL,CREATED_BY VARCHAR(36) NULL,LAST_MODIFIED_BY VARCHAR(36) NULL,PRIMARY KEY (VERSION,REFERENCE_ID));"
    expected = """CREATE TEMPORARY TABLE SYSTEM_CURRENCY_RATE_HISTORY (
\tCREATED_DATE DATETIME NULL,
\tLAST_MODIFIED_DATE DATETIME NULL,
\tRATE NUMERIC(28) NULL,
\tVERSION NUMERIC(19) NOT NULL,
\tREFERENCE_ID VARCHAR(255) NOT NULL,
\tCREATED_BY VARCHAR(36) NULL,
\tLAST_MODIFIED_BY VARCHAR(36) NULL,
\tPRIMARY KEY (VERSION,REFERENCE_ID)
);"""
    then:
    formatLine(source, LINE_SEPARATOR) == expected
  }

  def 'should format create index'() {
    setup:
    def source
    def expected

    when: "create index"
    source = "CREATE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED ON USER_ACCOUNT (ENABLED,DELETED);"
    expected = """CREATE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED
\tON USER_ACCOUNT (ENABLED,DELETED);"""
    then:
    formatLine(source, LINE_SEPARATOR) == expected

    when: "create index with option"
    source = "CREATE UNIQUE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED ON USER_ACCOUNT (ENABLED,DELETED);"
    expected = """CREATE UNIQUE INDEX INDEX_USER_ACCOUNT_ENABLED_DELETED
\tON USER_ACCOUNT (ENABLED,DELETED);"""
    then:
    formatLine(source, LINE_SEPARATOR) == expected

    when: "given illegal syntax (issue #9)"
    source = "CREATE INDEX INDEX_SYSTEM_CURRENCY_RATE_VERSION DESC ON SYSTEM_CURRENCY_RATE (VERSION DESC);"
    expected = """CREATE INDEX INDEX_SYSTEM_CURRENCY_RATE_VERSION
\tON SYSTEM_CURRENCY_RATE (VERSION DESC);"""
    then:
    formatLine(source, LINE_SEPARATOR) == expected
  }
}

class FormatIntegrationSpec extends IntegrationSpec {

  static final String LINE_SEPARATOR = System.properties["line.separator"] ?: "\n"

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

  def 'should work on eclipselink 2.7, without xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/src/java")
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
  format = true
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
  }
}
"""
    when:
    def result = runSchemaGenerationTask()

    then:
    result.output.contains("org.eclipse.persistence/org.eclipse.persistence.jpa/2.7.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("CREATE TABLE KEY_VALUE_STORE (" + LINE_SEPARATOR)
      it.contains("CREATE TABLE MANY_COLUMN_TABLE (" + LINE_SEPARATOR)
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("DROP TABLE KEY_VALUE_STORE;")
      it.contains("DROP TABLE MANY_COLUMN_TABLE;")
    }
  }

  def 'should work on hibernate 5.2, without xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/src/java")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/empty")
    }
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[5.2,5.3)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
  format = true
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
    result.output.contains("org.hibernate/hibernate-core/5.2.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
    getResultFile("build/generated-schema/h2-create.sql").text.with {
      it.contains("create table key_value_store (" + LINE_SEPARATOR)
      it.contains("create table many_column_table (" + LINE_SEPARATOR)
    }
    getResultFile("build/generated-schema/h2-drop.sql").text.with {
      it.contains("drop table key_value_store if exists;")
      it.contains("drop table many_column_table if exists;")
    }
  }

}