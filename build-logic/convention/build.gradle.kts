plugins {
    `kotlin-dsl`
}

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.google.services)
    implementation(libs.firebase.performance.gradlePlugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.junit.jupiter.api)
    compileOnly(libs.room.gradle.plugin)
    compileOnly(libs.rxjava)
    compileOnly(libs.hilt.agp)
    compileOnly(libs.dependency.analysis.gradle.plugin)
    compileOnly(libs.dependency.versions.gradle.plugin)
    compileOnly(libs.modules.graph.assert.gradle.plugin)
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
        register("javaLibrary") {
            id = "audioquiz.java.library"
            implementationClass = "JavaLibraryConventionPlugin"
        }
        register("androidHilt") {
            id = "audioquiz.android.hilt"
            implementationClass = "HiltConventionPlugin"
        }
        register("androidFeature") {
            id = "audioquiz.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidRoom") {
            id = "audioquiz.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("firebase") {
            id = "audioquiz.firebase"
            implementationClass = "FirebaseConventionPlugin"
        }
        register("dependencyVersions") {
            id = "audioquiz.dependency.versions"
            implementationClass = "DependencyVersionsConventionPlugin"
        }
        register("dependencyAnalysis") {
            id = "audioquiz.dependency.analysis"
            implementationClass = "DependencyAnalysisConventionPlugin"
        }
        register("modulesGraphAssert") {
            id = "audioquiz.modules.graph.assert"
            implementationClass = "ModulesGraphAssertConventionPlugin"
        }

    }
}
