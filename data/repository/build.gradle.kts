plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.audioquiz.data.repository"

}

dependencies {
    projects.apply {
        implementation(core.extensions)
        implementation(library.util)
        implementation(core.domain)
        implementation(core.model)
        implementation(data.api)
    }
    libs.apply {
        implementation(play.services.auth)
        implementation(rxjava)
        implementation(rxandroid)
    }
}
