import com.audioquiz.AndroidConfig

plugins {
    id("audioquiz.android.application")
    id("audioquiz.android.hilt")
    id("audioquiz.dependency.versions")
    id("audioquiz.modules.graph.assert")
}

android {
    defaultConfig {
        namespace = AndroidConfig.NAMESPACE
        applicationId = AndroidConfig.APP_ID
        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

// Define a custom property
    val isProdFlavor by extra {
        findProperty("isProdFlavor")?.toString()?.toBoolean() ?: false
    }

}

allprojects {
    android {
        lint {
            checkReleaseBuilds = false
            abortOnError = false
        }
        hilt {
            enableAggregatingTask = true
        }
    }
}


dependencies {
    projects.library.apply {
        implementation(designsystem)
    }
    projects.core.apply {
        implementation(domain)
        implementation(model)
        implementation(extensions)
        implementation(sync)
        implementation(ui)
    }
    projects.feature.apply {
        implementation(login)
        implementation(home)
        implementation(rank)
        implementation(quiz)
        implementation(settings)
        implementation(stats)
    }
    projects.data.apply {
        implementation(api)
        implementation(repository)
    }

    if (gradle.startParameter.taskNames.none { it.contains("Demo") }) {
        libs.apply {
            implementation(play.services.base)
            implementation(play.services.auth)
            implementation(googleid)
            implementation(credentials)
            implementation(credentials.play.services.auth)
        }
    }

    libs.apply {
        implementation(timber)

        implementation(kotlin.stdlib.jdk8)
        implementation(startup.runtime)
        implementation(androidx.core.splashscreen)
        implementation(dagger)
        ksp(dagger.compiler)
        implementation(work.runtime)
        ksp(work.compiler)
        implementation(hilt.work)
        implementation(work.rxjava3)


        // Navigation
        implementation(navigation.fragment)
        implementation(navigation.ui)
        implementation(hilt.navigation.fragment)
        //RxJava
        implementation(room.rxjava3)
        implementation(rxandroid)

        implementation(gson)
        implementation(annotation)
        implementation(appcompat)
        implementation(fragment)
        implementation(material)
        implementation(viewmodel)
        implementation(livedata)
        implementation(constraintlayout)
        implementation(blurview)
    }
}
