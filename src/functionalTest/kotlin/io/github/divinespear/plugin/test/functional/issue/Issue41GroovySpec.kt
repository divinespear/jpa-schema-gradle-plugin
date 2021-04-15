package io.github.divinespear.plugin.test.functional.issue

import io.github.divinespear.plugin.test.GroovyFunctionalSpec
import io.kotest.matchers.and
import io.kotest.matchers.file.exist
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.contain
import org.gradle.testkit.runner.TaskOutcome

class Issue41GroovySpec : GroovyFunctionalSpec() {
  init {
    "task" should {
      "work with Hibernate 5.4.7 or higher" {
        buildFile.appendText(
          """
            plugins {
              id 'io.github.divinespear.jpa-schema-generate'
              id 'io.spring.dependency-management' version '1.0.9.RELEASE'
              id 'org.springframework.boot' version '2.2.5.RELEASE'
            }
            
            repositories {
              mavenCentral()
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
          """.trimIndent()
        )

        val result = runGenerateSchemaTask()
        result.task(":generateSchema") should {
          it?.outcome shouldBe TaskOutcome.SUCCESS
        }
        resultFile("build/generated-schema/initial-create.sql") should {
          exist()
          it.readText() should (contain("create table key_value_store") and contain("create table many_column_table"))
        }
      }
    }
  }
}
