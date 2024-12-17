package com.audioquiz

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import java.util.Properties

fun configureSigningConfigs(applicationExtension: ApplicationExtension, project: Project) {
    applicationExtension.apply {
        val keyPropertiesFile = project.file("${project.rootDir}/keystores/keystore.properties")
        val keyProps = Properties()
        if (keyPropertiesFile.exists()) {
            keyProps.load(keyPropertiesFile.reader())
        }
        signingConfigs {
              if (findByName("debug") == null) {
                    create("debug") {
                          keyAlias = "android"
                          keyPassword = "android"
                          storeFile = project.file(System.getProperty("user.home") + "/.android/debug.keystore")
                          storePassword = "android"
                    }
              }
              if (findByName("release") == null) {
                    create("release") {
                          keyAlias = keyProps.getProperty("releaseKeyAlias")
                          keyPassword = keyProps.getProperty("releaseKeyPassword")
                          storeFile = project.file("${project.rootDir}/keystores/audioquiz-release-key.jks")
                          storePassword = keyProps.getProperty("releaseStorePassword")
                    }
              }
        }
    }
}
