package com.audioquiz

object AndroidConfig {

    const val APP_ID = "com.audioquiz"
    const val NAMESPACE = "com.audioquiz"
    const val COMPILE_SDK = 35
    const val MIN_SDK = 26
    const val TARGET_SDK = 35
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"
    const val TEST_INSTRUMENTATION_RUNNER = "com.audioquiz.core.testing.di.HiltTestRunner"
    const val TEST_BENCHMARK_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val BENCHMARK_TARGET_PROJECT_PATH = ":app"

}
