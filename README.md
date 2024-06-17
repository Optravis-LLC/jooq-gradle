# Jooq generator plugin

An opinionated gradle plugin generate jOOQ classes from Flyway migrations.

The goal is to be as easy as possible to set up jOOQ generation for projects using Kotlin, flyway and postgres.

The minimal setup for projects using Postgres (and the default flyway migration directory) is:

```kotlin
plugins {
    kotlin("jvm") version "<kotlin version>" // The Kotlin plugin is required
    id("com.optravis.platform.jooq") version "<platformlib version>" // Install jOOQ generation plugin
}
```
