

import com.audioquiz.androidGradle
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import com.audioquiz.libs
import com.audioquiz.configureJava
import com.audioquiz.configureJavaAndroid
import com.audioquiz.configureJavaCompatibility
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import com.audioquiz.javaGradle
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension


class JavaLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.plugins.apply("java-library")
        project.plugins.apply("org.jetbrains.kotlin.jvm")
        project.plugins.apply("com.google.devtools.ksp")

        project.configure<JavaPluginExtension> {
            configureJava(this)
            withSourcesJar()
            withJavadocJar()
        }


        project.dependencies {
            add("implementation", project.libs.findLibrary("dagger").get())
            add("ksp", project.libs.findLibrary("dagger-compiler").get())
            add("implementation", project.libs.findLibrary("rxjava").get())
            add("implementation", project.libs.findLibrary("guava").get())
            add("testImplementation", project.libs.findLibrary("junit").get())
        }

        // Configure test logging, or any other task configuration if needed
        project.tasks.withType<Test> {
            testLogging {
                events("passed", "skipped", "failed")
            }
        }


        // Define common dependencies
        project.dependencies {

        }
    }
}
