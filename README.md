# jOOQ Generator Plugin for Kotlin

An opinionated gradle plugin to generate jOOQ Kotlin code from Flyway migrations.

The goal is to be as easy as possible to set up jOOQ generation for projects that are using Kotlin, Flyway, and
Postgres.

The minimal setup for those project is:

```kotlin
plugins {
    kotlin("jvm") version "<kotlin version>" // The Kotlin plugin is required
    id("com.optravis.jooq") version "<version>" // Install jOOQ generation plugin
}
```

## What does the plugin do?

* Add `generateJooq` task:
    * start a PostgreSQL docker container
    * apply flyway migrations from `src/main/resources/db/migration`
    * run jOOQ code generator with a configuration tailored for Kotlin Projects
* Make the `compileKotlin` task depend on `generateJooq`
* Add the generated jooq code to the main source set

> [!NOTE]
>
> This plugin requires a `docker` installation

## Configuration

In general, this gradle plugin aims to require as little configuration as possible and all configuration is optional.

However, here's how it can be configured (values):

*build.gradle.kts*

```kotlin
import com.optravis.jooq.gradle.ContainerConfig
import com.optravis.jooq.gradle.ExperimentalJooqGeneratorConfig
import com.optravis.jooq.gradle.GeneratorType
import com.optravis.jooq.gradle.JooqDatabaseConfig
import com.optravis.jooq.gradle.JooqGeneratorConfig


// By default, the `group` determines the package name of generated code 
group = "com.optravis.jooq.gradle.example"

@OptIn(ExperimentalJooqGeneratorConfig::class)
jooqGenerator {

    // Configure postgres container version (default to 16)
    containerConfig.set(ContainerConfig.postgres(version = "16"))

    // Configure jooq database
    jooqDbConfig.set(
        JooqDatabaseConfig.postgres(
            schema = "public",
            recordVersionFields = emptyList(),
        )
    )

    // Override the package name
    packageName.set("$group.jooq")

    // Configure Flyway migration directory
    migrationDirectory.set(File("${project.layout.projectDirectory}/src/main/resources/db/migration"))

    // Configure jOOQ generator options
    generatorConfig.set(
        JooqGeneratorConfig(
            generatorType = GeneratorType.Kotlin,
            deprecateUnknownTypes = true,
            daos = true,
            pojos = true,
            javaTimeTypes = true,
        )
    )
}
```

> [!WARNING]
>
> The configuration API is experimental (hence the requirement to `@OptIn`)
>
> Breaking changes to that API may be introduced in minor releases of the plugin!

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
