package com.audioquiz

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureKotlinAndroid(
    extension: CommonExtension<*, *, *, *, *, *>
) {
    extension.apply {
        compileSdk = project.libs.findVersion("projectCompileSdkVersion").get().requiredVersion.toInt()

        defaultConfig {
            minSdk = 26
         }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        buildFeatures {
            viewBinding = true
        }
    }
}

internal fun Project.configureKotlinAndroidExtension() {
    configure<KotlinAndroidProjectExtension> {
        with(compilerOptions) {
            jvmTarget.set(JvmTarget.JVM_17)
            jvmToolchain(17)
         }
    }
}
private fun Project.configureKotlin(optInCoroutines: Boolean) {
    configure<KotlinAndroidProjectExtension> {
        compilerOptions.apply {
            jvmTarget.set(JvmTarget.JVM_17)
            // Treat all Kotlin warnings as errors (disabled by default)
            allWarningsAsErrors.set(true)
            freeCompilerArgs.addAll(
                listOfNotNull(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-Xcontext-receivers",
                    // Enable experimental coroutines APIs, including Flow
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi".takeIf { optInCoroutines },
                    "-opt-in=kotlinx.coroutines.FlowPreview".takeIf { optInCoroutines },
                )
            )
        }
    }
}
