import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.detekt)
    alias(libs.plugins.gradle.plugin.publish)
    `java-gradle-plugin`
}

group = "com.optravis.jooq.gradle"


repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleKotlinDsl())
    implementation(libs.jooq.codegen)
    implementation(libs.flyway.core)
    implementation(libs.testcontainers.core)
    implementation(libs.postgresql)

    runtimeOnly(libs.jooq.meta.kotlin)
    runtimeOnly(libs.flyway.postgres)

    testImplementation(rootProject.libs.kotest.runner)
    testImplementation(rootProject.libs.kotest.assertions)
    testImplementation(rootProject.libs.kotest.datatest)
}

gradlePlugin {
    website.set("https://github.com/Optravis-LLC/jooq-gradle")
    vcsUrl.set("https://github.com/Optravis-LLC/jooq-gradle")
    plugins {
        create("jooqGenerator") {
            id = "com.optravis.jooq"
            implementationClass = "com.optravis.jooq.gradle.JooqGeneratorPlugin"
            displayName = "jOOQ Generator Plugin for Kotlin"
            description = "An opinionated gradle plugin to generate jOOQ Kotlin code from Flyway migrations"
            tags.set(listOf("kotlin", "jooq", "flyway", "postgres"))
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_21
}

kotlin {
    explicitApi()
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

detekt {
    config.setFrom(rootDir.resolve("detekt.yml"))
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
