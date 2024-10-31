
import com.audioquiz.BuildPlugins
import com.audioquiz.configureDependencyAnalysis
import com.autonomousapps.DependencyAnalysisExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class DependencyAnalysisConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.DEPENDENCY_ANALYSIS)
            }
            extensions.configure<DependencyAnalysisExtension> {
                configureDependencyAnalysis(this)
            }
        }
    }
}
