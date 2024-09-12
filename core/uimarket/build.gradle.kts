plugins {
    id("audioquiz.android.library")
  //  id("audioquiz.android.library.compose")
}

android {
    namespace = "com.audioquiz.uimarket"
}

dependencies {
    implementation(projects.domain.market)
    testImplementation(projects.core.test)
    libs.apply {
/*        api(platform(compose.bom))
        api(bundles.compose)*/
    }
}
