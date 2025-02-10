plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
}

android {
    namespace = "com.audioquiz.sync"


    lint {
        checkReleaseBuilds = false
        abortOnError = false
    }
}

dependencies {
    projects.core.apply {
        implementation(model)
        implementation(domain)
        implementation(extensions)
    }

    projects.data.apply {
        implementation(api)
        implementation(remote)
        implementation(local)
    }

    libs.apply {
        implementation("com.google.guava:guava:31.1-android")
        implementation(room.runtime)
        ksp(room.compiler)
        implementation(work.runtime)
        implementation(hilt.work)
        implementation(work.rxjava3)
        ksp(work.compiler)
        implementation(gson)
        implementation(startup.runtime)
        implementation(rxjava)
        implementation(rxandroid)
    }
}
