package org.yarokovisty.delivery.feature.profile.main.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UserRequest(
    @SerialName("profile")
    val profile: org.yarokovisty.delivery.feature.profile.main.data.model.ProfileRequest,
    @SerialName("phone")
    val phone: String
)
