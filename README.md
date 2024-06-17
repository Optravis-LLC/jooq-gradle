# Jooq generator plugin

An opinionated gradle plugin to generate jOOQ classes from Flyway migrations.

The goal is to be as easy as possible to set up jOOQ generation for projects that are using Kotlin, Flyway, and Postgres.

The minimal setup for those project is:

```kotlin
plugins {
    kotlin("jvm") version "<kotlin version>" // The Kotlin plugin is required
    id("com.optravis.platform.jooq") version "<version>" // Install jOOQ generation plugin
}
```

## What does the plugin do?

* Add `generateJooq` task:
  * starts a PostgreSQL docker container
  * applies flyway migrations from `src/main/resources/db/migration`
  * run jOOQ code generator with a configuration tailored for Kotlin Projects
* Make the `compileKotlin` task depend on `generateJooq`
* Add the generated jooq code to the main source set

> [!NOTE]
>
> This plugin requires a `docker` installation


## MIT License

Copyright (c) 2024 Optravis LLC

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
