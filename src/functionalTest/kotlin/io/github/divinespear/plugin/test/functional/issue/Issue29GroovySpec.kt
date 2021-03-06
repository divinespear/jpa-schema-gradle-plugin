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
package io.github.divinespear.plugin.test.functional.issue

import io.github.divinespear.plugin.test.GroovyFunctionalSpec
import io.github.divinespear.plugin.test.helper.runHibernateTask
import io.kotest.matchers.and
import io.kotest.matchers.should
import io.kotest.matchers.string.contain

class Issue29GroovySpec : GroovyFunctionalSpec() {

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
    "spring-boot 1.5.10" should {
      "have spring dependencies" {
        runHibernateTask(script("1.5.10.RELEASE")) {
          it.output should (contain("org.springframework/spring-aspects/") and contain("org.springframework/spring-context/"))
        }
      }
    }

    "spring-boot 2.0.0" should {
      "have spring dependencies" {
        runHibernateTask(script("2.0.0.RELEASE")) {
          it.output should (contain("org.springframework/spring-aspects/") and contain("org.springframework/spring-context/"))
        }
      }
    }
  }
}
