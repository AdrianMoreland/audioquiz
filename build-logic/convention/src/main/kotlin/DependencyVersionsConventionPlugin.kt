import com.audioquiz.BuildPlugins
import com.audioquiz.configureDependencyVersions
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

class DependencyVersionsConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.DEPENDENCY_VERSIONS)
            }
            tasks.withType<DependencyUpdatesTask> {
                configureDependencyVersions(this)
            }
        }
    }
}
