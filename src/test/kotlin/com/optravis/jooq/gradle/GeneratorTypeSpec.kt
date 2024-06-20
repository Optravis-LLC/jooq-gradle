package com.optravis.jooq.gradle

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData

class GeneratorTypeSpec : FunSpec({
    context("each `fullyQualifiedName` class should be in classpath") {
        withData(GeneratorType.entries) { type ->
            shouldNotThrow<ClassNotFoundException> { Class.forName(type.fullyQualifiedName) }
        }
    }
})
