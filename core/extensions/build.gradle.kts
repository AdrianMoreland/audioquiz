plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
}

android {
    namespace = "com.audioquiz.core.extensions"


}

dependencies {
    libs.apply {
      //  implementation(gson)
        implementation(rxjava)
        implementation(lifecycle.reactivestreams)
        implementation(rxandroid)
        implementation(modelmapper)
    }
}
