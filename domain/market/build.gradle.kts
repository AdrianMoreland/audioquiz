plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
    libs.plugins.apply {
        alias(kotlin.parcelize)
    }
}

android {
    namespace = "com.audioquiz.domain"

}

dependencies {
    api(projects.core.test)
    api(projects.core.network.ktor)
    libs.apply {
        implementation(javax.inject)
        implementation(coroutines)
        implementation(lifecycle.viewmodel.ktx)
        api(kotlinx.collections.immutable)
        testImplementation(coroutines.test)
        testImplementation(mokito.kotlin)
    }
}
