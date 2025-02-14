plugins {
    id("audioquiz.android.feature")
}

android {
    namespace = "com.audioquiz.feature.rank"


}

configurations.all {
    resolutionStrategy {
        force("androidx.test:runner:1.4.0")
    }
}

dependencies {
    projects.apply {
        implementation(core.model)
        implementation(core.domain)
    }
    libs.apply {
        implementation(appcompat)
        implementation(material)
        implementation(navigation.ui)
        implementation(navigation.fragment)
        implementation(glide)
        ksp(glide.compiler)
    }
}
