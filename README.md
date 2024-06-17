# Jooq generator plugin

An opinionated gradle plugin generate jOOQ classes from Flyway migrations.

The goal is to be very easy to set up jOOQ generation for projects using Kotlin, flyway and postgres.

The minimal setup for projects using Postgres (and the default flyway migration directory) is:

```kotlin
plugins {
    kotlin("jvm") version "<kotlin version>" // The Kotlin plugin is required
    id("com.optravis.platform.jooq") version "<platformlib version>" // Install the gradle plugin
}
```

It is possible to use other databases by configuring the (experimental) 'JooqGeneratorExtension'

```kotlin
@OptIn(ExperimentalJooqGeneratorConfig::class)
configure<JooqGeneratorExtension> {
    //...
}
```
