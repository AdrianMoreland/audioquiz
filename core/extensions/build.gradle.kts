plugins {
    id("audioquiz.android.library")
}

android {
    namespace = "com.audioquiz.extensions"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    projects.apply {
        testImplementation(core.test)
    }
}
