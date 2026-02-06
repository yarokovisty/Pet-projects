listOf(
    ":composeApp",

    ":common:presentation",

    ":design:theme",

    ":util:coroutines",
).forEach { module ->
    include(module)
}