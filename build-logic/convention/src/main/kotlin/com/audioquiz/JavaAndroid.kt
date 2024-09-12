package com.audioquiz;

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import com.audioquiz.libs
import com.audioquiz.Android

internal fun configureJava(
    javaPluginExtension: JavaPluginExtension,
) {
    javaPluginExtension.apply {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}


internal fun Project.configureJava(
    javaPluginExtension: JavaPluginExtension
) {
    javaPluginExtension.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

internal fun Project.configureJavaAndroid(
    javaPluginExtension: CommonExtension<*, *, *, *, *, *>
) {
    javaPluginExtension.apply {
        compileSdk = libs.findVersion("projectTargetSdkVersion").get().requiredVersion.toInt()
            defaultConfig {
            appDefaultConfig()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        buildFeatures {
            viewBinding = true
        }

        sourceSets {
            configure<SourceSetContainer> { // Added back the configure block
                named("main")
                {
                    java.srcDirs = listOf("main")
                    resources.srcDirs = listOf("main")
                }
                named(Sources.TEST)
                {
                    java.srcDirs = listOf(Sources.Test.JAVA, Sources.Integration.JAVA)
                    resources.srcDirs =
                        listOf("resourcesS", "resources")
                }
            }
        }

        // Add other Android-specific configurations here
    }
}

