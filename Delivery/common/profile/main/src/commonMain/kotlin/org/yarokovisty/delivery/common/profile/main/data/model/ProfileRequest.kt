package org.yarokovisty.delivery.common.profile.main.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProfileRequest(
    @SerialName("firstname")
    val firstname: String?,
    @SerialName("lastname")
    val lastname: String?,
    @SerialName("middlename")
    val middlename: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("city")
    val city: String?
)
