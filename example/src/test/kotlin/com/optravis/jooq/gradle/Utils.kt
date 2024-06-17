package com.optravis.jooq.gradle

import io.kotest.core.spec.style.scopes.ContainerScope
import org.flywaydb.core.Flyway
import org.postgresql.ds.PGSimpleDataSource
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource

const val POSTGRES_IMAGE = "postgres:16"

fun ContainerScope.startPostgresDatabase(): DataSource {
    val container = PostgreSQLContainer(POSTGRES_IMAGE).apply {
        start()
    }
    afterContainer {
        container.stop()
    }
    val dataSource = PGSimpleDataSource().apply {
        setURL(container.jdbcUrl.also {
            println("[Test DB] jdbc url: $it")
        })
        user = container.username.also { println("[Test DB] user: $it") }
        password = container.password.also { println("[Test DB] password: $it") }
    }

    Flyway.configure().dataSource(dataSource).load().migrate()
    return dataSource
}
