package org.yarokovisty.delivery.util.kotlin.enums

inline fun <reified T : Enum<T>> String.toEnum(): T {
    val normalized = this
        .replace("-", "_")
        .replace(Regex("([a-z])([A-Z])"), "$1_$2")
        .uppercase()

    return enumValues<T>().firstOrNull { it.name == normalized }
        ?: throw IllegalArgumentException("Unknown enum name $normalized")
}
