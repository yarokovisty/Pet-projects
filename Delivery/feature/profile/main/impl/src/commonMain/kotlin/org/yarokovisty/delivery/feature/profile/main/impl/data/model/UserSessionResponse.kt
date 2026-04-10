package org.yarokovisty.delivery.feature.profile.main.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSessionResponse(
    @SerialName("user")
    val user: UserResponse
)
