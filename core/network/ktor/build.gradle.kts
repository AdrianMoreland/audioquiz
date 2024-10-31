plugins {
    alias(libs.plugins.audioquiz.android.library)
    id("audioquiz.android.hilt")
}

android {
    namespace = "com.audioquiz.ktor"



}

dependencies {
    libs.apply {
        implementation(bundles.ktor)
        implementation(libs.gson)
    }
}
