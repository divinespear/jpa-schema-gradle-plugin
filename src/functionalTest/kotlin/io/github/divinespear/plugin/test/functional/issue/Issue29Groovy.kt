package io.github.divinespear.plugin.test.functional.issue

import io.github.divinespear.plugin.test.GroovyFunctionalSpec
import io.kotest.matchers.and
import io.kotest.matchers.file.exist
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.contain
import org.gradle.testkit.runner.TaskOutcome
import java.io.File

class Issue29Groovy : GroovyFunctionalSpec() {

  private fun script(version: String) = """
    plugins {
      id 'java-library'
      id 'io.github.divinespear.jpa-schema-generate'
      id 'io.spring.dependency-management' version '1.0.10.RELEASE'
      id 'org.springframework.boot' version '$version'
    }
    
    repositories {
      mavenCentral()
    }
    
    dependencies {
      compile 'org.springframework.boot:spring-boot-starter-data-jpa'
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
    """.trimIndent()

  init {
    beforeTest {
      val mainJavaDir = testProjectDir.resolve("src/main/java").apply {
        mkdirs()
      }
      val resourceJavaDir = File("src/functionalTest/resources/java")
      resourceJavaDir.copyRecursively(mainJavaDir, true)
    }

    "spring-boot 1.5.10" should {
      "have spring dependencies" {
        buildFile.writeText(script("1.5.10.RELEASE"))

        val result = runGenerateSchemaTask()
        result.task(":generateSchema") should {
          it?.outcome shouldBe TaskOutcome.SUCCESS
        }
        result.output should (contain("org.springframework/spring-aspects/") and contain("org.springframework/spring-context/"))
        resultFile("build/generated-schema/h2-create.sql") should {
          exist()
          it.readText() should (contain("create table key_value_store") and contain("create table many_column_table"))
        }
        resultFile("build/generated-schema/h2-drop.sql") should {
          exist()
          it.readText() should (contain("drop table key_value_store") and contain("drop table many_column_table"))
        }
      }
    }

    "spring-boot 2.0.0" should {
      "have spring dependencies" {
        buildFile.writeText(script("2.0.0.RELEASE"))

        val result = runGenerateSchemaTask()
        result.task(":generateSchema").should {
          it?.outcome shouldBe TaskOutcome.SUCCESS
        }
        result.output should (contain("org.springframework/spring-aspects/") and contain("org.springframework/spring-context/"))
        resultFile("build/generated-schema/h2-create.sql") should {
          exist()
          it.readText() should (contain("create table key_value_store") and contain("create table many_column_table"))
        }
        resultFile("build/generated-schema/h2-drop.sql") should {
          exist()
          it.readText() should (contain("drop table key_value_store") and contain("drop table many_column_table"))
        }
      }
    }
  }
}
