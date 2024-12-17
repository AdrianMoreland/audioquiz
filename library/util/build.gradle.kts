plugins {
    id("audioquiz.android.library")
}

android {
    namespace = "com.audioquiz.library.util"
}

dependencies {

    libs.apply {
        implementation(rxjava)
        implementation(lifecycle.reactivestreams)
        implementation(rxandroid)
        implementation(modelmapper)
    }
}
