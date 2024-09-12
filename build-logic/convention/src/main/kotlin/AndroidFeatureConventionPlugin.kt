import com.audioquiz.androidGradle
import com.audioquiz.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            applyPlugins()
            applyDependencies()
            androidGradle {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
        }
    }

    private fun Project.applyPlugins() {
        pluginManager.apply {
            apply("audioquiz.android.library")
            apply("audioquiz.android.hilt")
        }
    }

    private fun Project.applyDependencies() {
        dependencies {
             "androidTestImplementation"(libs.findLibrary("runner").get())

            "testImplementation"(project(":core:test"))
            "api"(project(":library:designsystem"))
            "api"(project(":core:base"))
        }
    }
}
