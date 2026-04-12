package org.yarokovisty.delivery.feature.profile.main.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("_id")
    val id: String,
    @SerialName("phone")
    val phone: String,
    @SerialName("firstname")
    val firstname: String? = null,
    @SerialName("middlename")
    val middlename: String? = null,
    @SerialName("lastname")
    val lastname: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("city")
    val city: String? = null
)
