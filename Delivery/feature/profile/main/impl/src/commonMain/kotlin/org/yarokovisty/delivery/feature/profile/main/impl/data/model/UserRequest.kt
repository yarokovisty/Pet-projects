package org.yarokovisty.delivery.feature.profile.main.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserRequest(
    @SerialName("profile")
    val profile: ProfileRequest,
    @SerialName("phone")
    val phone: String
)
