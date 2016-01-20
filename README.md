jpa-schema-gradle-plugin
========================

[![Build Status](https://secure.travis-ci.org/divinespear/jpa-schema-gradle-plugin.png)](http://travis-ci.org/divinespear/jpa-schema-gradle-plugin)

Gradle plugin for generate schema or DDL scripts from JPA entities using [JPA 2.1](http://jcp.org/en/jsr/detail?id=338) schema generator.
for Maven, see [Maven Plugin](//github.com/divinespear/jpa-schema-maven-plugin).

Currently support [EclipseLink](http://www.eclipse.org/eclipselink) (Reference Implementation) and [Hibernate](http://hibernate.org).


## 0.2.x Plan
* Support generate without `persistence.xml` (like spring-data, spring-boot, ...) related [#14](//github.com/divinespear/jpa-schema-gradle-plugin/issues/14)
* Support more JPA 2.1 implementations (and also support JPA 2.0 implementations if possible)
* [Drop support Java 1.6](http://www.oracle.com/technetwork/java/javase/eol-135779.html)

* and Drop support Gradle 1.x

## Before Announce...

READ MY LIP; **JPA DDL GENERATOR IS NOT SILVER BULLET**

Sometimes (*most times* exactly :P) JPA will generate weird scripts so you **SHOULD** modify them properly.


## How-to Use

Put this to your `build.gradle`

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "io.github.divinespear:jpa-schema-gradle-plugin:+"
        // jdbc drivers also here
        ...
    }
}

apply plugin: "java"
apply plugin: "jpa-schema-generate"

sourceSets {
    main {
    	// set output to same directories
    	// jpa implementations always scan classes using classpath that found persistence.xml
        output.resourcesDir = output.classesDir
    }
}

generateSchema {
	// default options
	// see SchemaGenerationConfig to all options
	...
	// if you want multiple output
	targets {
		targetName {
			// same as default options
			...
		}
	}
}
```

To generate schema, run
```
gradle generateSchema
```
or
```
./gradlew generateSchema
```

see also test cases `Generate*Spec.groovy`, as examples.

#### without `persistence.xml`

you **MUST** specify two options: `vendor` and `packageToScan`.
```groovy
generateSchema {
    vendor = 'hibernate' // 'eclipselink' or 'hibernate'.
                         // you can use class name too. (like 'org.hibernate.jpa.HibernatePersistenceProvider')
    packageToScan = [ 'your.package.to.scan', ... ]
    ...
}
```

#### For Scala

You MUST put `scala-library` to `dependencies` of `buildscript`.

```groovy
buildscript {
    ...
    dependencies {
        ...
        classpath "org.scala-lang:scala-library:${your_scala_version}"
        ...
    }
}
```

#### For Hibernate

Hibernate **DOES NOT SUPPORT** `@GeneratedValue(strategy = GenerationType.SEQUENCE)` for DBMS dosen't support `CREATE/DROP SEQUENCE`. ~~WTF?!~~ You should use `@GeneratedValue` instead.

#### For EclipseLink with Oracle

EclipseLink's `Oracle{8,9,10,11}Platform` uses some type classes from Oracle's JDBC driver. you should have it in your dependency.

### SchemaGenerationConfig

Here is full list of parameters of `generateSchema`.

| name | type | description |
| ---- | ---- | ---- |
| `skip` | `boolean` | skip schema generation<p>default value is `false`.</p> |
| `format` | `boolean` | generate as formatted<p>default value is `false`.</p> |
| `scanTestClasses` | `boolean`| scan test classes<p>default value is `false`.</p> |
| `persistenceXml` | `string` | location of `persistence.xml` file<p>Note: **Hibernate DOES NOT SUPPORT custom location.** (`SchemaExport` support it, but JPA 2.1 schema generator does NOT.)</p><p>default value is `META-INF/persistence.xml`.</p> |
| `persistenceUnitName` | `string` | unit name of `persistence.xml`<p>default value is `default`.</p> |
| `databaseAction` | `string` | schema generation action for database<p>support value is one of <ul><li>`none`</li><li>`create`</li><li>`drop`</li><li>`drop-and-create`</li><li>`create-or-extend-tables` (only for EclipseLink with database target)</li></ul></p><p>default value is `none`.</p> |
| `scriptAction` | `string` | schema generation action for script<p>support value is one of <ul><li>`none`</li><li>`create`</li><li>`drop`</li><li>`drop-and-create`</li></ul></p><p>default value is `none`.</p> |
| `outputDirectory` | `file` | output directory for generated ddl scripts<p>REQUIRED for `scriptAction` is one of `create`, `drop`, or `drop-and-create`.</p><p>default value is `${project.buildDir}/generated-schema`.</p> |
| `createOutputFileName` | `string` | generated create script name<p>REQUIRED for `scriptAction` is one of `create`, or `drop-and-create`.</p><p>default value is `{targetName}-create.sql` if `targetName` presented, otherwise `create.sql`.</p> |
| `dropOutputFileName` | `string` | generated drop script name<p>REQUIRED for `scriptAction` is one of `drop`, or `drop-and-create`.</p><p>default value is `{targetName}-drop.sql` if `targetName` presented, otherwise `drop.sql`.</p> |
| `createSourceMode` | `string` | specifies whether the creation of database artifacts is to occur on the basis of the object/relational mappingmetadata, DDL script, or a combination of the two.<p>support value is one of <ul><li>`metadata`</li><li>`script`</li><li>`metadata-then-script`</li><li>`script-then-metadata`</li></ul></p><p>default value is `metadata`.</p> |
| `createSourceFile` | `string` | create source file path.<p>REQUIRED for `createSourceMode` is one of `script`, `metadata-then-script`, or`script-then-metadata`.</p> |
| `dropSourceMode` | `string` | specifies whether the dropping of database artifacts is to occur on the basis of the object/relational mappingmetadata, DDL script, or a combination of the two.<p>support value is one of <ul><li>`metadata`</li><li>`script`</li><li>`metadata-then-script`</li><li>`script-then-metadata`</li></ul></p><p>default value is `metadata`.</p> |
| `dropSourceFile` | `file` | drop source file path.<p>REQUIRED for `dropSourceMode` is one of `script`, `metadata-then-script`, or`script-then-metadata`.</p> |
| `jdbcDriver` | `string` | jdbc driver class name<p>default is declared class name in persistence xml.</p><p>and Remember, ~~[No Russian](http://callofduty.wikia.com/wiki/No_Russian)~~ you MUST configure jdbc driver to dependencies.</p> |
| `jdbcUrl` | `string` | jdbc connection url<p>default is declared connection url in persistence xml.</p> |
| `jdbcUser` | `string` | jdbc connection username<p>default is declared username in persistence xml.</p> |
| `jdbcPassword` | `string` | jdbc connection password<p>default is declared password in persistence xml.</p><p>If your account has no password (especially local file-base, like Apache Derby, H2, etc...), it can be omitted.</p> |
| `databaseProductName` | `string` | database product name for emulate database connection. this should useful for script-only action.<ul><li>specified if scripts are to be generated by the persistence provider and a connection to the target databaseis not supplied.</li><li>The value of this property should be the value returned for the target database by `DatabaseMetaData#getDatabaseProductName()`</li></ul> |
| `databaseMajorVersion` | `int` | database major version for emulate database connection. this should useful for script-only action.<ul><li>specified if sufficient database version information is not included from `DatabaseMetaData#getDatabaseProductName()`</li><li>The value of this property should be the value returned for the target database by `DatabaseMetaData#getDatabaseMajorVersion()`</li></ul> |
| `databaseMinorVersion` | `int` | database minor version for emulate database connection. this should useful for script-only action.<ul><li>specified if sufficient database version information is not included from `DatabaseMetaData#getDatabaseProductName()`</li><li>The value of this property should be the value returned for the target database by `DatabaseMetaData#getDatabaseMinorVersion()`</li></ul> |
| `lineSeparator` | `string` | line separator for generated schema file.<p>support value is one of <code>CRLF</code> (windows default), <code>LF</code> (*nix, max osx), and <code>CR</code> (classic mac), in case-insensitive.</p><p>default value is system property <code>line.separator</code>. if JVM cannot detect <code>line.separator</code>, then use <code>LF</code> by <a href="http://git-scm.com/book/en/Customizing-Git-Git-Configuration">git <code>core.autocrlf</code> handling</a>.</p> |
| `properties` | `java.util.Map` | JPA vendor specific properties. |
| `vendor` | `string` | JPA vendor name or class name of vendor's `PersistenceProvider` implemention.<p>vendor name is one of `eclipselink` (`org.eclipse.persistence.jpa.PersistenceProvider`) or `hibernate` (`org.hibernate.jpa.HibernatePersistenceProvider`)</p><p>**REQUIRED for project without `persistence.xml`**</p> |
| `packageToScan` | `java.util.List` | list of package name for scan entity classes<p>**REQUIRED for project without `persistence.xml`**</p> |

`namingStrategy` and `dialect` is removed cause Hibernate 4.x to 5.x is cataclysm. please use `properties` instead.

#### How-to config `properties`

It's just groovy map, so you can config like this:
```groovy
generateSchema {
    ...
    // global properties
    properties = [
        "hibernate.dialect": "org.hibernate.dialect.MySQL5InnoDBDialect",
        ...
    ]
    // you can set target-specific too.
    ...
}
```

Note: target properties will merged over global properties.

## Database Product Names

It's about `databaseProductName` property. If not listed below, will work as basic standard SQL.

### for EclipseLink
`databaseMajorVersion` and `databaseMinorVersion` is not required.

* `Oracle11`: Oracle 11g
* `Oracle10`: Oracle 10g
* `Oracle9`: Oracle 9i
* `Oracle`: Oracle with default compatibility
* `Microsoft SQL Server`
* `DB2`
* `MySQL`
* `PostgreSQL`
* `SQL Anywhere`
* `Sybase SQL Server`
* `Adaptive Server Enterprise` = Sybase
* `Pointbase`
* `Informix Dynamic Server`
* `Firebird`
* `ingres`
* `Apache Derby`
* `H2`
* `HSQL Database Engine`

### for Hibernate
some products uses different dialect by `databaseMajorVersion` and/or `databaseMinorVersion`.

* `CUBRID`
* `HSQL Database Engine`
* `H2`
* `MySQL`: 5.0 or above, 4.x or below
* `PostgreSQL`: 9.x, 8.2 or above, 8.1 or below
* `Apache Derby`: 10.7 or above, 10.6, 10.5, 10.4 or below
* `ingres`: 10.x, 9.2 or above, 9.1 or below
* `Microsoft SQL Server`: 11.x, 10.x, 9.x, 8.x or below
* `Sybase SQL Server`
* `Adaptive Server Enterprise` = Sybase
* `Adaptive Server Anywhere` = Sybase Anywhere
* `Informix Dynamic Server`
* `DB2 UDB for AS/400`
*  start with `DB2/`
* `Oracle`: 11.x, 10.x, 9.x, 8.x
* `Firebird`


## License

Source Copyright © 2013 Sin-young "Divinespear" Kang. Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses).
