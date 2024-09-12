plugins {
    id("audioquiz.android.feature")
}

android {
    namespace = "com.audioquiz.newsdetail"
}

configurations.all {
    resolutionStrategy {
        force("androidx.test:runner:1.4.0" )
    }
}

dependencies {
    implementation(projects.data.marketRepository)
    implementation(projects.core.uimarket)
    implementation(libs.espresso.core)
}
