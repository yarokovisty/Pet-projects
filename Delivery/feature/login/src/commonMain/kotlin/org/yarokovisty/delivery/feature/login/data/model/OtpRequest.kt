package org.yarokovisty.delivery.feature.login.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OtpRequest(
    @SerialName("phone")
    val phone: String
)
