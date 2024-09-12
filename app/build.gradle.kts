plugins {
    id("audioquiz.android.application")
//    alias(libs.plugins.audioquiz.android.application)
}

android {
    namespace = libs.versions.projectApplicationId.get()
    defaultConfig {
        applicationId = libs.versions.projectApplicationId.get()
        versionCode = libs.versions.projectVersionCode.get().toInt()
        versionName = libs.versions.projectVersionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    projects.library.apply {
        implementation(navigation)
        implementation(designsystem)
    }
    projects.core.apply {
        implementation(sync)
        implementation(base)
        implementation(uimarket)
    }
    libs.apply {
        implementation(hilt.work)
        implementation(lifecycle.runtime.ktx)
        implementation(libs.espresso.core)
        // Kotlin
        implementation(libs.kotlin.stdlib.jdk8)

        //Splash
        implementation (libs.androidx.core.splashscreen)

        // Hilt
        implementation(libs.hilt.android)
        implementation(libs.hilt.navigation.fragment)
        implementation(libs.annotation)
        implementation(libs.constraintlayout)
        implementation(libs.livedata.ktx)
        implementation(libs.viewmodel)

        implementation (libs.startup.runtime)
        ksp(libs.hilt.compiler)

        //WorkManager
        implementation(libs.work.runtime)
        implementation(libs.work.rxjava2)
        implementation(libs.hilt.work)
        ksp(libs.work.compiler)
        //Room
        implementation(libs.room.runtime)
        ksp(libs.room.compiler)
        //RxJava
        implementation(libs.room.rxjava3)
        implementation(libs.rxandroid)
        // Navigation
        implementation(libs.navigation.fragment)
        implementation(libs.navigation.ui)

        // External libraries
        implementation(libs.play.services.base)
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.auth)
        implementation(libs.firebase.firestore)
        implementation(libs.firebase.storage)
        implementation(libs.firebase.appcheck)
        implementation(libs.firebase.analytics)
        //  implementation(libs.bundles.firebase)
        implementation(libs.play.services.auth)
        implementation(libs.credentials)
        implementation(libs.credentials.play.services.auth)
        implementation(libs.googleid)

        implementation(libs.gson)
        implementation(libs.fragment)
        implementation(libs.viewmodel)
        implementation(libs.livedata)
        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.ucrop)
        implementation(libs.blurview)
    }
}
