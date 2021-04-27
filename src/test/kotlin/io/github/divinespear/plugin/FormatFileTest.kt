package io.github.divinespear.plugin

import com.github.difflib.DiffUtils
import io.kotest.core.spec.style.WordSpec
import io.kotest.core.spec.tempfile
import io.kotest.matchers.and
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.nulls.beNull
import io.kotest.matchers.paths.aFile
import io.kotest.matchers.paths.beReadable
import io.kotest.matchers.paths.exist
import io.kotest.matchers.should
import io.kotest.matchers.shouldNot
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class FormatFileTest : WordSpec() {
  companion object {
    val LINE_SEPARATOR = System.getProperty("line.separator") ?: "\n";
  }

  private lateinit var actual: File

  private fun resourcePath(name: String): Path {
    val path = this.javaClass.getResource(name)?.let {
      Paths.get(it.file)
    }
    path shouldNot beNull()
    path!! should (exist() and aFile() and beReadable())

    return path.normalize()
  }

  init {
    beforeTest {
      actual = tempfile("format-test")
    }

    "eclipselink result file" should {
      "be formatted" {
        val source = resourcePath("/eclipselink-normal-result.txt")
        val expected = resourcePath("/eclipselink-format-result.txt")
        val actualPath = actual.toPath()

        Files.copy(source, actualPath, StandardCopyOption.REPLACE_EXISTING)
        formatFile(actualPath, true, LINE_SEPARATOR)

        val diff = DiffUtils.diff(Files.readAllLines(expected), Files.readAllLines(actualPath))
        diff.deltas should beEmpty()
      }
    }

    "hibernate result file" should {
      "be formatted" {
        val source = resourcePath("/hibernate-normal-result.txt")
        val expected = resourcePath("/hibernate-format-result.txt")
        val actualPath = actual.toPath()

        Files.copy(source, actualPath, StandardCopyOption.REPLACE_EXISTING)
        formatFile(actualPath, true, LINE_SEPARATOR)

        val diff = DiffUtils.diff(Files.readAllLines(expected), Files.readAllLines(actualPath))
        diff.deltas should beEmpty()
      }
    }
  }
}
