plugins {
    id("audioquiz.android.feature")
}

android {
    namespace = "com.audioquiz.marketlist"
}

configurations.all {
    resolutionStrategy {
        force("androidx.test:runner:1.4.0")
    }
}

dependencies {
    implementation(projects.data.marketRepository)
    implementation(libs.kotlinx.collections.immutable)
    implementation(projects.core.uimarket)
    implementation(projects.core.extensions)
    implementation(libs.espresso.core)
}
