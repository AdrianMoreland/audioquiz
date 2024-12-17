plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
}

android {
    namespace = "com.audioquiz.data.api"


}

dependencies {
    libs.apply {
        implementation(rxjava)
        implementation(rxandroid)
        implementation(platform(firebase.bom))
        implementation(firebase.auth)
        implementation(firebase.firestore)
        implementation(firebase.storage)
        implementation(firebase.appcheck)
        implementation(firebase.analytics)
        implementation(play.services.auth)
        implementation(modelmapper)
    }
    projects.apply {
        implementation(core.model)
        implementation(core.domain)
    }
}
