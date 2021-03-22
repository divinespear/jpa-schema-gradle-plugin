package test.spec

import org.gradle.testkit.runner.GradleRunner
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import java.io.File

abstract class FunctionalSpec(private val suffix: String? = "gradle") {
  @Rule
  @JvmField
  val testProjectDir: TemporaryFolder = TemporaryFolder()
  lateinit var projectName: String private set
  lateinit var propertiesFile: File private set
  lateinit var settingsFile: File private set
  lateinit var buildFile: File private set

  @Before
  fun setup() {
    projectName = testProjectDir.root.name
    propertiesFile = testProjectDir.newFile("gradle.properties")
    settingsFile = testProjectDir.newFile("settings.${suffix}").apply {
      printWriter().use {
        it.println("rootProject.name = \"$projectName\"")
      }
    }
    buildFile = testProjectDir.newFile("build.${suffix}").apply {
      printWriter().use {
        it.println("// Running test for $projectName")
      }
    }
  }

  fun runTask() = GradleRunner.create()
    .withProjectDir(testProjectDir.root)
    .withArguments("generateSchema", "--info", "--stacktrace")
    .withPluginClasspath()
    .withDebug(true)
    .build()

  fun resultFile(path: String) = testProjectDir.root.resolve(path)
}
