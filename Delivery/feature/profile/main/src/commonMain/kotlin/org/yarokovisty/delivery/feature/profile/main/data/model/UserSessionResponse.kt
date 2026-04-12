package org.yarokovisty.delivery.feature.profile.main.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSessionResponse(
    @SerialName("user")
    val user: org.yarokovisty.delivery.feature.profile.main.data.model.UserResponse
)
