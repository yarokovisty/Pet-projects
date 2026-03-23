listOf(
    ":composeApp",

    ":common:presentation",

    ":core:network",

    ":design:theme",
    ":design:uikit",

    ":feature:delivery:main:api",
    ":feature:delivery:main:impl",

    ":libs:navigation",

    ":util:coroutines",
    ":util:logger",
    ":util:modifier",
).forEach { module ->
    include(module)
}
