package io.github.divinespear.gradle.plugin

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

import nebula.test.IntegrationSpec

class GenerateMySQLSpec extends IntegrationSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWorkEclipseLinkFormatted() {
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
                format = true
                scriptAction = "drop-and-create"
                databaseProductName = "MySQL"
                databaseMajorVersion = 5
                databaseMinorVersion = 1
            }
        """
        when:
        runTasks "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (
\tSTORED_KEY VARCHAR(128) NOT NULL,
\tCREATED_AT DATETIME,
\tSTORED_VALUE VARCHAR(32768),
\tPRIMARY KEY (STORED_KEY)
);

CREATE TABLE MANY_COLUMN_TABLE (
\tID BIGINT AUTO_INCREMENT NOT NULL,
\tCOLUMN00 VARCHAR(255),
\tCOLUMN01 VARCHAR(255),
\tCOLUMN02 VARCHAR(255),
\tCOLUMN03 VARCHAR(255),
\tCOLUMN04 VARCHAR(255),
\tCOLUMN05 VARCHAR(255),
\tCOLUMN06 VARCHAR(255),
\tCOLUMN07 VARCHAR(255),
\tCOLUMN08 VARCHAR(255),
\tCOLUMN09 VARCHAR(255),
\tCOLUMN10 VARCHAR(255),
\tCOLUMN11 VARCHAR(255),
\tCOLUMN12 VARCHAR(255),
\tCOLUMN13 VARCHAR(255),
\tCOLUMN14 VARCHAR(255),
\tCOLUMN15 VARCHAR(255),
\tCOLUMN16 VARCHAR(255),
\tCOLUMN17 VARCHAR(255),
\tCOLUMN18 VARCHAR(255),
\tCOLUMN19 VARCHAR(255),
\tCOLUMN20 VARCHAR(255),
\tCOLUMN21 VARCHAR(255),
\tCOLUMN22 VARCHAR(255),
\tCOLUMN23 VARCHAR(255),
\tCOLUMN24 VARCHAR(255),
\tCOLUMN25 VARCHAR(255),
\tCOLUMN26 VARCHAR(255),
\tCOLUMN27 VARCHAR(255),
\tCOLUMN28 VARCHAR(255),
\tCOLUMN29 VARCHAR(255),
\tPRIMARY KEY (ID)
);

"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE;

DROP TABLE MANY_COLUMN_TABLE;

"""
    }

    def shouldWorkHibernateFormatted() {
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
                format = true
                namingStrategy = "org.hibernate.cfg.ImprovedNamingStrategy"
                targets {
                    scriptdefault {
                        scriptAction = "drop-and-create"
                        databaseProductName = "MySQL"
                        databaseMajorVersion = 4
                        databaseMinorVersion = 1
                        createOutputFileName = "default-create.sql"
                        dropOutputFileName = "default-drop.sql"
                    }
                    scriptdefault5 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "MySQL"
                        databaseMajorVersion = 5
                        databaseMinorVersion = 1
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
                        databaseMajorVersion = 4
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
        runTasks "generateSchema"
        then:
        // scriptdefault
        file("build/generated-schema/default-create.sql").exists()
        file("build/generated-schema/default-create.sql").text == """create table key_value_store (
\tstored_key varchar(128) not null,
\tcreated_at datetime,
\tstored_value longtext,
\tprimary key (stored_key)
);

create table many_column_table (
\tid bigint not null auto_increment,
\tcolumn00 varchar(255),
\tcolumn01 varchar(255),
\tcolumn02 varchar(255),
\tcolumn03 varchar(255),
\tcolumn04 varchar(255),
\tcolumn05 varchar(255),
\tcolumn06 varchar(255),
\tcolumn07 varchar(255),
\tcolumn08 varchar(255),
\tcolumn09 varchar(255),
\tcolumn10 varchar(255),
\tcolumn11 varchar(255),
\tcolumn12 varchar(255),
\tcolumn13 varchar(255),
\tcolumn14 varchar(255),
\tcolumn15 varchar(255),
\tcolumn16 varchar(255),
\tcolumn17 varchar(255),
\tcolumn18 varchar(255),
\tcolumn19 varchar(255),
\tcolumn20 varchar(255),
\tcolumn21 varchar(255),
\tcolumn22 varchar(255),
\tcolumn23 varchar(255),
\tcolumn24 varchar(255),
\tcolumn25 varchar(255),
\tcolumn26 varchar(255),
\tcolumn27 varchar(255),
\tcolumn28 varchar(255),
\tcolumn29 varchar(255),
\tprimary key (id)
);

"""
        file("build/generated-schema/default-drop.sql").exists()
        file("build/generated-schema/default-drop.sql").text == """drop table if exists key_value_store;

drop table if exists many_column_table;

"""
        // scriptdefault5
        file("build/generated-schema/default-5-create.sql").exists()
        file("build/generated-schema/default-5-create.sql").text == """create table key_value_store (
\tstored_key varchar(128) not null,
\tcreated_at datetime,
\tstored_value varchar(32768),
\tprimary key (stored_key)
);

create table many_column_table (
\tid bigint not null auto_increment,
\tcolumn00 varchar(255),
\tcolumn01 varchar(255),
\tcolumn02 varchar(255),
\tcolumn03 varchar(255),
\tcolumn04 varchar(255),
\tcolumn05 varchar(255),
\tcolumn06 varchar(255),
\tcolumn07 varchar(255),
\tcolumn08 varchar(255),
\tcolumn09 varchar(255),
\tcolumn10 varchar(255),
\tcolumn11 varchar(255),
\tcolumn12 varchar(255),
\tcolumn13 varchar(255),
\tcolumn14 varchar(255),
\tcolumn15 varchar(255),
\tcolumn16 varchar(255),
\tcolumn17 varchar(255),
\tcolumn18 varchar(255),
\tcolumn19 varchar(255),
\tcolumn20 varchar(255),
\tcolumn21 varchar(255),
\tcolumn22 varchar(255),
\tcolumn23 varchar(255),
\tcolumn24 varchar(255),
\tcolumn25 varchar(255),
\tcolumn26 varchar(255),
\tcolumn27 varchar(255),
\tcolumn28 varchar(255),
\tcolumn29 varchar(255),
\tprimary key (id)
);

"""
        file("build/generated-schema/default-5-drop.sql").exists()
        file("build/generated-schema/default-5-drop.sql").text == """drop table if exists key_value_store;

drop table if exists many_column_table;

"""
        // scriptmyisam
        file("build/generated-schema/myisam-create.sql").exists()
        file("build/generated-schema/myisam-create.sql").text == """create table key_value_store (
\tstored_key varchar(128) not null,
\tcreated_at datetime,
\tstored_value longtext,
\tprimary key (stored_key)
) type=MyISAM;

create table many_column_table (
\tid bigint not null auto_increment,
\tcolumn00 varchar(255),
\tcolumn01 varchar(255),
\tcolumn02 varchar(255),
\tcolumn03 varchar(255),
\tcolumn04 varchar(255),
\tcolumn05 varchar(255),
\tcolumn06 varchar(255),
\tcolumn07 varchar(255),
\tcolumn08 varchar(255),
\tcolumn09 varchar(255),
\tcolumn10 varchar(255),
\tcolumn11 varchar(255),
\tcolumn12 varchar(255),
\tcolumn13 varchar(255),
\tcolumn14 varchar(255),
\tcolumn15 varchar(255),
\tcolumn16 varchar(255),
\tcolumn17 varchar(255),
\tcolumn18 varchar(255),
\tcolumn19 varchar(255),
\tcolumn20 varchar(255),
\tcolumn21 varchar(255),
\tcolumn22 varchar(255),
\tcolumn23 varchar(255),
\tcolumn24 varchar(255),
\tcolumn25 varchar(255),
\tcolumn26 varchar(255),
\tcolumn27 varchar(255),
\tcolumn28 varchar(255),
\tcolumn29 varchar(255),
\tprimary key (id)
) type=MyISAM;

"""
        file("build/generated-schema/myisam-drop.sql").exists()
        file("build/generated-schema/myisam-drop.sql").text == """drop table if exists key_value_store;

drop table if exists many_column_table;

"""
        // scriptinnodb
        file("build/generated-schema/innodb-create.sql").exists()
        file("build/generated-schema/innodb-create.sql").text == """create table key_value_store (
\tstored_key varchar(128) not null,
\tcreated_at datetime,
\tstored_value longtext,
\tprimary key (stored_key)
) type=InnoDB;

create table many_column_table (
\tid bigint not null auto_increment,
\tcolumn00 varchar(255),
\tcolumn01 varchar(255),
\tcolumn02 varchar(255),
\tcolumn03 varchar(255),
\tcolumn04 varchar(255),
\tcolumn05 varchar(255),
\tcolumn06 varchar(255),
\tcolumn07 varchar(255),
\tcolumn08 varchar(255),
\tcolumn09 varchar(255),
\tcolumn10 varchar(255),
\tcolumn11 varchar(255),
\tcolumn12 varchar(255),
\tcolumn13 varchar(255),
\tcolumn14 varchar(255),
\tcolumn15 varchar(255),
\tcolumn16 varchar(255),
\tcolumn17 varchar(255),
\tcolumn18 varchar(255),
\tcolumn19 varchar(255),
\tcolumn20 varchar(255),
\tcolumn21 varchar(255),
\tcolumn22 varchar(255),
\tcolumn23 varchar(255),
\tcolumn24 varchar(255),
\tcolumn25 varchar(255),
\tcolumn26 varchar(255),
\tcolumn27 varchar(255),
\tcolumn28 varchar(255),
\tcolumn29 varchar(255),
\tprimary key (id)
) type=InnoDB;

"""
        file("build/generated-schema/innodb-drop.sql").exists()
        file("build/generated-schema/innodb-drop.sql").text == """drop table if exists key_value_store;

drop table if exists many_column_table;

"""
        // scriptinnodb5
        file("build/generated-schema/innodb-5-create.sql").exists()
        file("build/generated-schema/innodb-5-create.sql").text == """create table key_value_store (
\tstored_key varchar(128) not null,
\tcreated_at datetime,
\tstored_value varchar(32768),
\tprimary key (stored_key)
) ENGINE=InnoDB;

create table many_column_table (
\tid bigint not null auto_increment,
\tcolumn00 varchar(255),
\tcolumn01 varchar(255),
\tcolumn02 varchar(255),
\tcolumn03 varchar(255),
\tcolumn04 varchar(255),
\tcolumn05 varchar(255),
\tcolumn06 varchar(255),
\tcolumn07 varchar(255),
\tcolumn08 varchar(255),
\tcolumn09 varchar(255),
\tcolumn10 varchar(255),
\tcolumn11 varchar(255),
\tcolumn12 varchar(255),
\tcolumn13 varchar(255),
\tcolumn14 varchar(255),
\tcolumn15 varchar(255),
\tcolumn16 varchar(255),
\tcolumn17 varchar(255),
\tcolumn18 varchar(255),
\tcolumn19 varchar(255),
\tcolumn20 varchar(255),
\tcolumn21 varchar(255),
\tcolumn22 varchar(255),
\tcolumn23 varchar(255),
\tcolumn24 varchar(255),
\tcolumn25 varchar(255),
\tcolumn26 varchar(255),
\tcolumn27 varchar(255),
\tcolumn28 varchar(255),
\tcolumn29 varchar(255),
\tprimary key (id)
) ENGINE=InnoDB;

"""
        file("build/generated-schema/innodb-5-drop.sql").exists()
        file("build/generated-schema/innodb-5-drop.sql").text == """drop table if exists key_value_store;

drop table if exists many_column_table;

"""
    }
}
