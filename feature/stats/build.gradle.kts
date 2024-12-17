plugins {
    id("audioquiz.android.feature")
}

android {
    namespace = "com.audioquiz.feature.stats"


}

configurations.all {
    resolutionStrategy {
        force("androidx.test:runner:1.4.0" )
    }
}

dependencies {
    projects.apply{
        implementation(core.model)
        implementation(core.domain)
    }

    libs.apply{
        implementation(rxjava)
        implementation(rxandroid)
        implementation(appcompat)
        implementation(material)
        implementation(navigation.ui)
        implementation(navigation.fragment)
        implementation(mpandroidchart)
        implementation(espresso.core)
    }

}
