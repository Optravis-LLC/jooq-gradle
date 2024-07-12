import com.optravis.jooq.gradle.ContainerConfig
import com.optravis.jooq.gradle.ExperimentalJooqGeneratorConfig
import com.optravis.jooq.gradle.JooqGeneratorConfig

plugins {
    alias(libs.plugins.kotlin.jvm)
    id("com.optravis.jooq")
}

repositories {
    mavenCentral()
}

// ATTENTION: If you change the config bellow, please update the README.md!
@OptIn(ExperimentalJooqGeneratorConfig::class)
jooqGenerator {
    packageName.set("com.optravis.jooq.gradle.example.jooq")
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
