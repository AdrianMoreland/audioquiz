plugins {
    id("audioquiz.android.library")
}

android {
    namespace = "com.audioquiz.core.ui"
}

dependencies {
    testImplementation(projects.core.test)
    libs.apply {

    }
}
