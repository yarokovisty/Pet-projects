val appModules = listOf(
    ":composeApp"
)

val commonModules = listOf(
    ":common:auth",
    ":common:presentation",
    ":common:validation"
)

val coreModules = listOf(
    ":core:common:coroutines",
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
    ":feature:profile:main:api",
    ":feature:profile:main:impl",
)

val libsModules = listOf(
    ":libs:coordinator",
    ":libs:encryption",
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
