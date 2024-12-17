// audioquiz.java.library => Java Library
plugins {
    id("audioquiz.java.library")
}

dependencies {
    projects.core.apply {
        implementation(model)
    }
    libs.apply {
        implementation(javax.inject)
    }
}
