import com.audioquiz.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FirebaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
              with(pluginManager) {
                    apply("com.google.gms.google-services")
              //      apply("com.google.firebase.crashlytics")
              }
              dependencies {
                    add("implementation", platform(libs.findLibrary("firebase-bom").get()))
                    add("implementation", libs.findLibrary("firebase.analytics").get())
                    add("implementation", libs.findLibrary("firebase.auth").get())
                    add("implementation", libs.findLibrary("firebase.storage").get())
                    add("implementation", libs.findLibrary("firebase.firestore").get())
                    add("implementation", libs.findLibrary("play.services.auth").get())
              }

//            configureCrashlytics(target.hasProperty("disableCrashlytics") && target.property("disableCrashlytics") == "true")
        }
    }

    /*    private fun Project.configureCrashlytics(disableCrashlytics: Boolean = false) {
            extensions.findByType(ApplicationExtension::class.java)?.let { extension ->
                extension.buildTypes.configureEach {
                    configure<CrashlyticsExtension> {
                        mappingFileUploadEnabled = !disableCrashlytics
                    }
                }
            }
        }*/

    private fun Project.getPluginId(pluginName: String): String {
        return libs.findPlugin(pluginName).get().get().pluginId
    }
}

