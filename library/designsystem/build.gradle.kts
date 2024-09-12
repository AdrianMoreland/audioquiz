plugins {
    id("audioquiz.android.library")
    //id("audioquiz.android.library.compose")
}

android {
    namespace = "com.audioquiz.designsystem"
}

dependencies {
    libs.apply {
       // api(platform(compose.bom))
     //   api(bundles.compose)
   //     api(compose.coil)
       // api(lifecycle.runtime.compose)
       // api(compose.lottie.animation)
    }
}
