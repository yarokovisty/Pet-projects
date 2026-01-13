listOf(
    ":composeApp",
    ":shared",

    ":common:data",
    ":common:presentation",

    ":core:dispatchers",
    ":core:navigation",
    ":core:network",

    ":design:component",
    ":design:resources",
    ":design:theme",

    ":feature:character:api",
    ":feature:character:impl",
    ":feature:episode:api",
    ":feature:episode:impl",
    ":feature:location:api",
    ":feature:location:impl",

    ":library:logger",
    ":library:navigation-ksp:annotation",
    ":library:navigation-ksp:processor",
    ":library:result",

    ":util",
).forEach { module ->
    include(module)
}