@file:OptIn(ExperimentalJooqGeneratorConfig::class)

package com.optravis.jooq.gradle

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Target
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName


internal fun JooqRootConfig.generate() {
    container.run { port ->
        val jdbcUrl = connection.makeUrl(port)
        var attempts = 0
        while (attempts < 10 && !connection.isReady(jdbcUrl)) {
            ++attempts
            Thread.sleep(500)
        }
        Flyway.configure()
            .dataSource(jdbcUrl, connection.user, connection.password)
            .locations("filesystem:${migrationDirectory.absolutePath}")
            .load()
            .migrate()
        target.directory.deleteRecursively()
        GenerationTool.generate(toConfiguration(jdbcUrl))
    }
}

private fun DbConnectionConfig.isReady(jdbcUrl: String): Boolean {
    val config = HikariConfig()
    config.jdbcUrl = jdbcUrl
    config.username = user
    config.password = password
    return runCatching { HikariDataSource(config).connection.close() }.isSuccess
}

private fun JooqRootConfig.toConfiguration(jdbcUrl: String) =
    Configuration()
        .withJdbc(
            Jdbc()
                .withUrl(jdbcUrl)
                .withUsername(connection.user)
                .withPassword(connection.password)
        )
        .withGenerator(
            Generator()
                .withName(generator.generatorType.fullyQualifiedName)
                .withDatabase(
                    Database()
                        .withName(database.name)
                        .withInputSchema(database.inputSchema)
                        .withRecordVersionFields(database.recordVersionFields.joinToString("|"))
                        .withExcludes("flyway_schema_history")
                )
                .withGenerate(generateSpecifically())
                .withTarget(
                    Target()
                        .withPackageName(target.packageName)
                        .withDirectory(target.directory.absolutePath)
                )
        )

private fun JooqRootConfig.generateSpecifically(): Generate {
    val common = Generate()
        .withDaos(generator.daos)
        .withPojos(generator.pojos)
        .withJavaTimeTypes(generator.javaTimeTypes)
        .withDeprecationOnUnknownTypes(generator.deprecateUnknownTypes)
    return when (generator.generatorType) {
        GeneratorType.Kotlin -> common
            .withPojosAsKotlinDataClasses(true)
            .withKotlinDefaultedNullablePojoAttributes(true)
            .withKotlinDefaultedNullableRecordAttributes(true)
            .withKotlinNotNullPojoAttributes(true)
            .withKotlinNotNullInterfaceAttributes(true)
            .withKotlinNotNullRecordAttributes(true)

        else -> common
    }
}

private inline fun <T> ContainerConfig.run(action: (exposedPort: Int) -> T): T {
    val container = GenericContainer(DockerImageName.parse(image))
        .withExposedPorts(port)
        .withEnv(environment)
        .waitingFor(Wait.forListeningPorts(port))
    container.start()
    return try {
        action(container.getMappedPort(port))
    } finally {
        container.stop()
    }
}
