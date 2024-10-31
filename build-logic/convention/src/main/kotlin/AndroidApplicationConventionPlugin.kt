import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import com.audioquiz.AndroidConfig
import com.audioquiz.BuildPlugins
import com.audioquiz.configureBuildFeatures
import com.audioquiz.configureCommonConfig
import com.audioquiz.configureFlavors
import com.audioquiz.configurePackagingOptions
import com.audioquiz.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.apply(BuildPlugins.ANDROID_APPLICATION)
        project.pluginManager.apply(BuildPlugins.KOTLIN_ANDROID)
        project.pluginManager.apply(BuildPlugins.GMS)

        configureCommonConfig(project)
        project.extensions.configure<ApplicationExtension> {
            defaultConfig.targetSdk = AndroidConfig.TARGET_SDK
            configureBuildFeatures(this as BaseExtension)
            configureBuildTypes(this)
            configureFlavors(this as BaseExtension)
            configurePackagingOptions(this as BaseExtension)
        }

    }

    private fun configureBuildTypes(applicationExtension: ApplicationExtension){
        applicationExtension.apply {
            buildTypes {
                BuildTypeDebug.create(this, signingConfigs)
                BuildTypeRelease.create(this, signingConfigs)
                BuildTypeBenchmark.create(this, signingConfigs)
            }
        }
    }

    private fun configureSigningConfigs(applicationExtension: ApplicationExtension, project: Project) {
        applicationExtension.apply {
            signingConfigs {
                if (!signingConfigs.names.contains("debug")) {
                    create("debug") {
                        storeFile = project.file("debug_key")
                        storePassword = "android"
                        keyAlias = "androiddebugkey"
                        keyPassword = "android"
                    }
                    /* create("release") {
                         storeFile = project.file("release_key")
                         storePassword = "releaseKey"
                         keyAlias = "com.example.flavor_variant"
                         keyPassword = "releaseKey"
                     }*/
                }
            }
        }
    }
}
