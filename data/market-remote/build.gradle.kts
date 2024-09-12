plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
    libs.plugins.apply {
        alias(kotlinx.serialization)
    }
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.audioquiz.remotedatasource"
}

dependencies {
    api(projects.core.network.ktor)
    libs.apply {
        implementation(bundles.ktor)

        debugImplementation(chucker)
        releaseImplementation(chucker.no.op)

        androidTestImplementation(runner)
    }
}
