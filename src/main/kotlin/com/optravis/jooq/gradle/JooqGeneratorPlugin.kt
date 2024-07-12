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

public interface JooqGeneratorExtension {
    public val packageName: Property<String>

    @ExperimentalJooqGeneratorConfig
    public val containerConfig: Property<ContainerConfig>

    @ExperimentalJooqGeneratorConfig
    public val connectionConfig: Property<DbConnectionConfig>

    @ExperimentalJooqGeneratorConfig
    public val jooqDbConfig: Property<JooqDatabaseConfig>

    @ExperimentalJooqGeneratorConfig
    public val migrationDirectory: Property<File>

    @ExperimentalJooqGeneratorConfig
    public val generatorConfig: Property<JooqGeneratorConfig>
}

@OptIn(ExperimentalJooqGeneratorConfig::class)
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
            packageName.convention(ext.packageName)
            containerConfig.convention(ext.containerConfig)
            connectionConfig.convention(ext.connectionConfig)
            jooqDbConfig.convention(ext.jooqDbConfig)
            migrationDirectory.convention(ext.migrationDirectory)
            generatorConfig.convention(ext.generatorConfig)
        }
        "compileJava" { dependsOn(generateTask) }
        "compileKotlin" { dependsOn(generateTask) }
    }

    private fun Project.configureSourceSets() {
        val sourceSets = properties["sourceSets"] as SourceSetContainer
        sourceSets["main"].java.srcDir(jooqTargetDir)
    }

    private fun Project.createExtension() =
        extensions.create<JooqGeneratorExtension>("jooqGenerator").apply {
            containerConfig.convention(ContainerConfig.postgres())
            connectionConfig.convention(DbConnectionConfig.postgres())
            jooqDbConfig.convention(JooqDatabaseConfig.postgres())
            migrationDirectory.convention(File("${project.layout.projectDirectory}/src/main/resources/db/migration"))
            generatorConfig.convention(JooqGeneratorConfig())
        }
}

@OptIn(ExperimentalJooqGeneratorConfig::class)
private abstract class JooqGenerateTask : DefaultTask() {

    @get:Input
    abstract val packageName: Property<String>

    @get:Input
    abstract val containerConfig: Property<ContainerConfig>

    @get:Input
    abstract val connectionConfig: Property<DbConnectionConfig>

    @get:Input
    abstract val jooqDbConfig: Property<JooqDatabaseConfig>

    @get:Input
    abstract val generatorConfig: Property<JooqGeneratorConfig>

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

    private fun config(): JooqRootConfig = JooqRootConfig(
        container = containerConfig.get(),
        connection = connectionConfig.get(),
        database = jooqDbConfig.get(),
        migrationDirectory = migrationDirectory.get(),
        generator = generatorConfig.get(),
        target = JooqTargetConfig(
            packageName = packageName.orNull ?: error(
                """
                    No package defined for jOOQ generation.
                    Add something like: `jooqGenerator { packageName.set("my.package") }` in your gradle script.
                """.trimIndent()
            ),
            directory = outputDirectory,
        )
    )
}

private val Project.jooqTargetDir get() = File("${layout.buildDirectory.get()}/generated-src/jooq")
