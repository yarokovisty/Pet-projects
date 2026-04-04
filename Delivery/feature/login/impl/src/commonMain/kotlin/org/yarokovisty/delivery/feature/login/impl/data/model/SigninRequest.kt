package org.yarokovisty.delivery.feature.login.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SigninRequest(
    @SerialName("phone")
    val phone: String,
    @SerialName("code")
    val code: Int
)
