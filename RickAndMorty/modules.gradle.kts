listOf(
    ":composeApp",
    ":shared",

    ":common:data",
    ":common:presentation",

    ":core:navigation",
    ":core:network",

    ":design:component",
    ":design:resources",
    ":design:theme",

    ":feature:character",
    ":feature:location",

    ":library:logger",
    ":library:result",

    ":util"
).forEach { module ->
    include(module)
}