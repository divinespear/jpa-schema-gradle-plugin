package io.github.divinespear

import org.gradle.test.FunctionalSpec

class GenerateOracleSpec extends FunctionalSpec {

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
                    script12g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 12
                        databaseMinorVersion = 0
                        createOutputFileName = "12g-create.sql"
                        dropOutputFileName = "12g-drop.sql"
                    }
                    script11g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 11
                        databaseMinorVersion = 0
                        createOutputFileName = "11g-create.sql"
                        dropOutputFileName = "11g-drop.sql"
                    }
                    script10g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 10
                        databaseMinorVersion = 0
                        createOutputFileName = "10g-create.sql"
                        dropOutputFileName = "10g-drop.sql"
                    }
                    script9i {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 9
                        databaseMinorVersion = 0
                        createOutputFileName = "9i-create.sql"
                        dropOutputFileName = "9i-drop.sql"
                    }
                    script8i {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 8
                        databaseMinorVersion = 0
                        createOutputFileName = "8i-create.sql"
                        dropOutputFileName = "8i-drop.sql"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        // script12g
        file("build/generated-schema/12g-create.sql").exists()
        file("build/generated-schema/12g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
"""
        file("build/generated-schema/12g-drop.sql").exists()
        file("build/generated-schema/12g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
"""
        // script11g
        file("build/generated-schema/11g-create.sql").exists()
        file("build/generated-schema/11g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
"""
        file("build/generated-schema/11g-drop.sql").exists()
        file("build/generated-schema/11g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
"""
        // script10g
        file("build/generated-schema/10g-create.sql").exists()
        file("build/generated-schema/10g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
"""
        file("build/generated-schema/10g-drop.sql").exists()
        file("build/generated-schema/10g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
"""
        // script9i
        file("build/generated-schema/9i-create.sql").exists()
        file("build/generated-schema/9i-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
"""
        file("build/generated-schema/9i-drop.sql").exists()
        file("build/generated-schema/9i-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
"""
        // script8i
        file("build/generated-schema/8i-create.sql").exists()
        file("build/generated-schema/8i-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));\r
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
"""
        file("build/generated-schema/8i-drop.sql").exists()
        file("build/generated-schema/8i-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
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
                targets {
                    script12g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 12
                        databaseMinorVersion = 0
                        createOutputFileName = "12g-create.sql"
                        dropOutputFileName = "12g-drop.sql"
                    }
                    script11g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 11
                        databaseMinorVersion = 0
                        createOutputFileName = "11g-create.sql"
                        dropOutputFileName = "11g-drop.sql"
                    }
                    script10g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 10
                        databaseMinorVersion = 0
                        createOutputFileName = "10g-create.sql"
                        dropOutputFileName = "10g-drop.sql"
                    }
                    script9i {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 9
                        databaseMinorVersion = 0
                        createOutputFileName = "9i-create.sql"
                        dropOutputFileName = "9i-drop.sql"
                    }
                    script8i {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 8
                        databaseMinorVersion = 0
                        createOutputFileName = "8i-create.sql"
                        dropOutputFileName = "8i-drop.sql"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:

        // script12g
        file("build/generated-schema/12g-create.sql").exists()
        file("build/generated-schema/12g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (\r
\tSTORED_KEY VARCHAR2(128) NOT NULL,\r
\tCREATED_AT TIMESTAMP NULL,\r
\tSTORED_VALUE VARCHAR2(32768) NULL,\r
\tPRIMARY KEY (STORED_KEY)\r
);\r
\r
CREATE TABLE MANY_COLUMN_TABLE (\r
\tID NUMBER(19) NOT NULL,\r
\tCOLUMN00 VARCHAR2(255) NULL,\r
\tCOLUMN01 VARCHAR2(255) NULL,\r
\tCOLUMN02 VARCHAR2(255) NULL,\r
\tCOLUMN03 VARCHAR2(255) NULL,\r
\tCOLUMN04 VARCHAR2(255) NULL,\r
\tCOLUMN05 VARCHAR2(255) NULL,\r
\tCOLUMN06 VARCHAR2(255) NULL,\r
\tCOLUMN07 VARCHAR2(255) NULL,\r
\tCOLUMN08 VARCHAR2(255) NULL,\r
\tCOLUMN09 VARCHAR2(255) NULL,\r
\tCOLUMN10 VARCHAR2(255) NULL,\r
\tCOLUMN11 VARCHAR2(255) NULL,\r
\tCOLUMN12 VARCHAR2(255) NULL,\r
\tCOLUMN13 VARCHAR2(255) NULL,\r
\tCOLUMN14 VARCHAR2(255) NULL,\r
\tCOLUMN15 VARCHAR2(255) NULL,\r
\tCOLUMN16 VARCHAR2(255) NULL,\r
\tCOLUMN17 VARCHAR2(255) NULL,\r
\tCOLUMN18 VARCHAR2(255) NULL,\r
\tCOLUMN19 VARCHAR2(255) NULL,\r
\tCOLUMN20 VARCHAR2(255) NULL,\r
\tCOLUMN21 VARCHAR2(255) NULL,\r
\tCOLUMN22 VARCHAR2(255) NULL,\r
\tCOLUMN23 VARCHAR2(255) NULL,\r
\tCOLUMN24 VARCHAR2(255) NULL,\r
\tCOLUMN25 VARCHAR2(255) NULL,\r
\tCOLUMN26 VARCHAR2(255) NULL,\r
\tCOLUMN27 VARCHAR2(255) NULL,\r
\tCOLUMN28 VARCHAR2(255) NULL,\r
\tCOLUMN29 VARCHAR2(255) NULL,\r
\tPRIMARY KEY (ID)\r
);\r
\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
\r
"""
        file("build/generated-schema/12g-drop.sql").exists()
        file("build/generated-schema/12g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
\r
"""
        // script11g
        file("build/generated-schema/11g-create.sql").exists()
        file("build/generated-schema/11g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (\r
\tSTORED_KEY VARCHAR2(128) NOT NULL,\r
\tCREATED_AT TIMESTAMP NULL,\r
\tSTORED_VALUE VARCHAR2(32768) NULL,\r
\tPRIMARY KEY (STORED_KEY)\r
);\r
\r
CREATE TABLE MANY_COLUMN_TABLE (\r
\tID NUMBER(19) NOT NULL,\r
\tCOLUMN00 VARCHAR2(255) NULL,\r
\tCOLUMN01 VARCHAR2(255) NULL,\r
\tCOLUMN02 VARCHAR2(255) NULL,\r
\tCOLUMN03 VARCHAR2(255) NULL,\r
\tCOLUMN04 VARCHAR2(255) NULL,\r
\tCOLUMN05 VARCHAR2(255) NULL,\r
\tCOLUMN06 VARCHAR2(255) NULL,\r
\tCOLUMN07 VARCHAR2(255) NULL,\r
\tCOLUMN08 VARCHAR2(255) NULL,\r
\tCOLUMN09 VARCHAR2(255) NULL,\r
\tCOLUMN10 VARCHAR2(255) NULL,\r
\tCOLUMN11 VARCHAR2(255) NULL,\r
\tCOLUMN12 VARCHAR2(255) NULL,\r
\tCOLUMN13 VARCHAR2(255) NULL,\r
\tCOLUMN14 VARCHAR2(255) NULL,\r
\tCOLUMN15 VARCHAR2(255) NULL,\r
\tCOLUMN16 VARCHAR2(255) NULL,\r
\tCOLUMN17 VARCHAR2(255) NULL,\r
\tCOLUMN18 VARCHAR2(255) NULL,\r
\tCOLUMN19 VARCHAR2(255) NULL,\r
\tCOLUMN20 VARCHAR2(255) NULL,\r
\tCOLUMN21 VARCHAR2(255) NULL,\r
\tCOLUMN22 VARCHAR2(255) NULL,\r
\tCOLUMN23 VARCHAR2(255) NULL,\r
\tCOLUMN24 VARCHAR2(255) NULL,\r
\tCOLUMN25 VARCHAR2(255) NULL,\r
\tCOLUMN26 VARCHAR2(255) NULL,\r
\tCOLUMN27 VARCHAR2(255) NULL,\r
\tCOLUMN28 VARCHAR2(255) NULL,\r
\tCOLUMN29 VARCHAR2(255) NULL,\r
\tPRIMARY KEY (ID)\r
);\r
\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
\r
"""
        file("build/generated-schema/11g-drop.sql").exists()
        file("build/generated-schema/11g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
\r
"""
        // script10g
        file("build/generated-schema/10g-create.sql").exists()
        file("build/generated-schema/10g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (\r
\tSTORED_KEY VARCHAR2(128) NOT NULL,\r
\tCREATED_AT TIMESTAMP NULL,\r
\tSTORED_VALUE VARCHAR2(32768) NULL,\r
\tPRIMARY KEY (STORED_KEY)\r
);\r
\r
CREATE TABLE MANY_COLUMN_TABLE (\r
\tID NUMBER(19) NOT NULL,\r
\tCOLUMN00 VARCHAR2(255) NULL,\r
\tCOLUMN01 VARCHAR2(255) NULL,\r
\tCOLUMN02 VARCHAR2(255) NULL,\r
\tCOLUMN03 VARCHAR2(255) NULL,\r
\tCOLUMN04 VARCHAR2(255) NULL,\r
\tCOLUMN05 VARCHAR2(255) NULL,\r
\tCOLUMN06 VARCHAR2(255) NULL,\r
\tCOLUMN07 VARCHAR2(255) NULL,\r
\tCOLUMN08 VARCHAR2(255) NULL,\r
\tCOLUMN09 VARCHAR2(255) NULL,\r
\tCOLUMN10 VARCHAR2(255) NULL,\r
\tCOLUMN11 VARCHAR2(255) NULL,\r
\tCOLUMN12 VARCHAR2(255) NULL,\r
\tCOLUMN13 VARCHAR2(255) NULL,\r
\tCOLUMN14 VARCHAR2(255) NULL,\r
\tCOLUMN15 VARCHAR2(255) NULL,\r
\tCOLUMN16 VARCHAR2(255) NULL,\r
\tCOLUMN17 VARCHAR2(255) NULL,\r
\tCOLUMN18 VARCHAR2(255) NULL,\r
\tCOLUMN19 VARCHAR2(255) NULL,\r
\tCOLUMN20 VARCHAR2(255) NULL,\r
\tCOLUMN21 VARCHAR2(255) NULL,\r
\tCOLUMN22 VARCHAR2(255) NULL,\r
\tCOLUMN23 VARCHAR2(255) NULL,\r
\tCOLUMN24 VARCHAR2(255) NULL,\r
\tCOLUMN25 VARCHAR2(255) NULL,\r
\tCOLUMN26 VARCHAR2(255) NULL,\r
\tCOLUMN27 VARCHAR2(255) NULL,\r
\tCOLUMN28 VARCHAR2(255) NULL,\r
\tCOLUMN29 VARCHAR2(255) NULL,\r
\tPRIMARY KEY (ID)\r
);\r
\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
\r
"""
        file("build/generated-schema/10g-drop.sql").exists()
        file("build/generated-schema/10g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
\r
"""
        // script9i
        file("build/generated-schema/9i-create.sql").exists()
        file("build/generated-schema/9i-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (\r
\tSTORED_KEY VARCHAR2(128) NOT NULL,\r
\tCREATED_AT TIMESTAMP NULL,\r
\tSTORED_VALUE VARCHAR2(32768) NULL,\r
\tPRIMARY KEY (STORED_KEY)\r
);\r
\r
CREATE TABLE MANY_COLUMN_TABLE (\r
\tID NUMBER(19) NOT NULL,\r
\tCOLUMN00 VARCHAR2(255) NULL,\r
\tCOLUMN01 VARCHAR2(255) NULL,\r
\tCOLUMN02 VARCHAR2(255) NULL,\r
\tCOLUMN03 VARCHAR2(255) NULL,\r
\tCOLUMN04 VARCHAR2(255) NULL,\r
\tCOLUMN05 VARCHAR2(255) NULL,\r
\tCOLUMN06 VARCHAR2(255) NULL,\r
\tCOLUMN07 VARCHAR2(255) NULL,\r
\tCOLUMN08 VARCHAR2(255) NULL,\r
\tCOLUMN09 VARCHAR2(255) NULL,\r
\tCOLUMN10 VARCHAR2(255) NULL,\r
\tCOLUMN11 VARCHAR2(255) NULL,\r
\tCOLUMN12 VARCHAR2(255) NULL,\r
\tCOLUMN13 VARCHAR2(255) NULL,\r
\tCOLUMN14 VARCHAR2(255) NULL,\r
\tCOLUMN15 VARCHAR2(255) NULL,\r
\tCOLUMN16 VARCHAR2(255) NULL,\r
\tCOLUMN17 VARCHAR2(255) NULL,\r
\tCOLUMN18 VARCHAR2(255) NULL,\r
\tCOLUMN19 VARCHAR2(255) NULL,\r
\tCOLUMN20 VARCHAR2(255) NULL,\r
\tCOLUMN21 VARCHAR2(255) NULL,\r
\tCOLUMN22 VARCHAR2(255) NULL,\r
\tCOLUMN23 VARCHAR2(255) NULL,\r
\tCOLUMN24 VARCHAR2(255) NULL,\r
\tCOLUMN25 VARCHAR2(255) NULL,\r
\tCOLUMN26 VARCHAR2(255) NULL,\r
\tCOLUMN27 VARCHAR2(255) NULL,\r
\tCOLUMN28 VARCHAR2(255) NULL,\r
\tCOLUMN29 VARCHAR2(255) NULL,\r
\tPRIMARY KEY (ID)\r
);\r
\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
\r
"""
        file("build/generated-schema/9i-drop.sql").exists()
        file("build/generated-schema/9i-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
\r
"""
        // script8i
        file("build/generated-schema/8i-create.sql").exists()
        file("build/generated-schema/8i-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (\r
\tSTORED_KEY VARCHAR2(128) NOT NULL,\r
\tCREATED_AT TIMESTAMP NULL,\r
\tSTORED_VALUE VARCHAR2(32768) NULL,\r
\tPRIMARY KEY (STORED_KEY)\r
);\r
\r
CREATE TABLE MANY_COLUMN_TABLE (\r
\tID NUMBER(19) NOT NULL,\r
\tCOLUMN00 VARCHAR2(255) NULL,\r
\tCOLUMN01 VARCHAR2(255) NULL,\r
\tCOLUMN02 VARCHAR2(255) NULL,\r
\tCOLUMN03 VARCHAR2(255) NULL,\r
\tCOLUMN04 VARCHAR2(255) NULL,\r
\tCOLUMN05 VARCHAR2(255) NULL,\r
\tCOLUMN06 VARCHAR2(255) NULL,\r
\tCOLUMN07 VARCHAR2(255) NULL,\r
\tCOLUMN08 VARCHAR2(255) NULL,\r
\tCOLUMN09 VARCHAR2(255) NULL,\r
\tCOLUMN10 VARCHAR2(255) NULL,\r
\tCOLUMN11 VARCHAR2(255) NULL,\r
\tCOLUMN12 VARCHAR2(255) NULL,\r
\tCOLUMN13 VARCHAR2(255) NULL,\r
\tCOLUMN14 VARCHAR2(255) NULL,\r
\tCOLUMN15 VARCHAR2(255) NULL,\r
\tCOLUMN16 VARCHAR2(255) NULL,\r
\tCOLUMN17 VARCHAR2(255) NULL,\r
\tCOLUMN18 VARCHAR2(255) NULL,\r
\tCOLUMN19 VARCHAR2(255) NULL,\r
\tCOLUMN20 VARCHAR2(255) NULL,\r
\tCOLUMN21 VARCHAR2(255) NULL,\r
\tCOLUMN22 VARCHAR2(255) NULL,\r
\tCOLUMN23 VARCHAR2(255) NULL,\r
\tCOLUMN24 VARCHAR2(255) NULL,\r
\tCOLUMN25 VARCHAR2(255) NULL,\r
\tCOLUMN26 VARCHAR2(255) NULL,\r
\tCOLUMN27 VARCHAR2(255) NULL,\r
\tCOLUMN28 VARCHAR2(255) NULL,\r
\tCOLUMN29 VARCHAR2(255) NULL,\r
\tPRIMARY KEY (ID)\r
);\r
\r
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;\r
\r
"""
        file("build/generated-schema/8i-drop.sql").exists()
        file("build/generated-schema/8i-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;\r
\r
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;\r
\r
DROP SEQUENCE SEQ_GEN_SEQUENCE;\r
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
                    script11g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 11
                        databaseMinorVersion = 0
                        createOutputFileName = "11g-create.sql"
                        dropOutputFileName = "11g-drop.sql"
                    }
                    script10g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 10
                        databaseMinorVersion = 0
                        createOutputFileName = "10g-create.sql"
                        dropOutputFileName = "10g-drop.sql"
                    }
                    script9i {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 9
                        databaseMinorVersion = 0
                        createOutputFileName = "9i-create.sql"
                        dropOutputFileName = "9i-drop.sql"
                    }
                    script8i {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 8
                        databaseMinorVersion = 0
                        createOutputFileName = "8i-create.sql"
                        dropOutputFileName = "8i-drop.sql"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        // script11g
        file("build/generated-schema/11g-create.sql").exists()
        file("build/generated-schema/11g-create.sql").text == """create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));\r
create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));\r
create sequence hibernate_sequence;\r
"""
        file("build/generated-schema/11g-drop.sql").exists()
        file("build/generated-schema/11g-drop.sql").text == """drop table key_value_store cascade constraints;\r
drop table many_column_table cascade constraints;\r
drop sequence hibernate_sequence;\r
"""
        // script10g
        file("build/generated-schema/10g-create.sql").exists()
        file("build/generated-schema/10g-create.sql").text == """create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));\r
create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));\r
create sequence hibernate_sequence;\r
"""
        file("build/generated-schema/10g-drop.sql").exists()
        file("build/generated-schema/10g-drop.sql").text == """drop table key_value_store cascade constraints;\r
drop table many_column_table cascade constraints;\r
drop sequence hibernate_sequence;\r
"""
        // script9i
        file("build/generated-schema/9i-create.sql").exists()
        file("build/generated-schema/9i-create.sql").text == """create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));\r
create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));\r
create sequence hibernate_sequence;\r
"""
        file("build/generated-schema/9i-drop.sql").exists()
        file("build/generated-schema/9i-drop.sql").text == """drop table key_value_store cascade constraints;\r
drop table many_column_table cascade constraints;\r
drop sequence hibernate_sequence;\r
"""
        // script8i
        file("build/generated-schema/8i-create.sql").exists()
        file("build/generated-schema/8i-create.sql").text == """create table key_value_store (stored_key varchar2(128) not null, created_at date, stored_value long, primary key (stored_key));\r
create table many_column_table (id number(19,0) not null, column00 varchar2(255), column01 varchar2(255), column02 varchar2(255), column03 varchar2(255), column04 varchar2(255), column05 varchar2(255), column06 varchar2(255), column07 varchar2(255), column08 varchar2(255), column09 varchar2(255), column10 varchar2(255), column11 varchar2(255), column12 varchar2(255), column13 varchar2(255), column14 varchar2(255), column15 varchar2(255), column16 varchar2(255), column17 varchar2(255), column18 varchar2(255), column19 varchar2(255), column20 varchar2(255), column21 varchar2(255), column22 varchar2(255), column23 varchar2(255), column24 varchar2(255), column25 varchar2(255), column26 varchar2(255), column27 varchar2(255), column28 varchar2(255), column29 varchar2(255), primary key (id));\r
create sequence hibernate_sequence;\r
"""
        file("build/generated-schema/8i-drop.sql").exists()
        file("build/generated-schema/8i-drop.sql").text == """drop table key_value_store cascade constraints;\r
drop table many_column_table cascade constraints;\r
drop sequence hibernate_sequence;\r
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
                    script11g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 11
                        databaseMinorVersion = 0
                        createOutputFileName = "11g-create.sql"
                        dropOutputFileName = "11g-drop.sql"
                    }
                    script10g {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 10
                        databaseMinorVersion = 0
                        createOutputFileName = "10g-create.sql"
                        dropOutputFileName = "10g-drop.sql"
                    }
                    script9i {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 9
                        databaseMinorVersion = 0
                        createOutputFileName = "9i-create.sql"
                        dropOutputFileName = "9i-drop.sql"
                    }
                    script8i {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 8
                        databaseMinorVersion = 0
                        createOutputFileName = "8i-create.sql"
                        dropOutputFileName = "8i-drop.sql"
                    }
                }
            }
        """
        when:
        run "generateSchema"
        then:
        // script11g
        file("build/generated-schema/11g-create.sql").exists()
        file("build/generated-schema/11g-create.sql").text == """create table key_value_store (\r
\tstored_key varchar2(128 char) not null,\r
\tcreated_at timestamp,\r
\tstored_value long,\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid number(19,0) not null,\r
\tcolumn00 varchar2(255 char),\r
\tcolumn01 varchar2(255 char),\r
\tcolumn02 varchar2(255 char),\r
\tcolumn03 varchar2(255 char),\r
\tcolumn04 varchar2(255 char),\r
\tcolumn05 varchar2(255 char),\r
\tcolumn06 varchar2(255 char),\r
\tcolumn07 varchar2(255 char),\r
\tcolumn08 varchar2(255 char),\r
\tcolumn09 varchar2(255 char),\r
\tcolumn10 varchar2(255 char),\r
\tcolumn11 varchar2(255 char),\r
\tcolumn12 varchar2(255 char),\r
\tcolumn13 varchar2(255 char),\r
\tcolumn14 varchar2(255 char),\r
\tcolumn15 varchar2(255 char),\r
\tcolumn16 varchar2(255 char),\r
\tcolumn17 varchar2(255 char),\r
\tcolumn18 varchar2(255 char),\r
\tcolumn19 varchar2(255 char),\r
\tcolumn20 varchar2(255 char),\r
\tcolumn21 varchar2(255 char),\r
\tcolumn22 varchar2(255 char),\r
\tcolumn23 varchar2(255 char),\r
\tcolumn24 varchar2(255 char),\r
\tcolumn25 varchar2(255 char),\r
\tcolumn26 varchar2(255 char),\r
\tcolumn27 varchar2(255 char),\r
\tcolumn28 varchar2(255 char),\r
\tcolumn29 varchar2(255 char),\r
\tprimary key (id)\r
);\r
\r
create sequence hibernate_sequence;\r
\r
"""
        file("build/generated-schema/11g-drop.sql").exists()
        file("build/generated-schema/11g-drop.sql").text == """drop table key_value_store cascade constraints;\r
\r
drop table many_column_table cascade constraints;\r
\r
drop sequence hibernate_sequence;\r
\r
"""
        // script10g
        file("build/generated-schema/10g-create.sql").exists()
        file("build/generated-schema/10g-create.sql").text == """create table key_value_store (\r
\tstored_key varchar2(128 char) not null,\r
\tcreated_at timestamp,\r
\tstored_value long,\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid number(19,0) not null,\r
\tcolumn00 varchar2(255 char),\r
\tcolumn01 varchar2(255 char),\r
\tcolumn02 varchar2(255 char),\r
\tcolumn03 varchar2(255 char),\r
\tcolumn04 varchar2(255 char),\r
\tcolumn05 varchar2(255 char),\r
\tcolumn06 varchar2(255 char),\r
\tcolumn07 varchar2(255 char),\r
\tcolumn08 varchar2(255 char),\r
\tcolumn09 varchar2(255 char),\r
\tcolumn10 varchar2(255 char),\r
\tcolumn11 varchar2(255 char),\r
\tcolumn12 varchar2(255 char),\r
\tcolumn13 varchar2(255 char),\r
\tcolumn14 varchar2(255 char),\r
\tcolumn15 varchar2(255 char),\r
\tcolumn16 varchar2(255 char),\r
\tcolumn17 varchar2(255 char),\r
\tcolumn18 varchar2(255 char),\r
\tcolumn19 varchar2(255 char),\r
\tcolumn20 varchar2(255 char),\r
\tcolumn21 varchar2(255 char),\r
\tcolumn22 varchar2(255 char),\r
\tcolumn23 varchar2(255 char),\r
\tcolumn24 varchar2(255 char),\r
\tcolumn25 varchar2(255 char),\r
\tcolumn26 varchar2(255 char),\r
\tcolumn27 varchar2(255 char),\r
\tcolumn28 varchar2(255 char),\r
\tcolumn29 varchar2(255 char),\r
\tprimary key (id)\r
);\r
\r
create sequence hibernate_sequence;\r
\r
"""
        file("build/generated-schema/10g-drop.sql").exists()
        file("build/generated-schema/10g-drop.sql").text == """drop table key_value_store cascade constraints;\r
\r
drop table many_column_table cascade constraints;\r
\r
drop sequence hibernate_sequence;\r
\r
"""
        // script9i
        file("build/generated-schema/9i-create.sql").exists()
        file("build/generated-schema/9i-create.sql").text == """create table key_value_store (\r
\tstored_key varchar2(128 char) not null,\r
\tcreated_at timestamp,\r
\tstored_value long,\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid number(19,0) not null,\r
\tcolumn00 varchar2(255 char),\r
\tcolumn01 varchar2(255 char),\r
\tcolumn02 varchar2(255 char),\r
\tcolumn03 varchar2(255 char),\r
\tcolumn04 varchar2(255 char),\r
\tcolumn05 varchar2(255 char),\r
\tcolumn06 varchar2(255 char),\r
\tcolumn07 varchar2(255 char),\r
\tcolumn08 varchar2(255 char),\r
\tcolumn09 varchar2(255 char),\r
\tcolumn10 varchar2(255 char),\r
\tcolumn11 varchar2(255 char),\r
\tcolumn12 varchar2(255 char),\r
\tcolumn13 varchar2(255 char),\r
\tcolumn14 varchar2(255 char),\r
\tcolumn15 varchar2(255 char),\r
\tcolumn16 varchar2(255 char),\r
\tcolumn17 varchar2(255 char),\r
\tcolumn18 varchar2(255 char),\r
\tcolumn19 varchar2(255 char),\r
\tcolumn20 varchar2(255 char),\r
\tcolumn21 varchar2(255 char),\r
\tcolumn22 varchar2(255 char),\r
\tcolumn23 varchar2(255 char),\r
\tcolumn24 varchar2(255 char),\r
\tcolumn25 varchar2(255 char),\r
\tcolumn26 varchar2(255 char),\r
\tcolumn27 varchar2(255 char),\r
\tcolumn28 varchar2(255 char),\r
\tcolumn29 varchar2(255 char),\r
\tprimary key (id)\r
);\r
\r
create sequence hibernate_sequence;\r
\r
"""
        file("build/generated-schema/9i-drop.sql").exists()
        file("build/generated-schema/9i-drop.sql").text == """drop table key_value_store cascade constraints;\r
\r
drop table many_column_table cascade constraints;\r
\r
drop sequence hibernate_sequence;\r
\r
"""
        // script8i
        file("build/generated-schema/8i-create.sql").exists()
        file("build/generated-schema/8i-create.sql").text == """create table key_value_store (\r
\tstored_key varchar2(128) not null,\r
\tcreated_at date,\r
\tstored_value long,\r
\tprimary key (stored_key)\r
);\r
\r
create table many_column_table (\r
\tid number(19,0) not null,\r
\tcolumn00 varchar2(255),\r
\tcolumn01 varchar2(255),\r
\tcolumn02 varchar2(255),\r
\tcolumn03 varchar2(255),\r
\tcolumn04 varchar2(255),\r
\tcolumn05 varchar2(255),\r
\tcolumn06 varchar2(255),\r
\tcolumn07 varchar2(255),\r
\tcolumn08 varchar2(255),\r
\tcolumn09 varchar2(255),\r
\tcolumn10 varchar2(255),\r
\tcolumn11 varchar2(255),\r
\tcolumn12 varchar2(255),\r
\tcolumn13 varchar2(255),\r
\tcolumn14 varchar2(255),\r
\tcolumn15 varchar2(255),\r
\tcolumn16 varchar2(255),\r
\tcolumn17 varchar2(255),\r
\tcolumn18 varchar2(255),\r
\tcolumn19 varchar2(255),\r
\tcolumn20 varchar2(255),\r
\tcolumn21 varchar2(255),\r
\tcolumn22 varchar2(255),\r
\tcolumn23 varchar2(255),\r
\tcolumn24 varchar2(255),\r
\tcolumn25 varchar2(255),\r
\tcolumn26 varchar2(255),\r
\tcolumn27 varchar2(255),\r
\tcolumn28 varchar2(255),\r
\tcolumn29 varchar2(255),\r
\tprimary key (id)\r
);\r
\r
create sequence hibernate_sequence;\r
\r
"""
        file("build/generated-schema/8i-drop.sql").exists()
        file("build/generated-schema/8i-drop.sql").text == """drop table key_value_store cascade constraints;\r
\r
drop table many_column_table cascade constraints;\r
\r
drop sequence hibernate_sequence;\r
\r
"""
    }
}
