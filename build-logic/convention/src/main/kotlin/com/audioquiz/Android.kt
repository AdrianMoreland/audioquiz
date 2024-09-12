package com.audioquiz

import com.android.build.api.dsl.AndroidSourceSet
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.gradle.ProguardFiles
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import java.util.*


fun BaseAppModuleExtension.appDefaultConfig() {
    defaultConfig {
        applicationId = Android.APPLICATION_ID
        minSdk = Android.Sdk.MIN
        versionCode = 1
        versionName = "1.0"
     }
}

object Android {

    const val APPLICATION_ID = "com.audioquiz"

    object Sdk {

        const val MIN = 26
        const val TARGET = 35
        const val COMPILE = 35

    }
}
