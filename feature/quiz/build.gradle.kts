plugins {
    alias(libs.plugins.audioquiz.android.feature)
}

android {
    namespace = "com.audioquiz.feature.quiz"


}

configurations.all {
    resolutionStrategy {
        force("androidx.test:runner:1.4.0" )
    }
}

dependencies {
    projects.apply{
        implementation(library.util)
        implementation(core.model)
        implementation(core.domain)
    }
    libs.apply{
        implementation(espresso.core)
        implementation(rxjava)
        implementation(rxandroid)

        implementation(appcompat)
        implementation(material)
        implementation(navigation.ui)
        implementation(navigation.fragment)
        implementation(autofittextview)
    }
}
