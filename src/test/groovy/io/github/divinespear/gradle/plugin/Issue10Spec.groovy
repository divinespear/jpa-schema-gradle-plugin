package io.github.divinespear.gradle.plugin

import nebula.test.IntegrationSpec

class Issue10Spec extends IntegrationSpec {
//
//    def setup() {
//        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
//    }
//
//    def shouldWorkWithLineSeparatorCRLF() {
//        given:
//        buildFile << """
//            sourceSets {
//                main {
//                    java {
//                        srcDir file("../../../../src/test/resources/unit/src")
//                    }
//                    resources {
//                        srcDir file("../../../../src/test/resources/unit/resources/eclipselink")
//                    }
//                    output.resourcesDir output.classesDir
//                }
//            }
//
//            generateSchema {
//                scriptAction = "drop-and-create"
//                databaseProductName = "H2"
//                databaseMajorVersion = 1
//                databaseMinorVersion = 3
//                lineSeparator = "crlf"
//            }
//        """
//        when:
//        runTasks "generateSchema"
//        then:
//        file("build/generated-schema/create.sql").exists()
//        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT TIMESTAMP, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));\r
//CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT NOT NULL, COLUMN00 VARCHAR, COLUMN01 VARCHAR, COLUMN02 VARCHAR, COLUMN03 VARCHAR, COLUMN04 VARCHAR, COLUMN05 VARCHAR, COLUMN06 VARCHAR, COLUMN07 VARCHAR, COLUMN08 VARCHAR, COLUMN09 VARCHAR, COLUMN10 VARCHAR, COLUMN11 VARCHAR, COLUMN12 VARCHAR, COLUMN13 VARCHAR, COLUMN14 VARCHAR, COLUMN15 VARCHAR, COLUMN16 VARCHAR, COLUMN17 VARCHAR, COLUMN18 VARCHAR, COLUMN19 VARCHAR, COLUMN20 VARCHAR, COLUMN21 VARCHAR, COLUMN22 VARCHAR, COLUMN23 VARCHAR, COLUMN24 VARCHAR, COLUMN25 VARCHAR, COLUMN26 VARCHAR, COLUMN27 VARCHAR, COLUMN28 VARCHAR, COLUMN29 VARCHAR, PRIMARY KEY (ID));\r
//CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT NUMERIC(38), PRIMARY KEY (SEQ_NAME));\r
//INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0);\r
//"""
//        file("build/generated-schema/drop.sql").exists()
//        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE;\r
//DROP TABLE MANY_COLUMN_TABLE;\r
//DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN';\r
//"""
//    }
//
//    def shouldWorkWithLineSeparatorLF() {
//        given:
//        buildFile << """
//            sourceSets {
//                main {
//                    java {
//                        srcDir file("../../../../src/test/resources/unit/src")
//                    }
//                    resources {
//                        srcDir file("../../../../src/test/resources/unit/resources/eclipselink")
//                    }
//                    output.resourcesDir output.classesDir
//                }
//            }
//
//            generateSchema {
//                scriptAction = "drop-and-create"
//                databaseProductName = "H2"
//                databaseMajorVersion = 1
//                databaseMinorVersion = 3
//                lineSeparator = "lf"
//            }
//        """
//        when:
//        runTasks "generateSchema"
//        then:
//        file("build/generated-schema/create.sql").exists()
//        file("build/generated-schema/create.sql").text == """CREATE TABLE KEY_VALUE_STORE (STORED_KEY VARCHAR(128) NOT NULL, CREATED_AT TIMESTAMP, STORED_VALUE VARCHAR(32768), PRIMARY KEY (STORED_KEY));
//CREATE TABLE MANY_COLUMN_TABLE (ID BIGINT NOT NULL, COLUMN00 VARCHAR, COLUMN01 VARCHAR, COLUMN02 VARCHAR, COLUMN03 VARCHAR, COLUMN04 VARCHAR, COLUMN05 VARCHAR, COLUMN06 VARCHAR, COLUMN07 VARCHAR, COLUMN08 VARCHAR, COLUMN09 VARCHAR, COLUMN10 VARCHAR, COLUMN11 VARCHAR, COLUMN12 VARCHAR, COLUMN13 VARCHAR, COLUMN14 VARCHAR, COLUMN15 VARCHAR, COLUMN16 VARCHAR, COLUMN17 VARCHAR, COLUMN18 VARCHAR, COLUMN19 VARCHAR, COLUMN20 VARCHAR, COLUMN21 VARCHAR, COLUMN22 VARCHAR, COLUMN23 VARCHAR, COLUMN24 VARCHAR, COLUMN25 VARCHAR, COLUMN26 VARCHAR, COLUMN27 VARCHAR, COLUMN28 VARCHAR, COLUMN29 VARCHAR, PRIMARY KEY (ID));
//CREATE TABLE SEQUENCE (SEQ_NAME VARCHAR(50) NOT NULL, SEQ_COUNT NUMERIC(38), PRIMARY KEY (SEQ_NAME));
//INSERT INTO SEQUENCE(SEQ_NAME, SEQ_COUNT) values ('SEQ_GEN', 0);
//"""
//        file("build/generated-schema/drop.sql").exists()
//        file("build/generated-schema/drop.sql").text == """DROP TABLE KEY_VALUE_STORE;
//DROP TABLE MANY_COLUMN_TABLE;
//DELETE FROM SEQUENCE WHERE SEQ_NAME = 'SEQ_GEN';
//"""
//    }
}
