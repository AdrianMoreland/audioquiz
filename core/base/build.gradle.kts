plugins {
    id("audioquiz.android.library")
    //id("audioquiz.android.library.compose")
}

android {
    namespace = "com.audioquiz.base"
}

dependencies {
    projects.library.apply {
        api(projects.core.test)
        api(projects.core.network.ktor)
        implementation(projects.library.designsystem)
    }
    libs.apply {
        implementation(lifecycle.viewmodel.ktx)
    }
}
