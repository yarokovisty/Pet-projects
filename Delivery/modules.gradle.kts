listOf(
    ":composeApp",

    ":common:presentation",

    ":design:theme",

    ":util:coroutines",
    ":util:logger",
).forEach { module ->
    include(module)
}