plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
    alias(libs.plugins.room.gradlePlugin))
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.audioquiz.localdatasource"
}

sqldelight {
    databases {
        create("MarketDatabase") {
            packageName.set("com.audioquiz.db")
        }
    }
}

dependencies {
    libs.apply {
        implementation(sqldelight.android)
        implementation(sqldelight.coroutines)
        testImplementation(sqldelight.test)
        testImplementation(runner)
    }
    projects.apply {
        testImplementation(core.test)
    }
}
