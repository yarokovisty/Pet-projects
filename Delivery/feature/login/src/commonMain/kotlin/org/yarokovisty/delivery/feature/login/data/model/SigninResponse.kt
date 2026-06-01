package org.yarokovisty.delivery.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.common.profile.main.data.model.UserDto

@Serializable
internal data class SigninResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("reason")
    val reason: String? = null,
    @SerialName("token")
    val token: String? = null,
    @SerialName("user")
    val user: UserDto,
)
