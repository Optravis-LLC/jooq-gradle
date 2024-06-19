package com.optravis.jooq.gradle

import java.io.File
import java.io.Serializable


@RequiresOptIn(
    message = "This configuration API is not yet stabilized and is subject to breaking changes",
    level = RequiresOptIn.Level.WARNING
)
public annotation class ExperimentalJooqGeneratorConfig

private object Default {
    const val DB = "jooq"
    const val USER = "jooq"
    const val PASSWORD = "jooq"
}

@OptIn(ExperimentalJooqGeneratorConfig::class)
internal data class JooqRootConfig(
    val container: ContainerConfig,
    val connection: DbConnectionConfig,
    val database: JooqDatabaseConfig,
    val generator: JooqGeneratorConfig,
    val migrationDirectory: File,
    val target: JooqTargetConfig,
)

@ExperimentalJooqGeneratorConfig
public data class JooqGeneratorConfig(
    internal val deprecateUnknownTypes: Boolean = true,
    internal val javaTimeTypes: Boolean = true,
    internal val kotlinPojos: Boolean = true,
) : Serializable

@ExperimentalJooqGeneratorConfig
public data class ContainerConfig(
    internal val image: String,
    internal val port: Int,
    internal val environment: Map<String, String>
) : Serializable {
    public companion object {
        public fun postgres(
            db: String = Default.DB,
            user: String = Default.USER,
            password: String = Default.PASSWORD,
            version: String = "16",
            port: Int = 5432,
        ): ContainerConfig = ContainerConfig(
            image = "postgres:$version",
            port = port,
            environment = mapOf(
                "POSTGRES_DB" to db,
                "POSTGRES_USER" to user,
                "POSTGRES_PASSWORD" to password,
            ),
        )
    }
}

@ExperimentalJooqGeneratorConfig
public data class JooqDatabaseConfig(
    /** Name of the jOOQ database connector (e.g. 'org.jooq.meta.postgres.PostgresDatabase') */
    internal val name: String,
    internal val inputSchema: String,
    internal val recordVersionFields: List<String> = emptyList(),
) : Serializable {
    public companion object {
        public fun postgres(
            schema: String = "public",
            recordVersionFields: List<String> = emptyList(),
        ): JooqDatabaseConfig = JooqDatabaseConfig(
            name = "org.jooq.meta.postgres.PostgresDatabase",
            inputSchema = schema,
            recordVersionFields = recordVersionFields,
        )
    }
}

internal data class JooqTargetConfig(
    val packageName: String,
    val directory: File,
) : Serializable

@ExperimentalJooqGeneratorConfig
public data class DbConnectionConfig(
    internal val user: String,
    internal val password: String,
    /** JDBC url template. `{{port}}` will be substituted with the container port */
    internal val urlTemplate: String,
) : Serializable {
    public companion object {
        public fun postgres(
            db: String = Default.DB,
            user: String = Default.USER,
            password: String = Default.PASSWORD
        ): DbConnectionConfig = DbConnectionConfig(
            user = user,
            password = password,
            urlTemplate = "jdbc:postgresql://localhost:{{port}}/$db"
        )
    }

    internal fun makeUrl(port: Int) = urlTemplate.replace(Regex("\\{\\{\\s*port\\s*}}"), port.toString())
}
