import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import com.audioquiz.AndroidConfig
import com.audioquiz.BuildPlugins
import com.audioquiz.configureBuildFeatures
import com.audioquiz.configureCommonConfig
import com.audioquiz.configurePackagingOptions
import com.audioquiz.configureSigningConfigs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
      override fun apply(project: Project) {
            project.pluginManager.apply(BuildPlugins.ANDROID_APPLICATION)
            project.pluginManager.apply(BuildPlugins.KOTLIN_ANDROID)
        //    project.pluginManager.apply(BuildPlugins.GMS)

            configureCommonConfig(project)
            project.extensions.configure<ApplicationExtension> {
                  defaultConfig.targetSdk = AndroidConfig.TARGET_SDK
                  configureBuildFeatures(this as BaseExtension)
                  configureSigningConfigs(this, project)
                  configureBuildTypes(this)
                //  configureFlavors(project)
                  configurePackagingOptions(this as BaseExtension)
            }
      }

      private fun configureBuildTypes(applicationExtension: ApplicationExtension) {
            applicationExtension.apply {
                  buildTypes {
                        BuildTypeDebug.create(this, signingConfigs)
                        BuildTypeRelease.create(this, signingConfigs)
                        //BuildTypeBenchmark.create(this, signingConfigs)
                  }
            }
      }

}
