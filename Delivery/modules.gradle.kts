listOf(
    ":composeApp",

    ":design:theme"
).forEach { module ->
    include(module)
}