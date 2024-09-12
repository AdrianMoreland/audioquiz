plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
}

android {
    namespace = "com.audioquiz.sync"
}

dependencies {
    api(projects.domain.market)
    libs.apply {
        implementation(hilt.work)
        implementation(startup.runtime)
        implementation(work.runtime)
    }
}
