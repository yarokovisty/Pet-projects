listOf(
    ":composeApp",

    ":design:theme",

    ":util:coroutines",
).forEach { module ->
    include(module)
}