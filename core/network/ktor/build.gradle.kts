plugins {
    alias(libs.plugins.audioquiz.android.library)
    libs.plugins.apply {
        alias(kotlinx.serialization)
    }
}

android {
    namespace = "com.audioquiz.ktor"

}

dependencies {
    libs.apply {
        implementation(bundles.ktor)
    }
}
