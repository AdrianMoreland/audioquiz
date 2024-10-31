import com.audioquiz.configureJava
import com.audioquiz.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType

class JavaLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply("java-library")
        project.plugins.apply("org.jetbrains.kotlin.jvm")
        project.plugins.apply("com.google.devtools.ksp")
        project.plugins.apply("audioquiz.dependency.versions")

        project.configure<JavaPluginExtension> {
            configureJava(project)
            withSourcesJar()
            withJavadocJar()
        }

        project.dependencies {
            add("implementation", project.libs.findLibrary("dagger").get())
            add("ksp", project.libs.findLibrary("dagger-compiler").get())
            add("implementation", project.libs.findLibrary("rxjava").get()) 
        }

        // Configure test logging, or any other task configuration if needed
        project.tasks.withType<Test> {
            testLogging {
                events("passed", "skipped", "failed")
            }
        }
    }
}
