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

    ":feature:character:api",
    ":feature:character:impl",
    ":feature:episode",
    ":feature:location",

    ":library:logger",
    ":library:result",

    ":util",
).forEach { module ->
    include(module)
}