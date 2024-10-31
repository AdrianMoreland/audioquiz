import com.audioquiz.configureBuildFeatures

plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.audioquiz.designsystem"

    configureBuildFeatures(this)
}

dependencies {
    libs.apply {
        implementation(modelmapper)
        implementation(rxjava)
        implementation(navigation.ui)
        implementation(navigation.fragment)
        implementation(appcompat)
        implementation(material)
        implementation(glide)
        ksp(glide.compiler)
        implementation(ucrop)
        implementation(blurview)
    } 
}
