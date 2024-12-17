import com.audioquiz.configureBuildFeatures
import com.audioquiz.configureJava
import com.audioquiz.configureKotlinAndroid
import com.audioquiz.configurePackagingOptions

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("audioquiz.android.hilt")
    id("audioquiz.firebase")
}
android {
    namespace = "com.audioquiz.admin"

    configureCommonConfig(this)
    configureBuildFeatures(this)
    configurePackagingOptions(this)
    configureJava(project)
    configureKotlinAndroid(project)
    defaultConfig {
        applicationId = "com.audioquiz.admin"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


}

dependencies {
    projects.library.apply {
        implementation(designsystem)
    }
    projects.core.apply {
        implementation(model)
        implementation(domain)
        implementation(extensions)
        implementation(sync)
//        implementation(ui)
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
        implementation(remote)
        implementation(local)
    }

    libs.apply {
        implementation(platform(firebase.bom))
        implementation(firebase.analytics)
        implementation(firebase.appcheck)
        implementation(play.services.base)
        implementation(play.services.auth)
        implementation(googleid)
        implementation(credentials)
        implementation(credentials.play.services.auth)
    }

    libs.apply {
        implementation(timber)

        implementation(kotlin.stdlib.jdk8)
        implementation (startup.runtime)
        implementation (androidx.core.splashscreen)
        implementation (work.runtime)
        ksp (work.compiler)


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

