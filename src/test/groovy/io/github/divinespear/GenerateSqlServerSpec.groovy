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

class GenerateSqlServerSpec extends FunctionalSpec {

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
                    mssqlscript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Microsoft SQL Server"
                        databaseMajorVersion = 10
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT DATETIME NULL, STORED_VALUE VARCHAR(32768) NULL, PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID NUMERIC(19) IDENTITY NOT NULL, COLUMN00 VARCHAR(255) NULL, COLUMN01 VARCHAR(255) NULL, COLUMN02 VARCHAR(255) NULL, COLUMN03 VARCHAR(255) NULL, COLUMN04 VARCHAR(255) NULL, COLUMN05 VARCHAR(255) NULL, COLUMN06 VARCHAR(255) NULL, COLUMN07 VARCHAR(255) NULL, COLUMN08 VARCHAR(255) NULL, COLUMN09 VARCHAR(255) NULL, COLUMN10 VARCHAR(255) NULL, COLUMN11 VARCHAR(255) NULL, COLUMN12 VARCHAR(255) NULL, COLUMN13 VARCHAR(255) NULL, COLUMN14 VARCHAR(255) NULL, COLUMN15 VARCHAR(255) NULL, COLUMN16 VARCHAR(255) NULL, COLUMN17 VARCHAR(255) NULL, COLUMN18 VARCHAR(255) NULL, COLUMN19 VARCHAR(255) NULL, COLUMN20 VARCHAR(255) NULL, COLUMN21 VARCHAR(255) NULL, COLUMN22 VARCHAR(255) NULL, COLUMN23 VARCHAR(255) NULL, COLUMN24 VARCHAR(255) NULL, COLUMN25 VARCHAR(255) NULL, COLUMN26 VARCHAR(255) NULL, COLUMN27 VARCHAR(255) NULL, COLUMN28 VARCHAR(255) NULL, COLUMN29 VARCHAR(255) NULL, PRIMARY KEY (ID));\r
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
                databaseProductName = "Microsoft SQL Server"
                databaseMajorVersion = 10
                databaseMinorVersion = 0
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (\r
\tSTORED_KEY VARCHAR(128) NOT NULL,\r
\tCREATED_AT DATETIME NULL,\r
\tSTORED_VALUE VARCHAR(32768) NULL,\r
\tPRIMARY KEY (STORED_KEY)\r
);\r
\r
CREATE TABLE MANY_COLUMN_TABLE (\r
\tID NUMERIC(19) IDENTITY NOT NULL,\r
\tCOLUMN00 VARCHAR(255) NULL,\r
\tCOLUMN01 VARCHAR(255) NULL,\r
\tCOLUMN02 VARCHAR(255) NULL,\r
\tCOLUMN03 VARCHAR(255) NULL,\r
\tCOLUMN04 VARCHAR(255) NULL,\r
\tCOLUMN05 VARCHAR(255) NULL,\r
\tCOLUMN06 VARCHAR(255) NULL,\r
\tCOLUMN07 VARCHAR(255) NULL,\r
\tCOLUMN08 VARCHAR(255) NULL,\r
\tCOLUMN09 VARCHAR(255) NULL,\r
\tCOLUMN10 VARCHAR(255) NULL,\r
\tCOLUMN11 VARCHAR(255) NULL,\r
\tCOLUMN12 VARCHAR(255) NULL,\r
\tCOLUMN13 VARCHAR(255) NULL,\r
\tCOLUMN14 VARCHAR(255) NULL,\r
\tCOLUMN15 VARCHAR(255) NULL,\r
\tCOLUMN16 VARCHAR(255) NULL,\r
\tCOLUMN17 VARCHAR(255) NULL,\r
\tCOLUMN18 VARCHAR(255) NULL,\r
\tCOLUMN19 VARCHAR(255) NULL,\r
\tCOLUMN20 VARCHAR(255) NULL,\r
\tCOLUMN21 VARCHAR(255) NULL,\r
\tCOLUMN22 VARCHAR(255) NULL,\r
\tCOLUMN23 VARCHAR(255) NULL,\r
\tCOLUMN24 VARCHAR(255) NULL,\r
\tCOLUMN25 VARCHAR(255) NULL,\r
\tCOLUMN26 VARCHAR(255) NULL,\r
\tCOLUMN27 VARCHAR(255) NULL,\r
\tCOLUMN28 VARCHAR(255) NULL,\r
\tCOLUMN29 VARCHAR(255) NULL,\r
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
                    script2008 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Microsoft SQL Server"
                        databaseMajorVersion = 10
                        createOutputFileName = "2008-create.sql"
                        dropOutputFileName = "2008-drop.sql"
                    }
                    script2005 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Microsoft SQL Server"
                        databaseMajorVersion = 9
                        createOutputFileName = "2005-create.sql"
                        dropOutputFileName = "2005-drop.sql"
                    }
                    script2003 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Microsoft SQL Server"
                        databaseMajorVersion = 8
                        createOutputFileName = "2003-create.sql"
                        dropOutputFileName = "2003-drop.sql"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        // script2008
        file("build/generated-schema/2008-create.sql").exists()
        file("build/generated-schema/2008-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime2, stored_value varchar(MAX), primary key (stored_key));\r
create table many_column_table (id bigint identity not null, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));\r
"""
        file("build/generated-schema/2008-drop.sql").exists()
        file("build/generated-schema/2008-drop.sql").text == """drop table key_value_store;\r
