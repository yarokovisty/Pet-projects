val appModules = listOf(
    ":composeApp"
)

val commonModules = listOf(
    ":common:auth",
    ":common:coroutines",
    ":common:presentation",
    ":common:validation"
)

val coreModules = listOf(
    ":core:network",
    ":core:storage"
)

val designModules = listOf(
    ":design:theme",
    ":design:uikit"
)

val featureModules = listOf(
    ":feature:delivery:direction:api",
    ":feature:delivery:direction:impl",
    ":feature:delivery:main:api",
    ":feature:delivery:main:impl",
    ":feature:login:api",
    ":feature:login:impl",
    ":feature:profile:main:api"
)

val libsModules = listOf(
    ":libs:coordinator",
    ":libs:navigation"
)

val utilModules = listOf(
    ":util:coroutines",
    ":util:flow",
    ":util:logger",
    ":util:modifier",
    ":util:phone",
    ":util:unit-test",
    ":util:validation"
)

val modules = mapOf(
    "app" to appModules,
    "common" to commonModules,
    "core" to coreModules,
    "design" to designModules,
    "feature" to featureModules,
    "libs" to libsModules,
    "util" to utilModules,
)

modules.values
    .flatten()
    .forEach { include(it) }
