// audioquiz.java.library => Java Library
plugins {
    id("audioquiz.java.library")
}

dependencies {
    libs.apply {
        implementation(javax.inject)
    }
}
