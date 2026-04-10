package org.yarokovisty.delivery.feature.profile.main.api.domain.entity

data class User(
    val id: String,
    val phone: String,
    val firstname: String?,
    val lastname: String?,
    val middlename: String?,
    val email: String?,
    val city: String?
)
