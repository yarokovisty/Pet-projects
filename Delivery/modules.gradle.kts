val appModules = listOf(
    ":composeApp"
)

val commonModules = listOf(
    ":common:auth",
    ":common:delivery:calculator",
    ":common:delivery:direction",
    ":common:delivery:parcel",
    ":common:delivery:person",
    ":common:delivery:point",
    ":common:delivery:step",
    ":common:profile:main",
    ":common:validation"
)

val coreModules = listOf(
    ":core:common:coroutines",
    ":core:common:presentation",
    ":core:network",
    ":core:storage"
)

val designModules = listOf(
    ":design:theme",
    ":design:uikit"
)

val featureModules = listOf(
    ":feature:delivery:calculator",
    ":feature:delivery:direction",
    ":feature:delivery:main",
    ":feature:delivery:person",
    ":feature:login",
    ":feature:profile:main",
)

val libsModules = listOf(
    ":libs:coordinator",
    ":libs:encryption",
    ":libs:navigation"
)

val utilModules = listOf(
    ":util:coroutines",
    ":util:flow",
    ":util:kotlin",
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
