import com.audioquiz.applicationGradle
import com.audioquiz.configureJavaAndroid
import com.audioquiz.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            applyPlugins()
            applicationGradle {
                configureJavaAndroid(this)
                defaultConfig.targetSdk =
                    Integer.parseInt(libs.findVersion("projectTargetSdkVersion").get().toString())
            }
        }
    }

    private fun Project.applyPlugins() {
        pluginManager.apply {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("audioquiz.android.hilt")
        }
    }
}
