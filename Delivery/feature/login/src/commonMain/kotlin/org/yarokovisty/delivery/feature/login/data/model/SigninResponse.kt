package org.yarokovisty.delivery.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SigninResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("reason")
    val reason: String? = null,
    @SerialName("token")
    val token: String? = null
)
