package test.plugin

import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.contain
import test.spec.KotlinFunctionalSpec

class KotlinFunctionalPluginTest : KotlinFunctionalSpec() {
  init {
    test("task should be registered") {
      buildFile.writeText(
        """
        plugins {
          id("io.github.divinespear.jpa-schema-generate")
          id("org.springframework.boot") version ("1.5.10.RELEASE")
        }

        repositories {
          mavenCentral()
        }
        """.trimIndent()
      )

      val result = runTask("tasks")
      result.output shouldBe contain("generateSchema")
    }

    test("task and extension should appears in kotlinDslAccessorsReport") {
      buildFile.writeText(
        """
        plugins {
          `kotlin-dsl`
          id("io.github.divinespear.jpa-schema-generate")
          id("org.springframework.boot") version ("1.5.10.RELEASE")
        }

        repositories {
          mavenCentral()
        }
        """.trimIndent()
      )

      val result = runTask("kotlinDslAccessorsReport")
      result.output should {
        contain("generateSchema")
        contain("JpaSchemaGenerationExtension")
        contain("JpaSchemaGenerationTask")
      }
    }
  }
}
