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

import org.gradle.test.FunctionalSpec

class GenerateMySQLSpec extends FunctionalSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWorkMySQLEclipseLink() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/eclipselink/src")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/eclipselink/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                targets {
                    mysql5script {
                        scriptAction = "drop-and-create"
                        databaseProductName = "MySQL"
                        databaseMajorVersion = 5
                        databaseMinorVersion = 1
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT DATETIME, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT AUTO_INCREMENT NOT NULL, COLUMN00 VARCHAR(255), COLUMN01 VARCHAR(255), COLUMN02 VARCHAR(255), COLUMN03 VARCHAR(255), COLUMN04 VARCHAR(255), COLUMN05 VARCHAR(255), COLUMN06 VARCHAR(255), COLUMN07 VARCHAR(255), COLUMN08 VARCHAR(255), COLUMN09 VARCHAR(255), COLUMN10 VARCHAR(255), COLUMN11 VARCHAR(255), COLUMN12 VARCHAR(255), COLUMN13 VARCHAR(255), COLUMN14 VARCHAR(255), COLUMN15 VARCHAR(255), COLUMN16 VARCHAR(255), COLUMN17 VARCHAR(255), COLUMN18 VARCHAR(255), COLUMN19 VARCHAR(255), COLUMN20 VARCHAR(255), COLUMN21 VARCHAR(255), COLUMN22 VARCHAR(255), COLUMN23 VARCHAR(255), COLUMN24 VARCHAR(255), COLUMN25 VARCHAR(255), COLUMN26 VARCHAR(255), COLUMN27 VARCHAR(255), COLUMN28 VARCHAR(255), COLUMN29 VARCHAR(255), PRIMARY KEY (ID));") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE;") > -1
    }

    def shouldWorkMySQLHibernate() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/hibernate/src")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/hibernate/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                namingStrategy = "org.hibernate.cfg.ImprovedNamingStrategy"
                targets {
                    scriptdefault {
                        scriptAction = "drop-and-create"
                        databaseProductName = "MySQL"
                        databaseMajorVersion = 5
                        databaseMinorVersion = 1
                        createOutputFileName = "default-create.sql"
                        dropOutputFileName = "default-drop.sql"
                    }
                    scriptdefault5 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "MySQL"
                        databaseMajorVersion = 5
                        databaseMinorVersion = 1
                        dialect = "org.hibernate.dialect.MySQL5Dialect"
                        createOutputFileName = "default-5-create.sql"
                        dropOutputFileName = "default-5-drop.sql"
                    }
                    scriptmyisam {
                        scriptAction = "drop-and-create"
                        databaseProductName = "MySQL"
                        databaseMajorVersion = 5
                        databaseMinorVersion = 1
                        dialect = "org.hibernate.dialect.MySQLMyISAMDialect"
                        createOutputFileName = "myisam-create.sql"
                        dropOutputFileName = "myisam-drop.sql"
                    }
                    scriptinnodb {
                        scriptAction = "drop-and-create"
                        databaseProductName = "MySQL"
                        databaseMajorVersion = 5
                        databaseMinorVersion = 1
                        dialect = "org.hibernate.dialect.MySQLInnoDBDialect"
                        createOutputFileName = "innodb-create.sql"
                        dropOutputFileName = "innodb-drop.sql"
                    }
                    scriptinnodb5 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "MySQL"
                        databaseMajorVersion = 5
                        databaseMinorVersion = 1
                        dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
                        createOutputFileName = "innodb-5-create.sql"
                        dropOutputFileName = "innodb-5-drop.sql"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        // scriptdefault
        file("build/generated-schema/default-create.sql").exists()
        file("build/generated-schema/default-create.sql").text.indexOf("create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value longtext, primary key (stored_key));") > -1
        file("build/generated-schema/default-create.sql").text.indexOf("create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));") > -1
        file("build/generated-schema/default-drop.sql").exists()
        file("build/generated-schema/default-drop.sql").text.indexOf("drop table if exists key_value_store;") > -1
        file("build/generated-schema/default-drop.sql").text.indexOf("drop table if exists many_column_table;") > -1
        // scriptdefault5
        file("build/generated-schema/default-5-create.sql").exists()
        file("build/generated-schema/default-5-create.sql").text.indexOf("create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value varchar(32768), primary key (stored_key));") > -1
        file("build/generated-schema/default-5-create.sql").text.indexOf("create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));") > -1
        file("build/generated-schema/default-5-drop.sql").exists()
        file("build/generated-schema/default-5-drop.sql").text.indexOf("drop table if exists key_value_store;") > -1
        file("build/generated-schema/default-5-drop.sql").text.indexOf("drop table if exists many_column_table;") > -1
        // scriptmyisam
        file("build/generated-schema/myisam-create.sql").exists()
        file("build/generated-schema/myisam-create.sql").text.indexOf("create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value longtext, primary key (stored_key)) type=MyISAM;") > -1
        file("build/generated-schema/myisam-create.sql").text.indexOf("create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id)) type=MyISAM;") > -1
        file("build/generated-schema/myisam-drop.sql").exists()
        file("build/generated-schema/myisam-drop.sql").text.indexOf("drop table if exists key_value_store;") > -1
        file("build/generated-schema/myisam-drop.sql").text.indexOf("drop table if exists many_column_table;") > -1
        // scriptinnodb
        file("build/generated-schema/innodb-create.sql").exists()
        file("build/generated-schema/innodb-create.sql").text.indexOf("create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value longtext, primary key (stored_key)) type=InnoDB;") > -1
        file("build/generated-schema/innodb-create.sql").text.indexOf("create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id)) type=InnoDB;") > -1
        file("build/generated-schema/innodb-drop.sql").exists()
        file("build/generated-schema/innodb-drop.sql").text.indexOf("drop table if exists key_value_store;") > -1
        file("build/generated-schema/innodb-drop.sql").text.indexOf("drop table if exists many_column_table;") > -1
        // scriptinnodb5
        file("build/generated-schema/innodb-5-create.sql").exists()
        file("build/generated-schema/innodb-5-create.sql").text.indexOf("create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value varchar(32768), primary key (stored_key)) ENGINE=InnoDB;") > -1
        file("build/generated-schema/innodb-5-create.sql").text.indexOf("create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id)) ENGINE=InnoDB;") > -1
        file("build/generated-schema/innodb-5-drop.sql").exists()
        file("build/generated-schema/innodb-5-drop.sql").text.indexOf("drop table if exists key_value_store;") > -1
        file("build/generated-schema/innodb-5-drop.sql").text.indexOf("drop table if exists many_column_table;") > -1
    }
}
