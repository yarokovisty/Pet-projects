package org.yarokovisty.delivery.common.delivery.point.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val street: String,
    val house: String,
    val apartment: String,
    val comment: String,
    val nonContacted: Boolean?
)
