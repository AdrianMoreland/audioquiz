package com.audioquiz

import com.jraska.module.graph.assertion.GraphRulesExtension

internal fun configureModulesGraphAssert(
    graphRulesExtension: GraphRulesExtension,
) {
    graphRulesExtension.apply {
        maxHeight = ModuleGraphAssert.MAX_HEIGHT
        allowed = ModuleGraphAssert.allowed
        restricted = ModuleGraphAssert.restricted
        configurations = ModuleGraphAssert.configurations
        assertOnAnyBuild = true
    }
}

/* CONFIG */
object ModuleGraphAssert {

    const val MAX_HEIGHT = 7

    val allowed = arrayOf(
        ":app\\S* -> :feature:\\S*",
        ":app\\S* -> :core\\S*",
        ":app\\S* -> :library\\S*",
        ":app\\S* -> :data\\S*",
        ":feature\\S* -> :core:domain",
        ":feature\\S* -> :core:model",
        ":feature\\S* -> :library:designsystem",
        ":core:domain -> :core:model",
        ":data:local -> :data:api",
        ":core:sync -> :core:extensions",
        ":core:sync -> :data:api",
        ":core:sync -> :data:repository",
        ":core:sync -> :data:remote",
        ":core:sync -> :data:local",
        ":data:repository -> :library:util",
        ":feature:login -> :library:util",
        ":feature:quiz -> :library:util",
        ":feature:login -> :data:api",
//        ":feature:home -> :core:ui",
        ":feature:settings -> :data:repository",
//        ":feature:settings -> :core:ui",
//        ":feature:stats -> :core:ui",
        ":data:repository -> :core:domain",
        ":data:repository -> :core:extensions",
        ":data:repository -> :data:api",
        ":data:repository -> :data:remote",
        ":data:repository -> :data:local",
        ":data:remote -> :core:model",
        ":data:remote -> :core:extensions",
        ":data:remote -> :data:api",
        ":data:local -> :core:extensions",
        ":core:sync -> :core:model",
        ":core:sync -> :core:domain",
        ":data:api -> :core:model",
        ":data:api -> :core:domain",
        ":data:repository -> :core:model",
        ":data:local -> :core:model"
    )

    val restricted = arrayOf(
        ":feature\\S* -X> :feature\\S*",
    )

    val configurations = setOf(
        "api",
        "implementation",
    )
}
