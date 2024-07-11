import com.optravis.jooq.gradle.ContainerConfig
import com.optravis.jooq.gradle.ExperimentalJooqGeneratorConfig
import com.optravis.jooq.gradle.GeneratorType
import com.optravis.jooq.gradle.JooqDatabaseConfig
import com.optravis.jooq.gradle.JooqGeneratorConfig

plugins {
    alias(libs.plugins.kotlin.jvm)
    id("com.optravis.jooq")
}

repositories {
    mavenCentral()
}

// ATTENTION: If you change the config bellow, please update the README.md!

// If defined, the `group` determines the package name of the generated code 
group = "org.jooq.generated"

@OptIn(ExperimentalJooqGeneratorConfig::class)
jooqGenerator {

    // Configure Postgres container version
    containerConfig.set(ContainerConfig.postgres(version = "16"))

    // Configure jOOQ database
    jooqDbConfig.set(
        JooqDatabaseConfig.postgres(
            schema = "public",
            recordVersionFields = emptyList(),
        )
    )

    // Override the package name
    packageName.set("$group.jooq") // If the group is not set, the default is `org.jooq.generated`

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

dependencies {
    implementation(libs.flyway.core)
    runtimeOnly(libs.flyway.postgres)
    implementation(libs.jooq.core)
    implementation(libs.jooq.kotlin)
    implementation(libs.jooq.coroutines)
    testImplementation(libs.kotest.runner)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.postgresql)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
