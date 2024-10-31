import com.audioquiz.configurePackagingOptions

plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
}

android {
    namespace = "com.audioquiz.core_test"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    configurePackagingOptions(this)
}

dependencies {
    libs.apply {
        api(mockk)
    }
}
