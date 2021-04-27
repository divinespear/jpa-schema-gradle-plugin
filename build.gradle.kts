/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

plugins {
  `java-gradle-plugin`
  `kotlin-dsl`
  `maven-publish`
  id("com.gradle.plugin-publish") version ("0.12.0")
  jacoco
}

group = "io.github.divinespear"
version = "0.4.0"

repositories {
  jcenter()
}

val functionalTest: SourceSet by sourceSets.creating {
  compileClasspath += configurations.testRuntimeClasspath.get() + sourceSets.main.get().output
  runtimeClasspath += output + compileClasspath
}

val jacocoRuntime: Configuration by configurations.creating

dependencies {
  implementation("com.github.vertical-blank:sql-formatter:2.0.0")
  // jaxb (removed from java 9+)
  api("javax.xml.bind:jaxb-api:2.3.0")
  // jpa
  api("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
  "functionalTestApi"("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
  // test
  testImplementation(gradleTestKit())
  testImplementation("io.github.java-diff-utils:java-diff-utils:4.5")
  testImplementation("io.kotest:kotest-runner-junit5:4.2.6")
  // functional test
  "functionalTestImplementation"(gradleTestKit())
  "functionalTestImplementation"(files("$buildDir/testkit"))
  // extra dependencies for test
  "functionalTestRuntimeOnly"("com.h2database:h2:1.4.200")
  "functionalTestRuntimeOnly"(fileTree("dir" to "lib", "include" to listOf("*.jar")))
  // lombok
  "functionalTestCompileOnly"("org.projectlombok:lombok:1.18.12")
  // jacoco
  "jacocoRuntime"("org.jacoco:org.jacoco.agent:${jacoco.toolVersion}:runtime")
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class).all {
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

val createTestkitFilesTask = tasks.create("createTestkitFiles") {
  val outputDir = file("$buildDir/testkit")

  inputs.files(jacocoRuntime)
  outputs.dir(outputDir)

  doLast {
    outputDir.mkdirs()
    val jacocoPath = jacocoRuntime.asPath.replace("\\", "/")
    outputDir.resolve("testkit-gradle.properties")
      .writeText("org.gradle.jvmargs=-javaagent:${jacocoPath}=destfile=$buildDir/jacoco/functionalTest.exec,append=true,inclnolocationclasses=false,dumponexit=true,output=file,jmx=false")
  }
}

val functionalTestTask = tasks.register<Test>("functionalTest") {
  testClassesDirs = functionalTest.output.classesDirs
  classpath = functionalTest.runtimeClasspath
}

val jacocoFunctionalTestReportTask = tasks.register<JacocoReport>("jacocoFunctionalTestReport") {
  sourceSets(sourceSets.main.get())
  executionData(functionalTestTask.get())
  reports {
    html.isEnabled = true
    xml.isEnabled = true
    all {
      destination = if (outputType === Report.OutputType.DIRECTORY) {
        file("${jacoco.reportsDir}/${functionalTestTask.name}/${this.name}")
      } else {
        file("${jacoco.reportsDir}/${functionalTestTask.name}/${this@register.name}.${this.name}")
      }
    }
  }
}

functionalTestTask {
  dependsOn(createTestkitFilesTask)
  finalizedBy(jacocoFunctionalTestReportTask)
}

jacocoFunctionalTestReportTask {
  dependsOn(functionalTestTask)
}

tasks.withType<Test> {
  useJUnitPlatform()
  jvmArgs = listOf("-Dorg.gradle.jvmargs=-XX:MaxMetaspaceSize=512m", "-Dkotest.assertions.multi-line-diff-size=0")
}

tasks.test {
  dependsOn(functionalTestTask)
  finalizedBy(tasks.jacocoTestReport)

  testLogging.showStandardStreams = true
}

tasks.jacocoTestReport {
  dependsOn(tasks.test)

  reports {
    html.isEnabled = true
    xml.isEnabled = true
  }
}

gradlePlugin {
  testSourceSets(functionalTest)

  plugins {
    create("generateSchema") {
      id = "io.github.divinespear.jpa-schema-generate"
      implementationClass = "io.github.divinespear.plugin.JpaSchemaGenerationPlugin"
    }
  }
}

pluginBundle {
  website = "https://divinespear.github.io/jpa-schema-gradle-plugin"
  vcsUrl = "https://github.com/divinespear/jpa-schema-gradle-plugin"
  description = "Gradle plugin for generate database schema or DDL scripts from JPA entities"

  (plugins) {
    "generateSchema" {
      displayName = "JPA 2.1 Schema Generation Plugin for Gradle"
      tags = listOf("jpa", "schema", "schemagen", "hibernate", "eclipselink", "generate")
    }
  }
}
