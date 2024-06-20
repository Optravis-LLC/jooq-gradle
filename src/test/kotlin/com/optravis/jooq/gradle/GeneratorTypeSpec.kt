package com.optravis.jooq.gradle

import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.equals.shouldNotBeEqual

@OptIn(ExperimentalJooqGeneratorConfig::class)
class GeneratorTypeSpec : FunSpec({
    context("each `fullyQualifiedName` class should be in classpath") {
        withData(GeneratorType.Java, GeneratorType.Kotlin) { type ->
            shouldNotThrow<ClassNotFoundException> { Class.forName(type.fullyQualifiedName) }
        }
    }
    test("should equal self") {
        GeneratorType.Kotlin shouldBeEqual GeneratorType.Kotlin
        GeneratorType.Kotlin.fullyQualifiedName shouldBeEqual GeneratorType.Kotlin.fullyQualifiedName
        GeneratorType.Kotlin.hashCode() shouldBeEqual GeneratorType.Kotlin.hashCode()
    }
    test("should not equal other") {
        GeneratorType.Kotlin shouldNotBeEqual GeneratorType.Java
        GeneratorType.Kotlin.fullyQualifiedName shouldNotBeEqual GeneratorType.Java.fullyQualifiedName
    }
})
