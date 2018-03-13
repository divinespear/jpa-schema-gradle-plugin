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
package io.github.divinespear.plugin

import io.github.divinespear.test.IntegrationSpec
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome

class HibernateSpec extends IntegrationSpec {

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

  def 'should work on hibernate 5.2, with xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/hibernate")
    }
    output.resourcesDir output.classesDir
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[5.2,5.3)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
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
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info").withPluginClasspath().withDebug(true).build()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.2.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
  }

  def 'should work on hibernate 5.2, without xml'() {
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
    output.resourcesDir output.classesDir
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[5.2,5.3)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
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
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info").withPluginClasspath().withDebug(true).build()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.2.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
  }

  def 'should work on hibernate 5.1, with xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/hibernate")
    }
    output.resourcesDir output.classesDir
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[5.1,5.2)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
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
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info").withPluginClasspath().withDebug(true).build()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.1.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
  }

  def 'should work on hibernate 5.1, without xml'() {
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
    output.resourcesDir output.classesDir
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[5.1,5.2)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
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
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info").withPluginClasspath().withDebug(true).build()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.1.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
  }

  def 'should work on hibernate 5.0, with xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/hibernate")
    }
    output.resourcesDir output.classesDir
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[5.0,5.1)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
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
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info").withPluginClasspath().withDebug(true).build()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.0.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
  }

  def 'should work on hibernate 5.0, without xml'() {
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
    output.resourcesDir output.classesDir
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[5.0,5.1)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
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
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info").withPluginClasspath().withDebug(true).build()

    then:
    result.output.contains("org.hibernate/hibernate-core/5.0.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
  }

  def 'should work on hibernate 4.3, with xml'() {
    given:
    buildFile << """
sourceSets {
  main {
    java {
      srcDir file("../../../src/test/resources/unit/src")
    }
    resources {
      srcDir file("../../../src/test/resources/unit/resources/hibernate")
    }
    output.resourcesDir output.classesDir
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[4.3,4.4)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
  runtime 'com.h2database:h2:1.4.191'
  runtime fileTree(dir: "../../../lib", include: "*.jar")
}

generateSchema {
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
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info").withPluginClasspath().withDebug(true).build()

    then:
    result.output.contains("org.hibernate/hibernate-core/4.3.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
  }

  def 'should work on hibernate 4.3, without xml'() {
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
    output.resourcesDir output.classesDir
  }
}

dependencies {
  compile 'org.hibernate:hibernate-core:[4.3,4.4)'
  compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
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
    h2database {
      databaseAction = "drop-and-create"
      scriptAction = null
      jdbcDriver = "org.h2.Driver"
      jdbcUrl = "jdbc:h2:\${buildDir}/generated-schema/test;AUTO_SERVER=TRUE"
      jdbcUser = "sa"
    }
  }
}
"""
    when:
    def result = GradleRunner.create().withProjectDir(testProjectDir.root).withArguments("generateSchema", "--info").withPluginClasspath().withDebug(true).build()

    then:
    result.output.contains("org.hibernate/hibernate-core/4.3.")
    result.task(":generateSchema").outcome == TaskOutcome.SUCCESS
  }
}
