listOf(
    ":composeApp",

    ":common:presentation",

    ":core:coroutines",
    ":core:network",
    ":core:storage",

    ":design:theme",
    ":design:uikit",

    ":feature:delivery:direction:api",
    ":feature:delivery:direction:impl",
    ":feature:delivery:main:api",
    ":feature:delivery:main:impl",

    ":libs:coordinator",
    ":libs:navigation",

    ":util:coroutines",
    ":util:logger",
    ":util:modifier",
    ":util:unit-test"
).forEach { module ->
    include(module)
}
