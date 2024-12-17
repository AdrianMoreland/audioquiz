package com.audioquiz

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import java.util.Properties

enum class FlavorDimension {
    VERSION
}

enum class BuildProductFlavor(
    val flavourName: String,
    val dimension: FlavorDimension,
    val applicationIdSuffix: String? = null,
    val versionNameSuffix: String? = null,
    val webClientId: String? = null,
) {

    PROD("prod", FlavorDimension.VERSION, null, null, "WEB_CLIENT_ID_PROD"),
    DEV("dev", FlavorDimension.VERSION, ".dev", "-dev", "WEB_CLIENT_ID_DEV"),
    //TEST("qa", FlavorDimension.VERSION, ".qa", "-qa", "WEB_CLIENT_ID_DEV"),

}

fun configureFlavors(project: Project) {
    val commonExtension = project.extensions.getByType(BaseExtension::class.java)

    commonExtension.apply {
        val flavorDimension = FlavorDimension.VERSION.name
        when (this) {
            is ApplicationExtension -> flavorDimensions.add(flavorDimension)
            is LibraryExtension -> flavorDimensions.add(flavorDimension)
        }
    }

    BuildProductFlavor.values().forEach { productFlavor ->
        commonExtension.productFlavors.maybeCreate(productFlavor.flavourName)
            .apply {
                dimension = productFlavor.dimension.name
                productFlavor.versionNameSuffix?.let { versionNameSuffix = it }
                if (commonExtension is ApplicationExtension && productFlavor.applicationIdSuffix != null) {
                    applicationIdSuffix = productFlavor.applicationIdSuffix
                }

               /* val webClientId = localProperties.getProperty(productFlavor.webClientId)
                if (webClientId != null) {
                    buildConfigField("String", "WEB_CLIENT_ID", "\"$webClientId\"")
                }*/
            }
    }
}
