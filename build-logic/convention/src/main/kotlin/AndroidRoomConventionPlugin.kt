import androidx.room.gradle.RoomExtension
import com.audioquiz.BuildPlugins
import com.audioquiz.libs
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.process.CommandLineArgumentProvider
import java.io.File


class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            with(pluginManager) {
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "true")
                arg(RoomSchemaArgProvider(File(projectDir, "schemas")))
            }

            extensions.configure<RoomExtension> { 
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                add("implementation", libs.findLibrary("room.runtime").get())
                add("implementation", libs.findLibrary("room.ktx").get())
                add("ksp", libs.findLibrary("room.compiler").get())
            }
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
            add("implementation", libs.findLibrary("room.runtime").get())
            add("implementation", libs.findLibrary("room.ktx").get())
            add("ksp", libs.findLibrary("room.compiler").get())

            "testImplementation"(project(":core:test"))
            "api"(project(":library:designsystem"))
            "api"(project(":core:base"))
        }
    }

    class RoomSchemaArgProvider(
        @get:InputDirectory
        @get:PathSensitive(PathSensitivity.RELATIVE)
        val schemaDir: File,
    ) : CommandLineArgumentProvider {
        override fun asArguments() = listOf("room.schemaLocation=${schemaDir.path}")
    }
}
