import com.audioquiz.BuildPlugins
import com.jraska.module.graph.assertion.GraphRulesExtension
import com.audioquiz.configureModulesGraphAssert
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class ModulesGraphAssertConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(BuildPlugins.MODULES_GRAPH_ASSERT)
            }
            extensions.configure<GraphRulesExtension> {
                configureModulesGraphAssert(this)
            }
        }
    }
}
