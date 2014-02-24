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

    def shouldWorkEclipseLink() {
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
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT DATETIME, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT AUTO_INCREMENT NOT NULL, COLUMN00 VARCHAR(255), COLUMN01 VARCHAR(255), COLUMN02 VARCHAR(255), COLUMN03 VARCHAR(255), COLUMN04 VARCHAR(255), COLUMN05 VARCHAR(255), COLUMN06 VARCHAR(255), COLUMN07 VARCHAR(255), COLUMN08 VARCHAR(255), COLUMN09 VARCHAR(255), COLUMN10 VARCHAR(255), COLUMN11 VARCHAR(255), COLUMN12 VARCHAR(255), COLUMN13 VARCHAR(255), COLUMN14 VARCHAR(255), COLUMN15 VARCHAR(255), COLUMN16 VARCHAR(255), COLUMN17 VARCHAR(255), COLUMN18 VARCHAR(255), COLUMN19 VARCHAR(255), COLUMN20 VARCHAR(255), COLUMN21 VARCHAR(255), COLUMN22 VARCHAR(255), COLUMN23 VARCHAR(255), COLUMN24 VARCHAR(255), COLUMN25 VARCHAR(255), COLUMN26 VARCHAR(255), COLUMN27 VARCHAR(255), COLUMN28 VARCHAR(255), COLUMN29 VARCHAR(255), PRIMARY KEY (ID));\r
"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE;\r
DROP TABLE MANY_COLUMN_TABLE;\r
"""
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
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (\r
\tSTORED_KEY VARCHAR(128) NOT NULL,\r
\tCREATED_AT DATETIME,\r
\tSTORED_VALUE VARCHAR(32768),\r
\tPRIMARY KEY (STORED_KEY)\r
);\r
\r
CREATE TABLE MANY_COLUMN_TABLE (\r
\tID BIGINT AUTO_INCREMENT NOT NULL,\r
\tCOLUMN00 VARCHAR(255),\r
\tCOLUMN01 VARCHAR(255),\r
\tCOLUMN02 VARCHAR(255),\r
\tCOLUMN03 VARCHAR(255),\r
\tCOLUMN04 VARCHAR(255),\r
\tCOLUMN05 VARCHAR(255),\r
\tCOLUMN06 VARCHAR(255),\r
\tCOLUMN07 VARCHAR(255),\r
\tCOLUMN08 VARCHAR(255),\r
\tCOLUMN09 VARCHAR(255),\r
\tCOLUMN10 VARCHAR(255),\r
\tCOLUMN11 VARCHAR(255),\r
\tCOLUMN12 VARCHAR(255),\r
\tCOLUMN13 VARCHAR(255),\r
\tCOLUMN14 VARCHAR(255),\r
\tCOLUMN15 VARCHAR(255),\r
\tCOLUMN16 VARCHAR(255),\r
\tCOLUMN17 VARCHAR(255),\r
\tCOLUMN18 VARCHAR(255),\r
\tCOLUMN19 VARCHAR(255),\r
\tCOLUMN20 VARCHAR(255),\r
\tCOLUMN21 VARCHAR(255),\r
\tCOLUMN22 VARCHAR(255),\r
\tCOLUMN23 VARCHAR(255),\r
\tCOLUMN24 VARCHAR(255),\r
\tCOLUMN25 VARCHAR(255),\r
\tCOLUMN26 VARCHAR(255),\r
\tCOLUMN27 VARCHAR(255),\r
\tCOLUMN28 VARCHAR(255),\r
\tCOLUMN29 VARCHAR(255),\r
\tPRIMARY KEY (ID)\r
);\r
\r
"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE;\r
\r
DROP TABLE MANY_COLUMN_TABLE;\r
\r
"""
    }

    def shouldWorkHibernate() {
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
        file("build/generated-schema/default-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value longtext, primary key (stored_key));\r
create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));\r
"""
        file("build/generated-schema/default-drop.sql").exists()
        file("build/generated-schema/default-drop.sql").text == """drop table if exists key_value_store;\r
drop table if exists many_column_table;\r
"""
        // scriptdefault5
        file("build/generated-schema/default-5-create.sql").exists()
        file("build/generated-schema/default-5-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value varchar(32768), primary key (stored_key));\r
create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));\r
"""
        file("build/generated-schema/default-5-drop.sql").exists()
        file("build/generated-schema/default-5-drop.sql").text == """drop table if exists key_value_store;\r
drop table if exists many_column_table;\r
"""
        // scriptmyisam
        file("build/generated-schema/myisam-create.sql").exists()
        file("build/generated-schema/myisam-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value longtext, primary key (stored_key)) type=MyISAM;\r
create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id)) type=MyISAM;\r
"""
        file("build/generated-schema/myisam-drop.sql").exists()
        file("build/generated-schema/myisam-drop.sql").text == """drop table if exists key_value_store;\r
