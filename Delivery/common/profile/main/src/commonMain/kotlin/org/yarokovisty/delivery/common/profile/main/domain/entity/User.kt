package org.yarokovisty.delivery.common.profile.main.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val phone: String,
    val firstname: String?,
    val lastname: String?,
    val middlename: String?,
    val email: String?,
    val city: String?
)
