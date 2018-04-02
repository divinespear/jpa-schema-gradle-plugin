jpa-schema-gradle-plugin
========================

[![Build Status](https://secure.travis-ci.org/divinespear/jpa-schema-gradle-plugin.png)](http://travis-ci.org/divinespear/jpa-schema-gradle-plugin)

Gradle plugin for generate schema or DDL scripts from JPA entities using [JPA 2.1](http://jcp.org/en/jsr/detail?id=338) schema generator.
for Maven, see [Maven Plugin](//github.com/divinespear/jpa-schema-maven-plugin).

Currently support [EclipseLink](http://www.eclipse.org/eclipselink) (Reference Implementation) and [Hibernate](http://hibernate.org).

## Before Announce...

READ MY LIP; **JPA DDL GENERATOR IS NOT SILVER BULLET**

Sometimes (*most times* exactly :P) JPA will generate weird scripts so you **SHOULD** modify them properly.

## Release 0.3

* Required Gradle 4.2.1 or above. (for support Java 9)
* Required JDK 8 or above.
* No more `output.resourcesDir = output.classesDir` needed.
* No more `buildscript` dependencies needed.
* Dropped support DataNucleus, it was my mistake.

### Reworking on 0.3

* Minimized spring dependency, only include `spring-orm`, `spring-context`, `spring-aspects` and its dependencies. (based on spring 5.0)
* Will improve test with each major release version of each JPA providers.
* Re-implemented with [Kotlin](https://kotlinlang.org), on my self-training.

If you have discussions, please make issue. discussions are always welcome.

## How-to Use

see [Gradle Plugins Registry](https://plugins.gradle.org/plugin/io.github.divinespear.jpa-schema-generate).

```groovy
plugins {
  id 'io.github.divinespear.jpa-schema-generate' version '0.3.2'
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

You **MUST** specify two options: `vendor` and `packageToScan`.
```groovy
generateSchema {
  vendor = 'hibernate' // 'eclipselink' or 'hibernate'.
                       // you can use class name too. (like 'org.hibernate.jpa.HibernatePersistenceProvider')
  packageToScan = [ 'your.package.to.scan', ... ]
  ...
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
| `vendor` | `string` | JPA vendor name or class name of vendor's `PersistenceProvider` implemention.<p>vendor name is one of <ul><li>`eclipselink`(or `org.eclipse.persistence.jpa.PersistenceProvider`)</li><li>`hibernate` (or `org.hibernate.jpa.HibernatePersistenceProvider`)</li></ul></p><p>**REQUIRED for project without `persistence.xml`**</p> |
| `packageToScan` | `java.util.List` | list of package name for scan entity classes<p>**REQUIRED for project without `persistence.xml`**</p> |

#### How-to config `properties`

It's just groovy map, so you can config like this:
```groovy
generateSchema {
  ...
  // global properties
  properties = [
    'hibernate.dialect': 'org.hibernate.dialect.MySQL5InnoDBDialect',
    ...
  ]
  // you can set target-specific too.
  ...
}
```

## Database Product Names

It's about `databaseProductName` property. If not listed below, will work as basic standard SQL.

### for EclipseLink
`databaseMajorVersion` and `databaseMinorVersion` is not required.

* `Oracle12` = Oracle 12c
* `Oracle11` = Oracle 11g
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
Some products uses different dialect by `databaseMajorVersion` and/or `databaseMinorVersion`.
You can override using `hibernate.dialect` property.

* `CUBRID`
    * `org.hibernate.dialect.CUBRIDDialect` = all version
* `HSQL Database Engine`
    * `org.hibernate.dialect.HSQLDialect` = all version
* `H2`
    * `org.hibernate.dialect.H2Dialect` = all version
* `MySQL`
    * `org.hibernate.dialect.MySQL5Dialect` = 5.x
    * `org.hibernate.dialect.MySQLDialect` = 4.x or below
    * `org.hibernate.dialect.MySQLMyISAMDialect`
    * `org.hibernate.dialect.MySQLInnoDBDialect`
    * `org.hibernate.dialect.MySQL5InnoDBDialect`
    * `org.hibernate.dialect.MySQL57InnoDBDialect`
* `PostgreSQL`
    * `org.hibernate.dialect.PostgreSQL94Dialect` = 9.4 or above
    * `org.hibernate.dialect.PostgreSQL92Dialect` = 9.2 or above
    * `org.hibernate.dialect.PostgreSQL9Dialect` = 9.x
    * `org.hibernate.dialect.PostgreSQL82Dialect` = 8.2 or above
    * `org.hibernate.dialect.PostgreSQL81Dialect` = 8.1 or below
* `Apache Derby`
    * `org.hibernate.dialect.DerbyTenSevenDialect` = 10.7 or above
    * `org.hibernate.dialect.DerbyTenSixDialect` = 10.6
    * `org.hibernate.dialect.DerbyTenFiveDialect` = 10.5
    * `org.hibernate.dialect.DerbyDialect` = 10.4 or below
* `ingres`
    * `org.hibernate.dialect.Ingres10Dialect` = 10.x
    * `org.hibernate.dialect.Ingres9Dialect` = 9.2 or above
    * `org.hibernate.dialect.IngresDialect` = 9.1 or below
* `Microsoft SQL Server`
    * `org.hibernate.dialect.SQLServer2012Dialect` = 11.x
    * `org.hibernate.dialect.SQLServer2008Dialect` = 10.x
    * `org.hibernate.dialect.SQLServer2005Dialect` = 9.x
    * `org.hibernate.dialect.SQLServerDialect` = 8.x or below
* `Sybase SQL Server`
    * `org.hibernate.dialect.SybaseASE15Dialect` = all version
    * `org.hibernate.dialect.SybaseASE17Dialect`
* `Adaptive Server Enterprise` = Sybase
* `Adaptive Server Anywhere`
    * `org.hibernate.dialect.SybaseAnywhereDialect` = all version
* `Informix Dynamic Server`
    * `org.hibernate.dialect.InformixDialect` = all version
* ~~`DB2 UDB for AS/390`~~
    * `org.hibernate.dialect.DB2390Dialect`
* `DB2 UDB for AS/400`
    * `org.hibernate.dialect.DB2400Dialect` = all version
*  start with `DB2/`
    * `org.hibernate.dialect.DB2Dialect` = all version
* `Oracle`
    * `org.hibernate.dialect.Oracle12cDialect` = 12.x
    * `org.hibernate.dialect.Oracle10gDialect` = 11.x, 10.x
    * `org.hibernate.dialect.Oracle9iDialect` = 9.x
    * `org.hibernate.dialect.Oracle8iDialect` = 8.x or below
* `Firebird`
    * `org.hibernate.dialect.FirebirdDialect` = all version


## License

Source Copyright © 2013-2018 Sin Young "Divinespear" Kang (divinespear at gmail dot com). Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses).