drop table if exists many_column_table;\r
"""
        // scriptinnodb
        file("build/generated-schema/innodb-create.sql").exists()
        file("build/generated-schema/innodb-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value longtext, primary key (stored_key)) type=InnoDB;\r
create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id)) type=InnoDB;\r
"""
        file("build/generated-schema/innodb-drop.sql").exists()
        file("build/generated-schema/innodb-drop.sql").text == """drop table if exists key_value_store;\r
drop table if exists many_column_table;\r
"""
        // scriptinnodb5
        file("build/generated-schema/innodb-5-create.sql").exists()
        file("build/generated-schema/innodb-5-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value varchar(32768), primary key (stored_key)) ENGINE=InnoDB;\r
create table many_column_table (id bigint not null auto_increment, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id)) ENGINE=InnoDB;\r
"""
        file("build/generated-schema/innodb-5-drop.sql").exists()
        file("build/generated-schema/innodb-5-drop.sql").text == """drop table if exists key_value_store;\r
drop table if exists many_column_table;\r
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
        file("build/generated-schema/default-create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at datetime,\r
\tstored_value longtext,\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid bigint not null auto_increment,\r
\tcolumn00 varchar(255),\r
\tcolumn01 varchar(255),\r
\tcolumn02 varchar(255),\r
\tcolumn03 varchar(255),\r
\tcolumn04 varchar(255),\r
\tcolumn05 varchar(255),\r
\tcolumn06 varchar(255),\r
\tcolumn07 varchar(255),\r
\tcolumn08 varchar(255),\r
\tcolumn09 varchar(255),\r
\tcolumn10 varchar(255),\r
\tcolumn11 varchar(255),\r
\tcolumn12 varchar(255),\r
\tcolumn13 varchar(255),\r
\tcolumn14 varchar(255),\r
\tcolumn15 varchar(255),\r
\tcolumn16 varchar(255),\r
\tcolumn17 varchar(255),\r
\tcolumn18 varchar(255),\r
\tcolumn19 varchar(255),\r
\tcolumn20 varchar(255),\r
\tcolumn21 varchar(255),\r
\tcolumn22 varchar(255),\r
\tcolumn23 varchar(255),\r
\tcolumn24 varchar(255),\r
\tcolumn25 varchar(255),\r
\tcolumn26 varchar(255),\r
\tcolumn27 varchar(255),\r
\tcolumn28 varchar(255),\r
\tcolumn29 varchar(255),\r
\tprimary key (id)\r
);\r
\r
"""
        file("build/generated-schema/default-drop.sql").exists()
        file("build/generated-schema/default-drop.sql").text == """drop table if exists key_value_store;\r
\r
drop table if exists many_column_table;\r
\r
"""
        // scriptdefault5
        file("build/generated-schema/default-5-create.sql").exists()
        file("build/generated-schema/default-5-create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at datetime,\r
\tstored_value varchar(32768),\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid bigint not null auto_increment,\r
\tcolumn00 varchar(255),\r
\tcolumn01 varchar(255),\r
\tcolumn02 varchar(255),\r
\tcolumn03 varchar(255),\r
\tcolumn04 varchar(255),\r
\tcolumn05 varchar(255),\r
\tcolumn06 varchar(255),\r
\tcolumn07 varchar(255),\r
\tcolumn08 varchar(255),\r
\tcolumn09 varchar(255),\r
\tcolumn10 varchar(255),\r
\tcolumn11 varchar(255),\r
\tcolumn12 varchar(255),\r
\tcolumn13 varchar(255),\r
\tcolumn14 varchar(255),\r
\tcolumn15 varchar(255),\r
\tcolumn16 varchar(255),\r
\tcolumn17 varchar(255),\r
\tcolumn18 varchar(255),\r
\tcolumn19 varchar(255),\r
\tcolumn20 varchar(255),\r
\tcolumn21 varchar(255),\r
\tcolumn22 varchar(255),\r
\tcolumn23 varchar(255),\r
\tcolumn24 varchar(255),\r
\tcolumn25 varchar(255),\r
\tcolumn26 varchar(255),\r
\tcolumn27 varchar(255),\r
\tcolumn28 varchar(255),\r
\tcolumn29 varchar(255),\r
\tprimary key (id)\r
);\r
\r
"""
        file("build/generated-schema/default-5-drop.sql").exists()
        file("build/generated-schema/default-5-drop.sql").text == """drop table if exists key_value_store;\r
\r
drop table if exists many_column_table;\r
\r
"""
        // scriptmyisam
        file("build/generated-schema/myisam-create.sql").exists()
        file("build/generated-schema/myisam-create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at datetime,\r
\tstored_value longtext,\r
\tprimary key (stored_key)\r
) type=MyISAM;\r
\r
create table many_column_table (\r
\tid bigint not null auto_increment,\r
\tcolumn00 varchar(255),\r
\tcolumn01 varchar(255),\r
\tcolumn02 varchar(255),\r
\tcolumn03 varchar(255),\r
\tcolumn04 varchar(255),\r
\tcolumn05 varchar(255),\r
\tcolumn06 varchar(255),\r
\tcolumn07 varchar(255),\r
\tcolumn08 varchar(255),\r
\tcolumn09 varchar(255),\r
\tcolumn10 varchar(255),\r
\tcolumn11 varchar(255),\r
\tcolumn12 varchar(255),\r
\tcolumn13 varchar(255),\r
\tcolumn14 varchar(255),\r
\tcolumn15 varchar(255),\r
\tcolumn16 varchar(255),\r
\tcolumn17 varchar(255),\r
\tcolumn18 varchar(255),\r
\tcolumn19 varchar(255),\r
\tcolumn20 varchar(255),\r
\tcolumn21 varchar(255),\r
\tcolumn22 varchar(255),\r
\tcolumn23 varchar(255),\r
\tcolumn24 varchar(255),\r
\tcolumn25 varchar(255),\r
\tcolumn26 varchar(255),\r
\tcolumn27 varchar(255),\r
\tcolumn28 varchar(255),\r
\tcolumn29 varchar(255),\r
\tprimary key (id)\r
) type=MyISAM;\r
\r
"""
        file("build/generated-schema/myisam-drop.sql").exists()
        file("build/generated-schema/myisam-drop.sql").text == """drop table if exists key_value_store;\r
