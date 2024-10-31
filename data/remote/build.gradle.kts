plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
    id("audioquiz.firebase")
}

android {
    namespace = "com.audioquiz.data.remote"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    projects.apply {
        implementation(core.extensions)
        implementation(data.api)
        implementation(core.model)
    }

    libs.apply {
        implementation(gson)
        implementation(rxjava)
        implementation(rxandroid)
        implementation(credentials)
        implementation(credentials.play.services.auth)
        implementation(play.services.auth)
        implementation(googleid)
        implementation(modelmapper)

        androidTestImplementation(runner)
    }
}
