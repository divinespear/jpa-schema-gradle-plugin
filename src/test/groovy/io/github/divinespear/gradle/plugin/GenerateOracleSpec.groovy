package io.github.divinespear.gradle.plugin

import nebula.test.IntegrationSpec

class GenerateOracleSpec extends IntegrationSpec {

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
                    script12c {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 12
                        databaseMinorVersion = 0
                        createOutputFileName = "12c-create.sql"
                        dropOutputFileName = "12c-drop.sql"
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
        runTasks "generateSchema"
        then:
        // script12g
        file("build/generated-schema/12c-create.sql").exists()
        file("build/generated-schema/12c-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;
"""
        file("build/generated-schema/12c-drop.sql").exists()
        file("build/generated-schema/12c-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_GEN_SEQUENCE;
"""
        // script11g
        file("build/generated-schema/11g-create.sql").exists()
        file("build/generated-schema/11g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;
"""
        file("build/generated-schema/11g-drop.sql").exists()
        file("build/generated-schema/11g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_GEN_SEQUENCE;
"""
        // script10g
        file("build/generated-schema/10g-create.sql").exists()
        file("build/generated-schema/10g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;
"""
        file("build/generated-schema/10g-drop.sql").exists()
        file("build/generated-schema/10g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_GEN_SEQUENCE;
"""
        // script9i
        file("build/generated-schema/9i-create.sql").exists()
        file("build/generated-schema/9i-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;
"""
        file("build/generated-schema/9i-drop.sql").exists()
        file("build/generated-schema/9i-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_GEN_SEQUENCE;
"""
        // script8i
        file("build/generated-schema/8i-create.sql").exists()
        file("build/generated-schema/8i-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR2(128) NOT NULL, CREATED_AT TIMESTAMP NULL, STORED_VALUE VARCHAR2(32768) NULL, PRIMARY KEY (STORED_KEY));
CREATE TABLE MANY_COLUMN_TABLE (ID NUMBER(19) NOT NULL, COLUMN00 VARCHAR2(255) NULL, COLUMN01 VARCHAR2(255) NULL, COLUMN02 VARCHAR2(255) NULL, COLUMN03 VARCHAR2(255) NULL, COLUMN04 VARCHAR2(255) NULL, COLUMN05 VARCHAR2(255) NULL, COLUMN06 VARCHAR2(255) NULL, COLUMN07 VARCHAR2(255) NULL, COLUMN08 VARCHAR2(255) NULL, COLUMN09 VARCHAR2(255) NULL, COLUMN10 VARCHAR2(255) NULL, COLUMN11 VARCHAR2(255) NULL, COLUMN12 VARCHAR2(255) NULL, COLUMN13 VARCHAR2(255) NULL, COLUMN14 VARCHAR2(255) NULL, COLUMN15 VARCHAR2(255) NULL, COLUMN16 VARCHAR2(255) NULL, COLUMN17 VARCHAR2(255) NULL, COLUMN18 VARCHAR2(255) NULL, COLUMN19 VARCHAR2(255) NULL, COLUMN20 VARCHAR2(255) NULL, COLUMN21 VARCHAR2(255) NULL, COLUMN22 VARCHAR2(255) NULL, COLUMN23 VARCHAR2(255) NULL, COLUMN24 VARCHAR2(255) NULL, COLUMN25 VARCHAR2(255) NULL, COLUMN26 VARCHAR2(255) NULL, COLUMN27 VARCHAR2(255) NULL, COLUMN28 VARCHAR2(255) NULL, COLUMN29 VARCHAR2(255) NULL, PRIMARY KEY (ID));
CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;
"""
        file("build/generated-schema/8i-drop.sql").exists()
        file("build/generated-schema/8i-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;
DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;
DROP SEQUENCE SEQ_GEN_SEQUENCE;
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
                    script12c {
                        scriptAction = "drop-and-create"
                        databaseProductName = "Oracle"
                        databaseMajorVersion = 12
                        databaseMinorVersion = 0
                        createOutputFileName = "12c-create.sql"
                        dropOutputFileName = "12c-drop.sql"
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
        runTasks "generateSchema"
        then:

        // script12g
        file("build/generated-schema/12c-create.sql").exists()
        file("build/generated-schema/12c-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (
\tSTORED_KEY VARCHAR2(128) NOT NULL,
\tCREATED_AT TIMESTAMP NULL,
\tSTORED_VALUE VARCHAR2(32768) NULL,
\tPRIMARY KEY (STORED_KEY)
);

CREATE TABLE MANY_COLUMN_TABLE (
\tID NUMBER(19) NOT NULL,
\tCOLUMN00 VARCHAR2(255) NULL,
\tCOLUMN01 VARCHAR2(255) NULL,
\tCOLUMN02 VARCHAR2(255) NULL,
\tCOLUMN03 VARCHAR2(255) NULL,
\tCOLUMN04 VARCHAR2(255) NULL,
\tCOLUMN05 VARCHAR2(255) NULL,
\tCOLUMN06 VARCHAR2(255) NULL,
\tCOLUMN07 VARCHAR2(255) NULL,
\tCOLUMN08 VARCHAR2(255) NULL,
\tCOLUMN09 VARCHAR2(255) NULL,
\tCOLUMN10 VARCHAR2(255) NULL,
\tCOLUMN11 VARCHAR2(255) NULL,
\tCOLUMN12 VARCHAR2(255) NULL,
\tCOLUMN13 VARCHAR2(255) NULL,
\tCOLUMN14 VARCHAR2(255) NULL,
\tCOLUMN15 VARCHAR2(255) NULL,
\tCOLUMN16 VARCHAR2(255) NULL,
\tCOLUMN17 VARCHAR2(255) NULL,
\tCOLUMN18 VARCHAR2(255) NULL,
\tCOLUMN19 VARCHAR2(255) NULL,
\tCOLUMN20 VARCHAR2(255) NULL,
\tCOLUMN21 VARCHAR2(255) NULL,
\tCOLUMN22 VARCHAR2(255) NULL,
\tCOLUMN23 VARCHAR2(255) NULL,
\tCOLUMN24 VARCHAR2(255) NULL,
\tCOLUMN25 VARCHAR2(255) NULL,
\tCOLUMN26 VARCHAR2(255) NULL,
\tCOLUMN27 VARCHAR2(255) NULL,
\tCOLUMN28 VARCHAR2(255) NULL,
\tCOLUMN29 VARCHAR2(255) NULL,
\tPRIMARY KEY (ID)
);

CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;

"""
        file("build/generated-schema/12c-drop.sql").exists()
        file("build/generated-schema/12c-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;

DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_GEN_SEQUENCE;

"""
        // script11g
        file("build/generated-schema/11g-create.sql").exists()
        file("build/generated-schema/11g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (
\tSTORED_KEY VARCHAR2(128) NOT NULL,
\tCREATED_AT TIMESTAMP NULL,
\tSTORED_VALUE VARCHAR2(32768) NULL,
\tPRIMARY KEY (STORED_KEY)
);

CREATE TABLE MANY_COLUMN_TABLE (
\tID NUMBER(19) NOT NULL,
\tCOLUMN00 VARCHAR2(255) NULL,
\tCOLUMN01 VARCHAR2(255) NULL,
\tCOLUMN02 VARCHAR2(255) NULL,
\tCOLUMN03 VARCHAR2(255) NULL,
\tCOLUMN04 VARCHAR2(255) NULL,
\tCOLUMN05 VARCHAR2(255) NULL,
\tCOLUMN06 VARCHAR2(255) NULL,
\tCOLUMN07 VARCHAR2(255) NULL,
\tCOLUMN08 VARCHAR2(255) NULL,
\tCOLUMN09 VARCHAR2(255) NULL,
\tCOLUMN10 VARCHAR2(255) NULL,
\tCOLUMN11 VARCHAR2(255) NULL,
\tCOLUMN12 VARCHAR2(255) NULL,
\tCOLUMN13 VARCHAR2(255) NULL,
\tCOLUMN14 VARCHAR2(255) NULL,
\tCOLUMN15 VARCHAR2(255) NULL,
\tCOLUMN16 VARCHAR2(255) NULL,
\tCOLUMN17 VARCHAR2(255) NULL,
\tCOLUMN18 VARCHAR2(255) NULL,
\tCOLUMN19 VARCHAR2(255) NULL,
\tCOLUMN20 VARCHAR2(255) NULL,
\tCOLUMN21 VARCHAR2(255) NULL,
\tCOLUMN22 VARCHAR2(255) NULL,
\tCOLUMN23 VARCHAR2(255) NULL,
\tCOLUMN24 VARCHAR2(255) NULL,
\tCOLUMN25 VARCHAR2(255) NULL,
\tCOLUMN26 VARCHAR2(255) NULL,
\tCOLUMN27 VARCHAR2(255) NULL,
\tCOLUMN28 VARCHAR2(255) NULL,
\tCOLUMN29 VARCHAR2(255) NULL,
\tPRIMARY KEY (ID)
);

CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;

"""
        file("build/generated-schema/11g-drop.sql").exists()
        file("build/generated-schema/11g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;

DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_GEN_SEQUENCE;

"""
        // script10g
        file("build/generated-schema/10g-create.sql").exists()
        file("build/generated-schema/10g-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (
\tSTORED_KEY VARCHAR2(128) NOT NULL,
\tCREATED_AT TIMESTAMP NULL,
\tSTORED_VALUE VARCHAR2(32768) NULL,
\tPRIMARY KEY (STORED_KEY)
);

CREATE TABLE MANY_COLUMN_TABLE (
\tID NUMBER(19) NOT NULL,
\tCOLUMN00 VARCHAR2(255) NULL,
\tCOLUMN01 VARCHAR2(255) NULL,
\tCOLUMN02 VARCHAR2(255) NULL,
\tCOLUMN03 VARCHAR2(255) NULL,
\tCOLUMN04 VARCHAR2(255) NULL,
\tCOLUMN05 VARCHAR2(255) NULL,
\tCOLUMN06 VARCHAR2(255) NULL,
\tCOLUMN07 VARCHAR2(255) NULL,
\tCOLUMN08 VARCHAR2(255) NULL,
\tCOLUMN09 VARCHAR2(255) NULL,
\tCOLUMN10 VARCHAR2(255) NULL,
\tCOLUMN11 VARCHAR2(255) NULL,
\tCOLUMN12 VARCHAR2(255) NULL,
\tCOLUMN13 VARCHAR2(255) NULL,
\tCOLUMN14 VARCHAR2(255) NULL,
\tCOLUMN15 VARCHAR2(255) NULL,
\tCOLUMN16 VARCHAR2(255) NULL,
\tCOLUMN17 VARCHAR2(255) NULL,
\tCOLUMN18 VARCHAR2(255) NULL,
\tCOLUMN19 VARCHAR2(255) NULL,
\tCOLUMN20 VARCHAR2(255) NULL,
\tCOLUMN21 VARCHAR2(255) NULL,
\tCOLUMN22 VARCHAR2(255) NULL,
\tCOLUMN23 VARCHAR2(255) NULL,
\tCOLUMN24 VARCHAR2(255) NULL,
\tCOLUMN25 VARCHAR2(255) NULL,
\tCOLUMN26 VARCHAR2(255) NULL,
\tCOLUMN27 VARCHAR2(255) NULL,
\tCOLUMN28 VARCHAR2(255) NULL,
\tCOLUMN29 VARCHAR2(255) NULL,
\tPRIMARY KEY (ID)
);

CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;

"""
        file("build/generated-schema/10g-drop.sql").exists()
        file("build/generated-schema/10g-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;

DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_GEN_SEQUENCE;

"""
        // script9i
        file("build/generated-schema/9i-create.sql").exists()
        file("build/generated-schema/9i-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (
\tSTORED_KEY VARCHAR2(128) NOT NULL,
\tCREATED_AT TIMESTAMP NULL,
\tSTORED_VALUE VARCHAR2(32768) NULL,
\tPRIMARY KEY (STORED_KEY)
);

CREATE TABLE MANY_COLUMN_TABLE (
\tID NUMBER(19) NOT NULL,
\tCOLUMN00 VARCHAR2(255) NULL,
\tCOLUMN01 VARCHAR2(255) NULL,
\tCOLUMN02 VARCHAR2(255) NULL,
\tCOLUMN03 VARCHAR2(255) NULL,
\tCOLUMN04 VARCHAR2(255) NULL,
\tCOLUMN05 VARCHAR2(255) NULL,
\tCOLUMN06 VARCHAR2(255) NULL,
\tCOLUMN07 VARCHAR2(255) NULL,
\tCOLUMN08 VARCHAR2(255) NULL,
\tCOLUMN09 VARCHAR2(255) NULL,
\tCOLUMN10 VARCHAR2(255) NULL,
\tCOLUMN11 VARCHAR2(255) NULL,
\tCOLUMN12 VARCHAR2(255) NULL,
\tCOLUMN13 VARCHAR2(255) NULL,
\tCOLUMN14 VARCHAR2(255) NULL,
\tCOLUMN15 VARCHAR2(255) NULL,
\tCOLUMN16 VARCHAR2(255) NULL,
\tCOLUMN17 VARCHAR2(255) NULL,
\tCOLUMN18 VARCHAR2(255) NULL,
\tCOLUMN19 VARCHAR2(255) NULL,
\tCOLUMN20 VARCHAR2(255) NULL,
\tCOLUMN21 VARCHAR2(255) NULL,
\tCOLUMN22 VARCHAR2(255) NULL,
\tCOLUMN23 VARCHAR2(255) NULL,
\tCOLUMN24 VARCHAR2(255) NULL,
\tCOLUMN25 VARCHAR2(255) NULL,
\tCOLUMN26 VARCHAR2(255) NULL,
\tCOLUMN27 VARCHAR2(255) NULL,
\tCOLUMN28 VARCHAR2(255) NULL,
\tCOLUMN29 VARCHAR2(255) NULL,
\tPRIMARY KEY (ID)
);

CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;

"""
        file("build/generated-schema/9i-drop.sql").exists()
        file("build/generated-schema/9i-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;

DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_GEN_SEQUENCE;

"""
        // script8i
        file("build/generated-schema/8i-create.sql").exists()
        file("build/generated-schema/8i-create.sql").text == """CREATE TABLE KEY_VALUE_STORE (
\tSTORED_KEY VARCHAR2(128) NOT NULL,
\tCREATED_AT TIMESTAMP NULL,
\tSTORED_VALUE VARCHAR2(32768) NULL,
\tPRIMARY KEY (STORED_KEY)
);

CREATE TABLE MANY_COLUMN_TABLE (
\tID NUMBER(19) NOT NULL,
\tCOLUMN00 VARCHAR2(255) NULL,
\tCOLUMN01 VARCHAR2(255) NULL,
\tCOLUMN02 VARCHAR2(255) NULL,
\tCOLUMN03 VARCHAR2(255) NULL,
\tCOLUMN04 VARCHAR2(255) NULL,
\tCOLUMN05 VARCHAR2(255) NULL,
\tCOLUMN06 VARCHAR2(255) NULL,
\tCOLUMN07 VARCHAR2(255) NULL,
\tCOLUMN08 VARCHAR2(255) NULL,
\tCOLUMN09 VARCHAR2(255) NULL,
\tCOLUMN10 VARCHAR2(255) NULL,
\tCOLUMN11 VARCHAR2(255) NULL,
\tCOLUMN12 VARCHAR2(255) NULL,
\tCOLUMN13 VARCHAR2(255) NULL,
\tCOLUMN14 VARCHAR2(255) NULL,
\tCOLUMN15 VARCHAR2(255) NULL,
\tCOLUMN16 VARCHAR2(255) NULL,
\tCOLUMN17 VARCHAR2(255) NULL,
\tCOLUMN18 VARCHAR2(255) NULL,
\tCOLUMN19 VARCHAR2(255) NULL,
\tCOLUMN20 VARCHAR2(255) NULL,
\tCOLUMN21 VARCHAR2(255) NULL,
\tCOLUMN22 VARCHAR2(255) NULL,
\tCOLUMN23 VARCHAR2(255) NULL,
\tCOLUMN24 VARCHAR2(255) NULL,
\tCOLUMN25 VARCHAR2(255) NULL,
\tCOLUMN26 VARCHAR2(255) NULL,
\tCOLUMN27 VARCHAR2(255) NULL,
\tCOLUMN28 VARCHAR2(255) NULL,
\tCOLUMN29 VARCHAR2(255) NULL,
\tPRIMARY KEY (ID)
);

CREATE SEQUENCE SEQ_GEN_SEQUENCE INCREMENT BY 50 START WITH 50;

"""
        file("build/generated-schema/8i-drop.sql").exists()
        file("build/generated-schema/8i-drop.sql").text == """DROP TABLE KEY_VALUE_STORE CASCADE CONSTRAINTS;

DROP TABLE MANY_COLUMN_TABLE CASCADE CONSTRAINTS;

DROP SEQUENCE SEQ_GEN_SEQUENCE;

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
        runTasks "generateSchema"
        then:
        // script11g
        file("build/generated-schema/11g-create.sql").exists()
        file("build/generated-schema/11g-create.sql").text == """create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));
create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));
create sequence hibernate_sequence;
"""
        file("build/generated-schema/11g-drop.sql").exists()
        file("build/generated-schema/11g-drop.sql").text == """drop table key_value_store cascade constraints;
drop table many_column_table cascade constraints;
drop sequence hibernate_sequence;
"""
        // script10g
        file("build/generated-schema/10g-create.sql").exists()
        file("build/generated-schema/10g-create.sql").text == """create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));
create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));
create sequence hibernate_sequence;
"""
        file("build/generated-schema/10g-drop.sql").exists()
        file("build/generated-schema/10g-drop.sql").text == """drop table key_value_store cascade constraints;
drop table many_column_table cascade constraints;
drop sequence hibernate_sequence;
"""
        // script9i
        file("build/generated-schema/9i-create.sql").exists()
        file("build/generated-schema/9i-create.sql").text == """create table key_value_store (stored_key varchar2(128 char) not null, created_at timestamp, stored_value long, primary key (stored_key));
create table many_column_table (id number(19,0) not null, column00 varchar2(255 char), column01 varchar2(255 char), column02 varchar2(255 char), column03 varchar2(255 char), column04 varchar2(255 char), column05 varchar2(255 char), column06 varchar2(255 char), column07 varchar2(255 char), column08 varchar2(255 char), column09 varchar2(255 char), column10 varchar2(255 char), column11 varchar2(255 char), column12 varchar2(255 char), column13 varchar2(255 char), column14 varchar2(255 char), column15 varchar2(255 char), column16 varchar2(255 char), column17 varchar2(255 char), column18 varchar2(255 char), column19 varchar2(255 char), column20 varchar2(255 char), column21 varchar2(255 char), column22 varchar2(255 char), column23 varchar2(255 char), column24 varchar2(255 char), column25 varchar2(255 char), column26 varchar2(255 char), column27 varchar2(255 char), column28 varchar2(255 char), column29 varchar2(255 char), primary key (id));
create sequence hibernate_sequence;
"""
        file("build/generated-schema/9i-drop.sql").exists()
        file("build/generated-schema/9i-drop.sql").text == """drop table key_value_store cascade constraints;
drop table many_column_table cascade constraints;
drop sequence hibernate_sequence;
"""
        // script8i
        file("build/generated-schema/8i-create.sql").exists()
        file("build/generated-schema/8i-create.sql").text == """create table key_value_store (stored_key varchar2(128) not null, created_at date, stored_value long, primary key (stored_key));
create table many_column_table (id number(19,0) not null, column00 varchar2(255), column01 varchar2(255), column02 varchar2(255), column03 varchar2(255), column04 varchar2(255), column05 varchar2(255), column06 varchar2(255), column07 varchar2(255), column08 varchar2(255), column09 varchar2(255), column10 varchar2(255), column11 varchar2(255), column12 varchar2(255), column13 varchar2(255), column14 varchar2(255), column15 varchar2(255), column16 varchar2(255), column17 varchar2(255), column18 varchar2(255), column19 varchar2(255), column20 varchar2(255), column21 varchar2(255), column22 varchar2(255), column23 varchar2(255), column24 varchar2(255), column25 varchar2(255), column26 varchar2(255), column27 varchar2(255), column28 varchar2(255), column29 varchar2(255), primary key (id));
create sequence hibernate_sequence;
"""
        file("build/generated-schema/8i-drop.sql").exists()
        file("build/generated-schema/8i-drop.sql").text == """drop table key_value_store cascade constraints;
drop table many_column_table cascade constraints;
drop sequence hibernate_sequence;
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
        runTasks "generateSchema"
        then:
        // script11g
        file("build/generated-schema/11g-create.sql").exists()
        file("build/generated-schema/11g-create.sql").text == """create table key_value_store (
\tstored_key varchar2(128 char) not null,
\tcreated_at timestamp,
\tstored_value long,
\tprimary key (stored_key)
);

create table many_column_table (
\tid number(19,0) not null,
\tcolumn00 varchar2(255 char),
\tcolumn01 varchar2(255 char),
\tcolumn02 varchar2(255 char),
\tcolumn03 varchar2(255 char),
\tcolumn04 varchar2(255 char),
\tcolumn05 varchar2(255 char),
\tcolumn06 varchar2(255 char),
\tcolumn07 varchar2(255 char),
\tcolumn08 varchar2(255 char),
\tcolumn09 varchar2(255 char),
\tcolumn10 varchar2(255 char),
\tcolumn11 varchar2(255 char),
\tcolumn12 varchar2(255 char),
\tcolumn13 varchar2(255 char),
\tcolumn14 varchar2(255 char),
\tcolumn15 varchar2(255 char),
\tcolumn16 varchar2(255 char),
\tcolumn17 varchar2(255 char),
\tcolumn18 varchar2(255 char),
\tcolumn19 varchar2(255 char),
\tcolumn20 varchar2(255 char),
\tcolumn21 varchar2(255 char),
\tcolumn22 varchar2(255 char),
\tcolumn23 varchar2(255 char),
\tcolumn24 varchar2(255 char),
\tcolumn25 varchar2(255 char),
\tcolumn26 varchar2(255 char),
\tcolumn27 varchar2(255 char),
\tcolumn28 varchar2(255 char),
\tcolumn29 varchar2(255 char),
\tprimary key (id)
);

create sequence hibernate_sequence;

"""
        file("build/generated-schema/11g-drop.sql").exists()
        file("build/generated-schema/11g-drop.sql").text == """drop table key_value_store cascade constraints;

drop table many_column_table cascade constraints;

drop sequence hibernate_sequence;

"""
        // script10g
        file("build/generated-schema/10g-create.sql").exists()
        file("build/generated-schema/10g-create.sql").text == """create table key_value_store (
\tstored_key varchar2(128 char) not null,
\tcreated_at timestamp,
\tstored_value long,
\tprimary key (stored_key)
);

create table many_column_table (
\tid number(19,0) not null,
\tcolumn00 varchar2(255 char),
\tcolumn01 varchar2(255 char),
\tcolumn02 varchar2(255 char),
\tcolumn03 varchar2(255 char),
\tcolumn04 varchar2(255 char),
\tcolumn05 varchar2(255 char),
\tcolumn06 varchar2(255 char),
\tcolumn07 varchar2(255 char),
\tcolumn08 varchar2(255 char),
\tcolumn09 varchar2(255 char),
\tcolumn10 varchar2(255 char),
\tcolumn11 varchar2(255 char),
\tcolumn12 varchar2(255 char),
\tcolumn13 varchar2(255 char),
\tcolumn14 varchar2(255 char),
\tcolumn15 varchar2(255 char),
\tcolumn16 varchar2(255 char),
\tcolumn17 varchar2(255 char),
\tcolumn18 varchar2(255 char),
\tcolumn19 varchar2(255 char),
\tcolumn20 varchar2(255 char),
\tcolumn21 varchar2(255 char),
\tcolumn22 varchar2(255 char),
\tcolumn23 varchar2(255 char),
\tcolumn24 varchar2(255 char),
\tcolumn25 varchar2(255 char),
\tcolumn26 varchar2(255 char),
\tcolumn27 varchar2(255 char),
\tcolumn28 varchar2(255 char),
\tcolumn29 varchar2(255 char),
\tprimary key (id)
);

create sequence hibernate_sequence;

"""
        file("build/generated-schema/10g-drop.sql").exists()
        file("build/generated-schema/10g-drop.sql").text == """drop table key_value_store cascade constraints;

drop table many_column_table cascade constraints;

drop sequence hibernate_sequence;

"""
        // script9i
        file("build/generated-schema/9i-create.sql").exists()
        file("build/generated-schema/9i-create.sql").text == """create table key_value_store (
\tstored_key varchar2(128 char) not null,
\tcreated_at timestamp,
\tstored_value long,
\tprimary key (stored_key)
);

create table many_column_table (
\tid number(19,0) not null,
\tcolumn00 varchar2(255 char),
\tcolumn01 varchar2(255 char),
\tcolumn02 varchar2(255 char),
\tcolumn03 varchar2(255 char),
\tcolumn04 varchar2(255 char),
\tcolumn05 varchar2(255 char),
\tcolumn06 varchar2(255 char),
\tcolumn07 varchar2(255 char),
\tcolumn08 varchar2(255 char),
\tcolumn09 varchar2(255 char),
\tcolumn10 varchar2(255 char),
\tcolumn11 varchar2(255 char),
\tcolumn12 varchar2(255 char),
\tcolumn13 varchar2(255 char),
\tcolumn14 varchar2(255 char),
\tcolumn15 varchar2(255 char),
\tcolumn16 varchar2(255 char),
\tcolumn17 varchar2(255 char),
\tcolumn18 varchar2(255 char),
\tcolumn19 varchar2(255 char),
\tcolumn20 varchar2(255 char),
\tcolumn21 varchar2(255 char),
\tcolumn22 varchar2(255 char),
\tcolumn23 varchar2(255 char),
\tcolumn24 varchar2(255 char),
\tcolumn25 varchar2(255 char),
\tcolumn26 varchar2(255 char),
\tcolumn27 varchar2(255 char),
\tcolumn28 varchar2(255 char),
\tcolumn29 varchar2(255 char),
\tprimary key (id)
);

create sequence hibernate_sequence;

"""
        file("build/generated-schema/9i-drop.sql").exists()
        file("build/generated-schema/9i-drop.sql").text == """drop table key_value_store cascade constraints;

drop table many_column_table cascade constraints;

drop sequence hibernate_sequence;

"""
        // script8i
        file("build/generated-schema/8i-create.sql").exists()
        file("build/generated-schema/8i-create.sql").text == """create table key_value_store (
\tstored_key varchar2(128) not null,
\tcreated_at date,
\tstored_value long,
\tprimary key (stored_key)
);

create table many_column_table (
\tid number(19,0) not null,
\tcolumn00 varchar2(255),
\tcolumn01 varchar2(255),
\tcolumn02 varchar2(255),
\tcolumn03 varchar2(255),
\tcolumn04 varchar2(255),
\tcolumn05 varchar2(255),
\tcolumn06 varchar2(255),
\tcolumn07 varchar2(255),
\tcolumn08 varchar2(255),
\tcolumn09 varchar2(255),
\tcolumn10 varchar2(255),
\tcolumn11 varchar2(255),
\tcolumn12 varchar2(255),
\tcolumn13 varchar2(255),
\tcolumn14 varchar2(255),
\tcolumn15 varchar2(255),
\tcolumn16 varchar2(255),
\tcolumn17 varchar2(255),
\tcolumn18 varchar2(255),
\tcolumn19 varchar2(255),
\tcolumn20 varchar2(255),
\tcolumn21 varchar2(255),
\tcolumn22 varchar2(255),
\tcolumn23 varchar2(255),
\tcolumn24 varchar2(255),
\tcolumn25 varchar2(255),
\tcolumn26 varchar2(255),
\tcolumn27 varchar2(255),
\tcolumn28 varchar2(255),
\tcolumn29 varchar2(255),
\tprimary key (id)
);

create sequence hibernate_sequence;

"""
        file("build/generated-schema/8i-drop.sql").exists()
        file("build/generated-schema/8i-drop.sql").text == """drop table key_value_store cascade constraints;

drop table many_column_table cascade constraints;

drop sequence hibernate_sequence;

"""
    }
}
