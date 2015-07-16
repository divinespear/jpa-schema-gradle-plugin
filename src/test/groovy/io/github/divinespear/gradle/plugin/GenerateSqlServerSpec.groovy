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

class GenerateSqlServerSpec extends IntegrationSpec {

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
        runTasks "generateSchema"
        then:
        file("build/generated-schema/mssqlscript-create.sql").exists()
        file("build/generated-schema/mssqlscript-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT DATETIME NULL, STORED_VALUE VARCHAR(32768) NULL, PRIMARY KEY (STORED_KEY));
CREATE TABLE MANY_COLUMN_TABLE (ID NUMERIC(19) IDENTITY NOT NULL, COLUMN00 VARCHAR(255) NULL, COLUMN01 VARCHAR(255) NULL, COLUMN02 VARCHAR(255) NULL, COLUMN03 VARCHAR(255) NULL, COLUMN04 VARCHAR(255) NULL, COLUMN05 VARCHAR(255) NULL, COLUMN06 VARCHAR(255) NULL, COLUMN07 VARCHAR(255) NULL, COLUMN08 VARCHAR(255) NULL, COLUMN09 VARCHAR(255) NULL, COLUMN10 VARCHAR(255) NULL, COLUMN11 VARCHAR(255) NULL, COLUMN12 VARCHAR(255) NULL, COLUMN13 VARCHAR(255) NULL, COLUMN14 VARCHAR(255) NULL, COLUMN15 VARCHAR(255) NULL, COLUMN16 VARCHAR(255) NULL, COLUMN17 VARCHAR(255) NULL, COLUMN18 VARCHAR(255) NULL, COLUMN19 VARCHAR(255) NULL, COLUMN20 VARCHAR(255) NULL, COLUMN21 VARCHAR(255) NULL, COLUMN22 VARCHAR(255) NULL, COLUMN23 VARCHAR(255) NULL, COLUMN24 VARCHAR(255) NULL, COLUMN25 VARCHAR(255) NULL, COLUMN26 VARCHAR(255) NULL, COLUMN27 VARCHAR(255) NULL, COLUMN28 VARCHAR(255) NULL, COLUMN29 VARCHAR(255) NULL, PRIMARY KEY (ID));
"""
        file("build/generated-schema/mssqlscript-drop.sql").exists()
        file("build/generated-schema/mssqlscript-drop.sql").text == """DROP TABLE KEY_VALUE_STORE;
DROP TABLE MANY_COLUMN_TABLE;
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
        runTasks "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (
\tSTORED_KEY VARCHAR(128) NOT NULL,
\tCREATED_AT DATETIME NULL,
\tSTORED_VALUE VARCHAR(32768) NULL,
\tPRIMARY KEY (STORED_KEY)
);

CREATE TABLE MANY_COLUMN_TABLE (
\tID NUMERIC(19) IDENTITY NOT NULL,
\tCOLUMN00 VARCHAR(255) NULL,
\tCOLUMN01 VARCHAR(255) NULL,
\tCOLUMN02 VARCHAR(255) NULL,
\tCOLUMN03 VARCHAR(255) NULL,
\tCOLUMN04 VARCHAR(255) NULL,
\tCOLUMN05 VARCHAR(255) NULL,
\tCOLUMN06 VARCHAR(255) NULL,
\tCOLUMN07 VARCHAR(255) NULL,
\tCOLUMN08 VARCHAR(255) NULL,
\tCOLUMN09 VARCHAR(255) NULL,
\tCOLUMN10 VARCHAR(255) NULL,
\tCOLUMN11 VARCHAR(255) NULL,
\tCOLUMN12 VARCHAR(255) NULL,
\tCOLUMN13 VARCHAR(255) NULL,
\tCOLUMN14 VARCHAR(255) NULL,
\tCOLUMN15 VARCHAR(255) NULL,
\tCOLUMN16 VARCHAR(255) NULL,
\tCOLUMN17 VARCHAR(255) NULL,
\tCOLUMN18 VARCHAR(255) NULL,
\tCOLUMN19 VARCHAR(255) NULL,
\tCOLUMN20 VARCHAR(255) NULL,
\tCOLUMN21 VARCHAR(255) NULL,
\tCOLUMN22 VARCHAR(255) NULL,
\tCOLUMN23 VARCHAR(255) NULL,
\tCOLUMN24 VARCHAR(255) NULL,
\tCOLUMN25 VARCHAR(255) NULL,
\tCOLUMN26 VARCHAR(255) NULL,
\tCOLUMN27 VARCHAR(255) NULL,
\tCOLUMN28 VARCHAR(255) NULL,
\tCOLUMN29 VARCHAR(255) NULL,
\tPRIMARY KEY (ID)
);

"""
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE;

DROP TABLE MANY_COLUMN_TABLE;

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
        runTasks "generateSchema"
        then:
        // script2008
        file("build/generated-schema/2008-create.sql").exists()
        file("build/generated-schema/2008-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime2, stored_value varchar(MAX), primary key (stored_key));
create table many_column_table (id bigint identity not null, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));
"""
        file("build/generated-schema/2008-drop.sql").exists()
        file("build/generated-schema/2008-drop.sql").text == """drop table key_value_store;
drop table many_column_table;
"""
        // script2005
        file("build/generated-schema/2005-create.sql").exists()
        file("build/generated-schema/2005-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value varchar(MAX), primary key (stored_key));
create table many_column_table (id bigint identity not null, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));
"""
        file("build/generated-schema/2005-drop.sql").exists()
        file("build/generated-schema/2005-drop.sql").text == """drop table key_value_store;
drop table many_column_table;
"""
        // script2003
        file("build/generated-schema/2003-create.sql").exists()
        file("build/generated-schema/2003-create.sql").text == """create table key_value_store (stored_key varchar(128) not null, created_at datetime, stored_value varchar(32768), primary key (stored_key));
create table many_column_table (id numeric(19,0) identity not null, column00 varchar(255), column01 varchar(255), column02 varchar(255), column03 varchar(255), column04 varchar(255), column05 varchar(255), column06 varchar(255), column07 varchar(255), column08 varchar(255), column09 varchar(255), column10 varchar(255), column11 varchar(255), column12 varchar(255), column13 varchar(255), column14 varchar(255), column15 varchar(255), column16 varchar(255), column17 varchar(255), column18 varchar(255), column19 varchar(255), column20 varchar(255), column21 varchar(255), column22 varchar(255), column23 varchar(255), column24 varchar(255), column25 varchar(255), column26 varchar(255), column27 varchar(255), column28 varchar(255), column29 varchar(255), primary key (id));
"""
        file("build/generated-schema/2003-drop.sql").exists()
        file("build/generated-schema/2003-drop.sql").text == """drop table key_value_store;
drop table many_column_table;
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
        runTasks "generateSchema"
        then:
        // script2008
        file("build/generated-schema/2008-create.sql").exists()
        file("build/generated-schema/2008-create.sql").text == """create table key_value_store (
\tstored_key varchar(128) not null,
\tcreated_at datetime2,
\tstored_value varchar(MAX),
\tprimary key (stored_key)
);

create table many_column_table (
\tid bigint identity not null,
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
        file("build/generated-schema/2008-drop.sql").exists()
        file("build/generated-schema/2008-drop.sql").text == """drop table key_value_store;

drop table many_column_table;

"""
        // script2005
        file("build/generated-schema/2005-create.sql").exists()
        file("build/generated-schema/2005-create.sql").text == """create table key_value_store (
\tstored_key varchar(128) not null,
\tcreated_at datetime,
\tstored_value varchar(MAX),
\tprimary key (stored_key)
);

create table many_column_table (
\tid bigint identity not null,
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
        file("build/generated-schema/2005-drop.sql").exists()
        file("build/generated-schema/2005-drop.sql").text == """drop table key_value_store;

drop table many_column_table;

"""
        // script2003
        file("build/generated-schema/2003-create.sql").exists()
        file("build/generated-schema/2003-create.sql").text == """create table key_value_store (
\tstored_key varchar(128) not null,
\tcreated_at datetime,
\tstored_value varchar(32768),
\tprimary key (stored_key)
);

create table many_column_table (
\tid numeric(19,0) identity not null,
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
        file("build/generated-schema/2003-drop.sql").exists()
        file("build/generated-schema/2003-drop.sql").text == """drop table key_value_store;

drop table many_column_table;

"""
    }
}
