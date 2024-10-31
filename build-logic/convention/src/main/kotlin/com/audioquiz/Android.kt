package com.audioquiz

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project


internal val Project.androidExtension: BaseExtension
    get() {
        return extensions.findByName("android") as BaseExtension?
            ?: error("no 'android' extension found")
    }


fun Project.androidSetup() {
    configureKotlinAndroid(this)
    androidExtension.apply {
        defaultConfig {
            minSdk = AndroidConfig.MIN_SDK
            targetSdk = AndroidConfig.TARGET_SDK
            versionCode = AndroidConfig.VERSION_CODE
            versionName = AndroidConfig.VERSION_NAME
        }

        buildFeatures.apply {
            viewBinding = true
            dataBinding.enable = true
        }

    }
}
