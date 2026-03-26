package org.yarokovisty.delivery.libs.coordinator

data class CoordinatorResult<T>(
    val key: String,
    val value: T
)
