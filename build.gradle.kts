import org.gradle.api.tasks.TaskAction

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.room.gradle) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.gms) apply false
    alias(libs.plugins.dependency.versions) apply false
    alias(libs.plugins.dependency.analysis) apply false
    id("audioquiz.dependency.analysis")
    alias(libs.plugins.modules.graph.assert) apply false
    id ("com.savvasdalkitsis.module-dependency-graph") version "0.10"

}
