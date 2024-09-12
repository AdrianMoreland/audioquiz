plugins {
    id("audioquiz.java.library")
}

android {
    namespace = "com.audioquiz.domain"
}

dependencies {
    api(projects.core.test)
    api(projects.core.network.ktor)
    libs.apply {
        implementation(javax.inject)
        implementation(lifecycle.viewmodel.ktx)
        api(kotlinx.collections.immutable)
        testImplementation(coroutines.test)
        testImplementation(mokito.kotlin)
    }
}
