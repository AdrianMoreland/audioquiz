plugins {
    id("audioquiz.android.library")
 //   id("audioquiz.android.library.compose")
}

android {
    namespace = "com.audioquiz.navigation"
}

dependencies {
    projects.apply {
        implementation(feature.marketlist)
        implementation(feature.marketdetail)
        implementation(core.uimarket)
        implementation(domain.market)
    }
 //   api(libs.navigation.compose)
}
