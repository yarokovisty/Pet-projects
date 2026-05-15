package org.yarokovisty.delivery.common.delivery.point.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressResponse(
    @SerialName("street")
    val street: String,
    @SerialName("house")
    val house: String,
    @SerialName("apartment")
    val apartment: String,
    @SerialName("comment")
    val comment: String,
    @SerialName("isNonContact")
    val nonContacted: Boolean? = null
)
