import com.android.build.api.variant.BuildConfigField
import com.audioquiz.BuildProductFlavor
import kotlin.text.uppercase
import java.util.Properties
import com.android.build.api.variant.LibraryVariant

plugins {
    id("audioquiz.android.library")
    id("audioquiz.android.hilt")
}

android {
    namespace = "com.audioquiz.data.remote"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "WEB_CLIENT_ID", project.properties["WEB_CLIENT_ID"].toString())
    }


      val localProperties = Properties()
      localProperties.load(project.rootProject.file("local.properties").inputStream())

      androidComponents {
            onVariants(selector().all()) { variant: LibraryVariant ->
                  val flavorName = variant.flavorName
                  val webClientIdKey = "WEB_CLIENT_ID"
                  val webClientId = localProperties.getProperty(webClientIdKey)
                  webClientId?.let {
                        val buildConfigField = BuildConfigField("String", "\"$it\"", "")
                        variant.buildConfigFields.put("WEB_CLIENT_ID", buildConfigField)
                  }
            }
}

dependencies {
    projects.apply {
        implementation(core.extensions)
        implementation(data.api)
        implementation(core.model)
    }

    libs.apply {
        implementation(gson)
        implementation(rxjava)
        implementation(rxandroid)
          implementation(platform(firebase.bom))
          implementation(firebase.auth)
          implementation(firebase.firestore)
          implementation(firebase.storage)
          implementation(firebase.appcheck)
          implementation(firebase.analytics)
          implementation(play.services.auth)
          implementation(credentials)
          implementation(credentials.play.services.auth)
        implementation(googleid)
        implementation(modelmapper)

        androidTestImplementation(runner)
    }
}
}
