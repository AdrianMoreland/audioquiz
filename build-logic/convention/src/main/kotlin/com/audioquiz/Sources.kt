package com.audioquiz

object Sources {

    const val MAIN = "main"
    const val TEST = "test"
    const val ANDROID_TEST = "androidTest"

    object Folders {

        const val SRC = "src"

        const val MAIN = "main"
        const val TEST = "test"
        const val INTEGRATION_TEST = "integrationTest"
        const val ROBOLECTRIC_TEST = "robolectricTest"
        const val ANDROID_TEST = "androidTest"

        object Subfolder {

            const val JAVA = "java"
            const val RESOURCES = "resources"

        }

    }

    object Main {

        const val JAVA = "${Folders.SRC}/${Folders.MAIN}/" +
            Folders.Subfolder.JAVA
        const val RESOURCES = "${Folders.SRC}/${Folders.MAIN}/" +
            Folders.Subfolder.RESOURCES

    }

    object Test {

        const val JAVA = "${Folders.SRC}/${Folders.TEST}/" +
            Folders.Subfolder.JAVA
        const val RESOURCES = "${Folders.SRC}/${Folders.TEST}/" +
            Folders.Subfolder.RESOURCES

    }

    object Integration {

        const val JAVA = "${Folders.SRC}/${Folders.INTEGRATION_TEST}/" +
            Folders.Subfolder.JAVA
        const val RESOURCES = "${Folders.SRC}/${Folders.INTEGRATION_TEST}/" +
            Folders.Subfolder.RESOURCES

    }

    object Robolectric {

        const val JAVA = "${Folders.SRC}/${Folders.ROBOLECTRIC_TEST}/" +
            Folders.Subfolder.JAVA
        const val RESOURCES = "${Folders.SRC}/${Folders.ROBOLECTRIC_TEST}/" +
            Folders.Subfolder.RESOURCES

    }

    object Android {

        object Test {

            const val JAVA = "${Folders.SRC}/${Folders.ANDROID_TEST}/" +
                Folders.Subfolder.JAVA
            const val RESOURCES = "${Folders.SRC}/${Folders.ANDROID_TEST}/" +
                Folders.Subfolder.RESOURCES

        }

    }

}
