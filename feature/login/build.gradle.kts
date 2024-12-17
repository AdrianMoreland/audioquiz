plugins {
    id("audioquiz.android.feature")
}

android {
    namespace = "com.audioquiz.feature.login"


}

configurations.all {
    resolutionStrategy {
        force("androidx.test:runner:1.4.0")
    }
}

dependencies {
    projects.apply {
        implementation(library.util)
        implementation(data.api)
        implementation(core.model)
        implementation(core.domain)
    }
    libs.apply {
        implementation(rxjava)
        implementation(rxandroid)
        implementation(appcompat)
        implementation(material)
        implementation(navigation.fragment)
        implementation(navigation.ui)
    }
}
