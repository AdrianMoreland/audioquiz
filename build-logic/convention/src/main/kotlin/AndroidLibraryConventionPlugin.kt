import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import com.audioquiz.BuildPlugins
import com.audioquiz.configureCommonConfig
import com.audioquiz.configureFlavors
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply(BuildPlugins.ANDROID_LIBRARY)
        project.pluginManager.apply(BuildPlugins.KOTLIN_ANDROID)
        project.pluginManager.apply("audioquiz.dependency.versions")

        project.extensions.configure<LibraryExtension> {
            try {
                configureCommonConfig(project)
                configureFlavors(this as BaseExtension)
            } catch (e: Exception) {
                project.logger.error("Error occurred during configuration: ${e.message}")
            }
        }
    }
}
