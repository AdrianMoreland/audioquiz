plugins {
    `kotlin-dsl`
}

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    compileOnly(libs.ktlint.kotlinter)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.junit.jupiter.api)
    implementation(libs.rxjava)
    implementation(libs.hilt.agp)
    implementation(libs.room.gradlePlugin)
    implementation(libs.room.rxjava3)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "audioquiz.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "audioquiz.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "audioquiz.android.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("androidFeature") {
            id = "audioquiz.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidDetekt") {
            id = "audioquiz.android.detekt"
            implementationClass = "AndroidDetektConventionPlugin"
        }
        register("androidKtlint") {
            id = "audioquiz.android.ktlint"
            implementationClass = "AndroidKotlinterConventionPlugin"
        }
    }
}
