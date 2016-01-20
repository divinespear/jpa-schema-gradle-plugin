package io.github.divinespear.gradle.plugin

import nebula.test.IntegrationSpec

class Issue5Spec extends IntegrationSpec {

    def setup() {
        buildFile << applyPlugin(JpaSchemaGeneratePlugin)
    }
    
    def shouldWorkHibernateWithValidationAPI() {
        given:
        buildFile << """
            sourceSets {
                main {
                    java {
                        srcDir file("../../../../src/test/resources/unit/src")
                    }
                    resources {
                        srcDir file("../../../../src/test/resources/unit/resources/hibernate")
                    }
                    output.resourcesDir output.classesDir
                }
            }


            repositories {
                mavenCentral()
            }
            
            
            dependencies {
                compile 'org.hibernate:hibernate-entitymanager:5.0.7.Final'
                compile 'javax.validation:validation-api:1.1.0.Final'
                runtime 'org.hibernate:hibernate-validator:5.1.0.Final'
            }
            
            generateSchema {
                format = true
                scriptAction = "drop-and-create"
                databaseProductName = "H2"
                databaseMajorVersion = 1
                databaseMinorVersion = 3
            }
        """
        when:
        runTasks "generateSchema"
        then:
        file("build/generated-schema/create.sql").exists()
        file("build/generated-schema/create.sql").text == """create sequence hibernate_sequence start with 1 increment by 1;

create table KEY_VALUE_STORE (
\tSTORED_KEY varchar(128) not null,
\tCREATED_AT timestamp,
\tSTORED_VALUE varchar(32768),
\tprimary key (STORED_KEY)
);

create table MANY_COLUMN_TABLE (
\tid bigint not null,
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
        file("build/generated-schema/drop.sql").exists()
        file("build/generated-schema/drop.sql").text == """drop table KEY_VALUE_STORE if exists;

drop table MANY_COLUMN_TABLE if exists;

drop sequence if exists hibernate_sequence;

"""
    }
}
