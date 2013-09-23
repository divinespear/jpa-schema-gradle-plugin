jpa-schema-gradle-plugin
========================

[![Build Status](https://secure.travis-ci.org/divinespear/jpa-schema-gradle-plugin.png)](http://travis-ci.org/divinespear/jpa-schema-gradle-plugin)

Gradle plugin for generate schema or DDL scripts from JPA entities using [JPA 2.1](http://jcp.org/en/jsr/detail?id=338) schema generator.

Currently support [EclipseLink](http://www.eclipse.org/eclipselink) (Reference Implementation) and [Hibernate](http://hibernate.org).


## How-to Use

Put this to your `build.gradle`

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
    	// plugin dependencies
        classpath "org.eclipse.persistence:org.eclipse.persistence.jpa:2.5.0"
        classpath "org.hibernate:hibernate-entitymanager:4.3.0.Beta3"
        // plugin
        classpath "io.github.divinespear:jpa-schema-gradle-plugin:0.1.0"
        // jdbc drivers also here
        ...
    }
}

apply plugin: "java"
apply plugin: "jpa-schema-generate"

sourceSets {
    main {
    	// set output to same directories
        output.classesDir = "${buildDir}/classes"
        output.resourcesDir = "${buildDir}/classes"
    }
}

generateSchema {
	// default options
	// see below to all options
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

### SchemaGenerationConfig

Here is full list of parameters of `generateSchema`.


## License

Source Copyright © 2013 Sin-young "Divinespear" Kang. Distributed under the [Apache License, Version 2.0](http://www.apache.org/licenses).
