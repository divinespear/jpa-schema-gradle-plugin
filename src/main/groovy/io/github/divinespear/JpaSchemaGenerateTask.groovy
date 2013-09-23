package io.github.divinespear

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

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class JpaSchemaGenerateTask extends DefaultTask {

    SchemaGenerationConfig merge(SchemaGenerationConfig a, SchemaGenerationConfig b) {
        if (b == null) {
            return a
        } else {
            def config = new SchemaGenerationConfig(b.name)
            return config
        }
    }

    List<SchemaGenerationConfig> getTargets() {
        def List<SchemaGenerationConfig> list = []

        project.generateSchema.targets.all { target ->
            list.add(merge(project.generateSchema, target))
        }
        if (list.empty) {
            list.add(project.generateSchema)
        }

        return list
    }

    @TaskAction
    def generate() {
    }
}
