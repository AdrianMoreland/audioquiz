// ProjectExtensions.kt
package com.audioquiz

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.support.KotlinCompilerOptions

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

inline fun Project.androidGradle(crossinline configure: LibraryExtension.() -> Unit) =
    extensions.configure<LibraryExtension> {
        configure()
    }

inline fun Project.detektGradle(crossinline configure: DetektExtension.() -> Unit) =
    extensions.configure<DetektExtension> {
        configure()
    }

inline fun Project.applicationGradle(crossinline configure: ApplicationExtension.() -> Unit) =
    extensions.configure<ApplicationExtension> {
        configure()
    }

inline fun Project.configureJavaCompatibility(crossinline configure: JavaPluginExtension.() -> Unit) =
    extensions.configure<JavaPluginExtension> {
        configure()
    }

inline fun Project.javaGradle(crossinline configure: JavaPluginExtension.() -> Unit) =
    extensions.configure<JavaPluginExtension> {
        configure()
    }

fun CommonExtension<*, *, *, *, *, *>.kotlinOptions(block: KotlinCompilerOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
