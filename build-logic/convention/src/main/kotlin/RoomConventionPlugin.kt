import androidx.room.gradle.RoomExtension
import com.google.devtools.ksp.gradle.KspExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import com.audioquiz.libs

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            // Apply Room and KSP plugins
            pluginManager.apply("androidx.room")
            pluginManager.apply("com.google.devtools.ksp")


            // Configure KSP to work for Room's Java setup
            extensions.configure<KspExtension> {
                arg("room.generateKotlin", "false") // Change to "false" since it's a Java project
            }

            // Configure Room extension
            extensions.configure<RoomExtension> {
                // Set the directory for Room schemas for auto migrations
                schemaDirectory("$projectDir/schemas")
            }

            // Declare dependencies (remove Kotlin-specific dependencies)
            dependencies {
                add("implementation", libs.findLibrary("room.runtime").get()) // Room runtime
                add("ksp", libs.findLibrary("room.compiler").get()) // Room compiler for KSP
            }
        }
    }
}
