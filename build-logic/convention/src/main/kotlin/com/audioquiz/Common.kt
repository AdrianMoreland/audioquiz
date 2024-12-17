package com.audioquiz

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaLanguageVersion
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension


fun configureCommonConfig(project: Project) {
    project.extensions.findByType<BaseExtension>()?.apply {
        compileSdkVersion(AndroidConfig.COMPILE_SDK)
        defaultConfig.minSdk = AndroidConfig.MIN_SDK
    }

    configureJava(project)
    configureKotlinAndroid(project)
}


fun configureJava(project: Project) {
    project.extensions.findByType<JavaPluginExtension>()?.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

fun configureKotlinAndroid(project: Project) {
    project.configure<KotlinAndroidProjectExtension> {
        with(compilerOptions) {
            jvmTarget.set(JvmTarget.JVM_17)
            jvmToolchain(17)
        }
    }
}

fun configureBuildFeatures(extension: BaseExtension) {
    extension.buildFeatures.apply {
        viewBinding = true
        extension.dataBinding.enable = true
    }
}

fun configurePackagingOptions(extension: BaseExtension) {
    extension.packagingOptions {
        resources {
            excludes += setOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
        }
    }
}


fun Project.catalogDependencyPlugin(name: String): String? = extensions.findByType(VersionCatalogsExtension::class.java)
    ?.named("libs")
    ?.findPlugin(name)
    ?.get()?.get()?.pluginId

fun Project.catalogDependency(name: String): String? = extensions.findByType(VersionCatalogsExtension::class.java)
    ?.named("libs")
    ?.findLibrary(name)
    ?.get()?.get()?.let { "${it.module}:${it.versionConstraint.displayName}" }

