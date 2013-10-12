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
                    oracle12gscript {
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
}
