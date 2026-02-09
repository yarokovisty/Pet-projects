listOf(
    ":composeApp",

    ":common:presentation",

    ":core:network",

    ":design:theme",

    ":util:coroutines",
    ":util:logger",
).forEach { module ->
    include(module)
}