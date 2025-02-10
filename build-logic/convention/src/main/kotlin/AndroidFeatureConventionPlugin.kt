import com.android.build.gradle.BaseExtension
import com.audioquiz.BuildPlugins
import com.audioquiz.configureBuildFeatures
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidFeatureConventionPlugin : Plugin<Project> {
      override fun apply(project: Project) {
            with(project) {
                  with(pluginManager) {
                        apply("audioquiz.android.library")
                        apply("audioquiz.android.hilt")
                  }

                  configureBuildFeatures(extensions.getByType<BaseExtension>())

                  val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                  dependencies {
                        add("implementation", project(":library:designsystem"))
                        add("implementation", libs.findLibrary("timber").get())
                        add("implementation", libs.findLibrary("hilt.navigation.fragment").get())
                  }
            }
      }
}
