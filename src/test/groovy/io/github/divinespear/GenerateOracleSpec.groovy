package io.github.divinespear

import org.gradle.test.FunctionalSpec

class GenerateOracleSpec extends FunctionalSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }

    def shouldWork12gEclipseLink() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/eclipselink-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 12
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP SEQUENCE SEQ_GEN_SEQUENCE;") > -1
    }

    def shouldWork11gEclipseLink() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/eclipselink-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 11
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP SEQUENCE SEQ_GEN_SEQUENCE;") > -1
    }

    def shouldWork10gEclipseLink() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/eclipselink-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
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
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP SEQUENCE SEQ_GEN_SEQUENCE;") > -1
    }

    def shouldWork9iEclipseLink() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/eclipselink-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 9
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP SEQUENCE SEQ_GEN_SEQUENCE;") > -1
    }

    def shouldWork8iEclipseLink() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/eclipselink-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 8
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));") > -1
        file("build/generated-schema/create.sql").text.indexOf("CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("DROP SEQUENCE SEQ_GEN_SEQUENCE;") > -1
    }


    def shouldWork11gHibernate() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/hibernate-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                namingStrategy = "org.hibernate.cfg.ImprovedNamingStrategy"
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 11
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create sequence hibernate_sequence;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("drop table key_value_store cascade constraints;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop table many_column_table cascade constraints;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop sequence hibernate_sequence;") > -1
    }

    def shouldWork10gHibernate() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/hibernate-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                namingStrategy = "org.hibernate.cfg.ImprovedNamingStrategy"
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
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
        file("build/generated-schema/create.sql").text.indexOf("create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create sequence hibernate_sequence;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("drop table key_value_store cascade constraints;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop table many_column_table cascade constraints;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop sequence hibernate_sequence;") > -1
    }

    def shouldWork9iHibernate() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/hibernate-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                namingStrategy = "org.hibernate.cfg.ImprovedNamingStrategy"
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 9
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create sequence hibernate_sequence;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("drop table key_value_store cascade constraints;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop table many_column_table cascade constraints;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop sequence hibernate_sequence;") > -1
    }

    def shouldWork8iHibernate() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src/java")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/hibernate-simple-test/resources")
                    }
                    output.resourcesDir output.classesDir
                }
            }
            
            generateSchema {
                namingStrategy = "org.hibernate.cfg.ImprovedNamingStrategy"
                targets {
                    oraclescript {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 8
                        databaseMinorVersion = 0
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text.indexOf("create table key_value_store (stored_key varchar2(128) not null, created_at date, stored_value long, primary key (stored_key));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create table many_column_table (id number(19,0) not null, column00 varchar2(255), column01 varchar2(255), column02 varchar2(255), column03 varchar2(255), column04 varchar2(255), column05 varchar2(255), column06 varchar2(255), column07 varchar2(255), column08 varchar2(255), column09 varchar2(255), column10 varchar2(255), column11 varchar2(255), column12 varchar2(255), column13 varchar2(255), column14 varchar2(255), column15 varchar2(255), column16 varchar2(255), column17 varchar2(255), column18 varchar2(255), column19 varchar2(255), column20 varchar2(255), column21 varchar2(255), column22 varchar2(255), column23 varchar2(255), column24 varchar2(255), column25 varchar2(255), column26 varchar2(255), column27 varchar2(255), column28 varchar2(255), column29 varchar2(255), primary key (id));") > -1
        file("build/generated-schema/create.sql").text.indexOf("create sequence hibernate_sequence;") > -1
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text.indexOf("drop table key_value_store cascade constraints;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop table many_column_table cascade constraints;") > -1
        file("build/generated-schema/drop.sql").text.indexOf("drop sequence hibernate_sequence;") > -1
    }
}
