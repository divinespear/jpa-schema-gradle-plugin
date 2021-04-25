package io.github.divinespear.plugin.test.functional.hibernatespring

import io.github.divinespear.plugin.test.KotlinFunctionalSpec
import io.github.divinespear.plugin.test.helper.HIBERNATE_4_KOTLIN_PROPERTIES
import io.github.divinespear.plugin.test.helper.HIBERNATE_5_KOTLIN_PROPERTIES
import io.github.divinespear.plugin.test.helper.runHibernateSpringTask
import io.kotest.matchers.should
import io.kotest.matchers.string.contain

class HibernateSpringKotlinSpec : KotlinFunctionalSpec() {

  private fun script(properties: String) =
    """
      
      plugins {
        id("io.github.divinespear.jpa-schema-generate")
        id("io.spring.dependency-management") version("1.0.10.RELEASE")
        id("org.springframework.boot") version("1.5.10.RELEASE")
      }
      
      repositories {
        mavenCentral()
      }
      
      dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtimeOnly("com.h2database:h2")
        runtimeOnly(fileTree(projectDir.relativeTo(file("lib"))) { include("*.jar") })
      }
      
      generateSchema {
        vendor = "hibernate+spring"
        packageToScan = setOf("io.github.divinespear.model")
        scriptAction = "drop-and-create"
        properties = $properties
        targets {
          create("h2script") {
            databaseProductName = "H2"
            databaseMajorVersion = 1
            databaseMinorVersion = 4
            createOutputFileName = "h2-create.sql"
            dropOutputFileName = "h2-drop.sql"
          }
          create("h2database") {
            databaseAction = "drop-and-create"
            scriptAction = null
            jdbcDriver = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:${'$'}{buildDir}/generated-schema/test"
            jdbcUser = "sa"
          }
        }
      }
    """.trimIndent()

  init {
    "task for spring-boot without persistence.xml" should {
      "work on hibernate 5.2" {
        runHibernateSpringTask(
          "5.2.16.Final",
          script(HIBERNATE_5_KOTLIN_PROPERTIES)
        ) {
          it.output should contain("org.hibernate/hibernate-core/5.2.")
        }
      }
      "work on hibernate 5.1" {
        runHibernateSpringTask(
          "5.1.13.Final",
          script(HIBERNATE_5_KOTLIN_PROPERTIES)
        ) {
          it.output should contain("org.hibernate/hibernate-core/5.1.")
        }
      }
      "work on hibernate 5.0" {
        runHibernateSpringTask(
          "5.0.12.Final",
          script(HIBERNATE_5_KOTLIN_PROPERTIES)
        ) {
          it.output should contain("org.hibernate/hibernate-core/5.0.")
        }
      }
      "work on hibernate 4.3" {
        runHibernateSpringTask(
          "4.3.11.Final",
          script(HIBERNATE_4_KOTLIN_PROPERTIES)
        ) {
          it.output should contain("org.hibernate/hibernate-core/4.3.")
        }
      }
    }
  }
}
