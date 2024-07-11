import com.optravis.jooq.gradle.ContainerConfig
import com.optravis.jooq.gradle.ExperimentalJooqGeneratorConfig
import com.optravis.jooq.gradle.GeneratorType
import com.optravis.jooq.gradle.JooqDatabaseConfig
import com.optravis.jooq.gradle.JooqGeneratorConfig

plugins {
    alias(libs.plugins.kotlin.jvm)
    id("com.optravis.jooq")
}

group = "com.optravis.jooq.gradle.example"

repositories {
    mavenCentral()
}

// ATTENTION: If you change the config bellow, please update the README.md!
@OptIn(ExperimentalJooqGeneratorConfig::class)
jooqGenerator {
    containerConfig.set(ContainerConfig.postgres(version = "16"))
    jooqDbConfig.set(
        JooqDatabaseConfig.postgres(
            schema = "public",
            recordVersionFields = listOf("version"),
        )
    )
    packageName.set("$group.jooq")
    migrationDirectory.set(File("${project.layout.projectDirectory}/src/main/resources/db/migration"))
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
