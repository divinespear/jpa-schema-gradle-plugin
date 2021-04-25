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
package io.github.divinespear.plugin.test.functional.hibernate

import io.github.divinespear.plugin.test.GroovyFunctionalSpec
import io.github.divinespear.plugin.test.helper.HIBERNATE_4_GROOVY_PROPERTIES
import io.github.divinespear.plugin.test.helper.HIBERNATE_5_GROOVY_PROPERTIES
import io.github.divinespear.plugin.test.helper.runHibernateTask
import io.kotest.matchers.should
import io.kotest.matchers.string.contain

class HibernateGroovySpec : GroovyFunctionalSpec() {

  private fun script(version: String, dependencySuffix: String, properties: String) =
    """
      
      plugins {
        id 'io.github.divinespear.jpa-schema-generate'
      }
      
      repositories {
        mavenCentral()
      }
      
      dependencies {
        compile 'org.hibernate:hibernate-$dependencySuffix:$version'
        compile 'org.springframework.boot:spring-boot:1.5.10.RELEASE'
        runtime 'com.h2database:h2:1.4.191'
        runtime fileTree(dir: "../../../lib", include: "*.jar")
      }
      
      generateSchema {
        vendor = 'hibernate'
        packageToScan = [ 'io.github.divinespear.model' ]
        scriptAction = "drop-and-create"
        properties = $properties
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
            jdbcUrl = "jdbc:h2:${'$'}{buildDir}/generated-schema/test"
            jdbcUser = "sa"
          }
        }
      }
    """.trimIndent()

  init {
    "task without persistence.xml" should {
      "work on hibernate 5.2" {
        runHibernateTask(
          script(
            "[5.2,5.3)",
            "core",
            HIBERNATE_5_GROOVY_PROPERTIES
          )
        ) {
          it.output should contain("org.hibernate/hibernate-core/5.2.")
        }
      }
      "work on hibernate 5.1" {
        runHibernateTask(
          script(
            "[5.1,5.2)",
            "entitymanager",
            HIBERNATE_5_GROOVY_PROPERTIES
          )
        ) {
          it.output should contain("org.hibernate/hibernate-core/5.1.")
        }
      }
      "work on hibernate 5.0" {
        runHibernateTask(
          script(
            "[5.0,5.1)",
            "entitymanager",
            HIBERNATE_5_GROOVY_PROPERTIES
          )
        ) {
          it.output should contain("org.hibernate/hibernate-core/5.0.")
        }
      }
      "work on hibernate 4.3" {
        runHibernateTask(
          script(
            "[4.3,4.4)",
            "entitymanager",
            HIBERNATE_4_GROOVY_PROPERTIES
          )
        ) {
          it.output should contain("org.hibernate/hibernate-core/4.3.")
        }
      }
    }
  }
}