drop table many_column_table;\r
"""
        // script2005
        file("build/generated-schema/2005-create.sql").exists()
        file("build/generated-schema/2005-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value varchar(MAX), primary key (stored_key));\r
create table many_column_table (id bigint identity not null, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));\r
"""
        file("build/generated-schema/2005-drop.sql").exists()
        file("build/generated-schema/2005-drop.sql").text == """drop table key_value_store;\r
drop table many_column_table;\r
"""
        // script2003
        file("build/generated-schema/2003-create.sql").exists()
        file("build/generated-schema/2003-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value varchar(32768), primary key (stored_key));\r
create table many_column_table (id numeric(19,0) identity not null, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));\r
"""
        file("build/generated-schema/2003-drop.sql").exists()
        file("build/generated-schema/2003-drop.sql").text == """drop table key_value_store;\r
drop table many_column_table;\r
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
                    script2008 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Microsoft SQL Server"
                        databaseMajorVersion = 10
                        createOutputFileName = "2008-create.sql"
                        dropOutputFileName = "2008-drop.sql"
                    }
                    script2005 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Microsoft SQL Server"
                        databaseMajorVersion = 9
                        createOutputFileName = "2005-create.sql"
                        dropOutputFileName = "2005-drop.sql"
                    }
                    script2003 {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Microsoft SQL Server"
                        databaseMajorVersion = 8
                        createOutputFileName = "2003-create.sql"
                        dropOutputFileName = "2003-drop.sql"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        // script2008
        file("build/generated-schema/2008-create.sql").exists()
        file("build/generated-schema/2008-create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at datetime2,\r
\tstored_value varchar(MAX),\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid bigint identity not null,\r
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
        file("build/generated-schema/2008-drop.sql").exists()
        file("build/generated-schema/2008-drop.sql").text == """drop table key_value_store;\r
\r
drop table many_column_table;\r
\r
"""
        // script2005
        file("build/generated-schema/2005-create.sql").exists()
        file("build/generated-schema/2005-create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at datetime,\r
\tstored_value varchar(MAX),\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid bigint identity not null,\r
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
        file("build/generated-schema/2005-drop.sql").exists()
        file("build/generated-schema/2005-drop.sql").text == """drop table key_value_store;\r
\r
drop table many_column_table;\r
\r
"""
        // script2003
        file("build/generated-schema/2003-create.sql").exists()
        file("build/generated-schema/2003-create.sql").text == """create table key_value_store (\r
\tstored_key varchar(128) not null,\r
\tcreated_at datetime,\r
\tstored_value varchar(32768),\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid numeric(19,0) identity not null,\r
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
        file("build/generated-schema/2003-drop.sql").exists()
        file("build/generated-schema/2003-drop.sql").text == """drop table key_value_store;\r
\r
drop table many_column_table;\r
\r
"""
    }
}
