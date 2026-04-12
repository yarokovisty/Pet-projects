package org.yarokovisty.delivery.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OtpResponse(
    @SerialName("success")
    val success: Boolean,
    @SerialName("retryDelay")
    val retryDelay: Long
)