\r
drop table if exists many_column_table;\r
\r
"""
        // scriptinnodb
        file("build/generated-schema/innodb-create.sql").exists()
        file("build/generated-schema/innodb-create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at datetime,\r
\tstored_value longtext,\r
\tprimary key (stored_key)\r
) type=InnoDB;\r
\r
create table many_column_table (\r
\tid bigint not null auto_increment,\r
\tcolumn00 varchar(255),\r
\tcolumn01 varchar(255),\r
\tcolumn02 varchar(255),\r
\tcolumn03 varchar(255),\r
\tcolumn04 varchar(255),\r
\tcolumn05 varchar(255),\r
\tcolumn06 varchar(255),\r
\tcolumn07 varchar(255),\r
\tcolumn08 varchar(255),\r
\tcolumn09 varchar(255),\r
\tcolumn10 varchar(255),\r
\tcolumn11 varchar(255),\r
\tcolumn12 varchar(255),\r
\tcolumn13 varchar(255),\r
\tcolumn14 varchar(255),\r
\tcolumn15 varchar(255),\r
\tcolumn16 varchar(255),\r
\tcolumn17 varchar(255),\r
\tcolumn18 varchar(255),\r
\tcolumn19 varchar(255),\r
\tcolumn20 varchar(255),\r
\tcolumn21 varchar(255),\r
\tcolumn22 varchar(255),\r
\tcolumn23 varchar(255),\r
\tcolumn24 varchar(255),\r
\tcolumn25 varchar(255),\r
\tcolumn26 varchar(255),\r
\tcolumn27 varchar(255),\r
\tcolumn28 varchar(255),\r
\tcolumn29 varchar(255),\r
\tprimary key (id)\r
) type=InnoDB;\r
\r
"""
        file("build/generated-schema/innodb-drop.sql").exists()
        file("build/generated-schema/innodb-drop.sql").text == """drop table if exists key_value_store;\r
\r
drop table if exists many_column_table;\r
\r
"""
        // scriptinnodb5
        file("build/generated-schema/innodb-5-create.sql").exists()
        file("build/generated-schema/innodb-5-create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at datetime,\r
\tstored_value varchar(32768),\r
\tprimary key (stored_key)\r
) ENGINE=InnoDB;\r
\r
create table many_column_table (\r
\tid bigint not null auto_increment,\r
\tcolumn00 varchar(255),\r
\tcolumn01 varchar(255),\r
\tcolumn02 varchar(255),\r
\tcolumn03 varchar(255),\r
\tcolumn04 varchar(255),\r
\tcolumn05 varchar(255),\r
\tcolumn06 varchar(255),\r
\tcolumn07 varchar(255),\r
\tcolumn08 varchar(255),\r
\tcolumn09 varchar(255),\r
\tcolumn10 varchar(255),\r
\tcolumn11 varchar(255),\r
\tcolumn12 varchar(255),\r
\tcolumn13 varchar(255),\r
\tcolumn14 varchar(255),\r
\tcolumn15 varchar(255),\r
\tcolumn16 varchar(255),\r
\tcolumn17 varchar(255),\r
\tcolumn18 varchar(255),\r
\tcolumn19 varchar(255),\r
\tcolumn20 varchar(255),\r
\tcolumn21 varchar(255),\r
\tcolumn22 varchar(255),\r
\tcolumn23 varchar(255),\r
\tcolumn24 varchar(255),\r
\tcolumn25 varchar(255),\r
\tcolumn26 varchar(255),\r
\tcolumn27 varchar(255),\r
\tcolumn28 varchar(255),\r
\tcolumn29 varchar(255),\r
\tprimary key (id)\r
) ENGINE=InnoDB;\r
\r
"""
        file("build/generated-schema/innodb-5-drop.sql").exists()
        file("build/generated-schema/innodb-5-drop.sql").text == """drop table if exists key_value_store;\r
\r
drop table if exists many_column_table;\r
\r
"""
    }
}
