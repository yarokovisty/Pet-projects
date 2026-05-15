package org.yarokovisty.delivery.common.delivery.person.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonInfoResponse(
    @SerialName("firstname")
    val firstname: String,
    @SerialName("lastname")
    val lastname: String,
    @SerialName("middlename")
    val middlename: String,
    @SerialName("phone")
    val phone: String
)
