package com.optravis.jooq.gradle

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.equals.shouldBeEqual

@OptIn(ExperimentalJooqGeneratorConfig::class)
class DbConnectionConfigSpec : FunSpec({
    test("replace port in postgres url template") {
        DbConnectionConfig.postgres("mydb", "myuser", "mypassword").makeUrl(42)
            .shouldBeEqual("jdbc:postgresql://localhost:42/mydb")
    }
})
