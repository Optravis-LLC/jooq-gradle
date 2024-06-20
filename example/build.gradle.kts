import com.optravis.jooq.gradle.ContainerConfig
import com.optravis.jooq.gradle.ExperimentalJooqGeneratorConfig
import com.optravis.jooq.gradle.JooqGeneratorConfig

plugins {
    alias(libs.plugins.kotlin.jvm)
    id("com.optravis.jooq")
}

// IMPORTANT: The group determines the package of generated jooq files
group = "com.optravis.jooq.gradle.example"

repositories {
    mavenCentral()
}

// ATTENTION: If you change the config bellow, please update the README.md!
@OptIn(ExperimentalJooqGeneratorConfig::class)
jooqGenerator {
    containerConfig.set(ContainerConfig.postgres(version = "16"))
    generatorConfig.set(
        JooqGeneratorConfig(
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
