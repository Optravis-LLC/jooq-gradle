package com.optravis.jooq.gradle

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import org.jooq.generated.tables.references.BOOK

class ExampleTest : FunSpec({
    isolationMode = IsolationMode.InstancePerLeaf
    context("given a data-source") {
        val dataSource = startPostgresDatabase()
        val dsl = DSL.using(dataSource, SQLDialect.POSTGRES)
        test("query books") {
            dsl.select(BOOK.ID, BOOK.author().FIRST_NAME)
                .from(BOOK)
                .where(BOOK.PUBLISHED_IN.greaterThan(3000))
                .toList()
                .shouldBeEmpty()
        }
    }
})
