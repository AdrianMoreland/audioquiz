plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.audioquiz.data"
}

dependencies {
    projects.apply {
        api(domain.market)
        implementation(data.marketRemote)
        implementation(data.marketLocal)
    }
    libs.apply {
        testImplementation(bundles.kotest)
        testImplementation(sqldelight.test)
    }
}
