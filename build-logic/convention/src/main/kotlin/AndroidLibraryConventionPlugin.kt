import com.audioquiz.androidGradle
import com.audioquiz.configureJavaAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            applyPlugins()
            applyDependencies()
            androidGradle {
                configureJavaAndroid(this)
            }
        }
    }

    private fun Project.applyPlugins() {
        pluginManager.apply {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
            apply("audioquiz.android.hilt")
            apply("audioquiz.android.detekt")
            apply("audioquiz.android.ktlint")
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
            "androidTestImplementation"(kotlin("test"))
            "testImplementation"(kotlin("test"))
        }
    }
}
