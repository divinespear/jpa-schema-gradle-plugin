jpa-schema-gradle-plugin
========================

[![Build Status](https://secure.travis-ci.org/divinespear/jpa-schema-gradle-plugin.png)](http://travis-ci.org/divinespear/jpa-schema-gradle-plugin)

Gradle plugin for generate schema or DDL scripts from JPA entities using [JPA 2.1](http://jcp.org/en/jsr/detail?id=338) schema generator.
for Maven, see [Maven Plugin](https://github.com/divinespear/jpa-schema-maven-plugin).

Currently support [EclipseLink](http://www.eclipse.org/eclipselink) (Reference Implementation) and [Hibernate](http://hibernate.org).

## Before Announce...

READ MY LIP; **JPA DDL GENERATOR IS NOT SILVER BULLET**

Sometimes (*most times* exactly :P) JPA will generate weird scripts so you **SHOULD** modify them properly.

## Version History

See Releases for more informations...

### 0.4 (in progress...)

* Java 10, 11, 12, and 13 support.
* Required JDK 8 or above.
* Required Gradle 6.0 or above. (for support Java 13)

### 0.3.6

* Required Gradle 4.10 or above. (for support spring-boot plugin version 2.0+)

### 0.3

* Required Gradle 4.0 or above.
* Java 9 support.
* Required JDK 8 or above.
* No more `output.resourcesDir = output.classesDir` needed.
* No more `buildscript` dependencies needed.
* Dropped support DataNucleus, it was my mistake.
* Required Gradle 6.0 or above. (for support Java 13)

### Reworking on 0.3

* Minimized spring dependency, only include `spring-orm`, `spring-context`, `spring-aspects` and its dependencies. (based on spring 5.x)
* Direct including any JPA implementation is removed, remains [JUST API](http://doki-doki-literature-club.wikia.com/wiki/Monika).
* Will improve test with each major release version of each JPA providers.
* Re-implemented with [Kotlin](https://kotlinlang.org), on my self-training.

If you have discussions, please make issue. discussions are always welcome.

## How-to Use

see [Gradle Plugins Registry](https://plugins.gradle.org/plugin/io.github.divinespear.jpa-schema-generate).

```groovy
plugins {
  id 'io.github.divinespear.jpa-schema-generate' version '0.3.6'
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

### without `persistence.xml`

You **MUST** specify two options: `vendor` and `packageToScan`.
```groovy
generateSchema {
  vendor = 'hibernate' // 'eclipselink', 'hibernate', or 'hibernate+spring'.
                       // you can use class name too. (like 'org.hibernate.jpa.HibernatePersistenceProvider')
  packageToScan = [ 'your.package.to.scan', ... ]
  ...
}
```

## Plugin only dependencies

Since 0.3.4, you can add dependencies for plugin with configuration `generateSchema`.

```groovy
// no need to add 'generateSchema' into configurations block.

dependencies {
  ...
  compile 'org.springframework.boot:spring-boot-starter-data-jpa'
  // only need to load java.time converter from spring-data-jpa on schema generation
  generateSchema 'org.threeten:threetenbp:1.3.6'
}

generateSchema {
  ...  
  packageToScan = [
    // load java.time converter from spring-data-jpa
    'org.springframework.data.jpa.convert.threeten',
    'your.package.to.scan',
    ...
  ]
  ...
}
```

## Provider specific

### EclipseLink

 * EclipseLink 2.5 on Java 9 without `persistence.xml` will not work.
 * EclipseLink 2.6 on Java 12 or higher will not work. embedded ASM library cannot read class files.
 * EclipseLink's `Oracle{8,9,10,11}Platform` uses some type classes from Oracle's JDBC driver. you should have it in your dependency.

### Hibernate

* After 5.2, just use `hibernate-core` instead `hibernate-entitymanager`, it is [merged](https://github.com/hibernate/hibernate-orm/wiki/Migration-Guide---5.2).
* Naming strategy property is
  * 4.x: `hibernate.ejb.naming_strategy`
  * 5.x: `hibernate.physical_naming_strategy` / `hibernate.implicit_naming_strategy`

* For select dialect:
  * set `hibernate.dialect` on `properties`
  * set `databaseProductName`, `databaseMajorVersion`, and/or `databaseMinorVersion` for determine dialect.

### Hibernate with Spring ORM

 * `vendor` should be `hibernate+spring`. (without `persistence.xml`)
 * use `io.spring.dependency-management` for version management.
   * You can change hibernate version with `hibernate.version` property.

## SchemaGenerationConfig

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
| `vendor` | `string` | JPA vendor name or class name of vendor's `PersistenceProvider` implemention.<p>vendor name is one of <ul><li>`eclipselink`(or `org.eclipse.persistence.jpa.PersistenceProvider`)</li><li>`hibernate` (or `org.hibernate.jpa.HibernatePersistenceProvider`)</li><li>`hibernate+spring` (or `org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider`)</li></ul></p><p>**REQUIRED for project without `persistence.xml`**</p> |
| `packageToScan` | `java.util.List` | list of package name for scan entity classes<p>**REQUIRED for project without `persistence.xml`**</p> |

### How-to config `properties`

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

* [All dialect list of 5.4.12](https://github.com/hibernate/hibernate-orm/tree/5.4.12/hibernate-core/src/main/java/org/hibernate/dialect)
* [Preregistered dialects of 5.4.12](https://github.com/hibernate/hibernate-orm/blob/5.4.12/hibernate-core/src/test/java/org/hibernate/dialect/resolver/DialectFactoryTest.java#L107)

For other versions, select tag to your version.

## License

Source Copyright © 2013-2018 Sin Young "Divinespear" Kang (divinespear at gmail dot com). Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses).
