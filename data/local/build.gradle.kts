plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
    id("androidx.room")
    id("com.google.devtools.ksp")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.audioquiz.data.local"

    room {
        schemaDirectory(file("schemas").absolutePath)
    }

}


dependencies {
    libs.apply {
        implementation(gson)
        implementation(room.runtime)
        ksp(room.compiler)
        implementation(room.rxjava3)
        implementation(rxjava)
        implementation(rxandroid)
        implementation(modelmapper)
        testImplementation(runner)
    }
    projects.core.apply {
        implementation(model)
        implementation(extensions)
        testImplementation(test)
    }
    projects.apply {
        implementation(data.api)
    }
}
