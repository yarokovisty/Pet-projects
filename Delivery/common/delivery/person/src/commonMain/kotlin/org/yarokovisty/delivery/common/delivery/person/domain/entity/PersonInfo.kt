package org.yarokovisty.delivery.common.delivery.person.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class PersonInfo(
    val firstname: String,
    val lastname: String,
    val middlename: String?,
    val phone: String
)
