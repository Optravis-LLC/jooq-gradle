@file:OptIn(ExperimentalJooqGeneratorConfig::class)

package com.optravis.jooq.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.TaskContainerScope
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.register
import java.io.File

@ExperimentalJooqGeneratorConfig
public interface JooqGeneratorExtension {
    public val containerConfig: Property<ContainerConfig>
    public val connectionConfig: Property<DbConnectionConfig>
    public val jooqDbConfig: Property<JooqDatabaseConfig>
    public val migrationDirectory: Property<File>
    public val packageName: Property<String>
    public val deprecateUnknownTypes: Property<Boolean>
    public val javaTimeTypes: Property<Boolean>
}

private object Default {
    const val DB = "jooq"
    const val USER = "jooq"
    const val PASSWORD = "jooq"
}

public class JooqGeneratorPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val extension = createExtension()
        configureSourceSets()
        project.tasks {
            configureTasks(extension)
        }
    }

    private fun TaskContainerScope.configureTasks(ext: JooqGeneratorExtension) {
        val generateTask = register<JooqGenerateTask>("generateJooq") {
            group = "jOOQ"
            description = "Generate jOOQ classes from database migrations"
            outputDirectory = project.jooqTargetDir
            containerConfig.convention(ext.containerConfig)
            connectionConfig.convention(ext.connectionConfig)
            jooqDbConfig.convention(ext.jooqDbConfig)
            migrationDirectory.convention(ext.migrationDirectory)
            deprecateUnknownTypes.convention(ext.deprecateUnknownTypes)
            javaTimeTypes.convention(ext.javaTimeTypes)
            packageName.convention(ext.packageName.orElse("${project.group}.jooq"))
        }
        "compileJava" { dependsOn(generateTask) }
        "compileKotlin" { dependsOn(generateTask) }
    }

    private fun Project.configureSourceSets() {
        val sourceSets = properties["sourceSets"] as SourceSetContainer
        sourceSets["main"].java.srcDir(jooqTargetDir)
    }

    private fun Project.createExtension() =
        extensions.create<JooqGeneratorExtension>("jooqGeneratorExtension").apply {
            containerConfig.convention(ContainerConfig.postgres(Default.DB, Default.USER, Default.PASSWORD))
            connectionConfig.convention(DbConnectionConfig.postgres(Default.DB, Default.USER, Default.PASSWORD))
            jooqDbConfig.convention(JooqDatabaseConfig.postgres)
            migrationDirectory.convention(File("${project.layout.projectDirectory}/src/main/resources/db/migration"))
            deprecateUnknownTypes.convention(true)
            javaTimeTypes.convention(true)
        }
}

private abstract class JooqGenerateTask : DefaultTask() {

    @get:Input
    abstract val containerConfig: Property<ContainerConfig>

    @get:Input
    abstract val connectionConfig: Property<DbConnectionConfig>

    @get:Input
    abstract val jooqDbConfig: Property<JooqDatabaseConfig>

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val deprecateUnknownTypes: Property<Boolean>

    @get:Input
    abstract val javaTimeTypes: Property<Boolean>

    @get:InputDirectory
    abstract val migrationDirectory: Property<File>

    @get:OutputDirectory
    lateinit var outputDirectory: File

    @TaskAction
    fun generate() {
        config()
            .also { logger.info("jOOQ generator config: {}", it) }
            .generate()
    }

    private fun config(): JooqGeneratorConfig = JooqGeneratorConfig(
        container = containerConfig.get(),
        connection = connectionConfig.get(),
        database = jooqDbConfig.get(),
        migrationDirectory = migrationDirectory.get(),
        deprecateUnknownTypes = deprecateUnknownTypes.get(),
        javaTimeTypes = javaTimeTypes.get(),
        target = JooqTargetConfig(
            packageName = packageName.get(),
            directory = outputDirectory,
        )
    )
}

private val Project.jooqTargetDir get() = File("${layout.buildDirectory.get()}/generated-src/jooq")
