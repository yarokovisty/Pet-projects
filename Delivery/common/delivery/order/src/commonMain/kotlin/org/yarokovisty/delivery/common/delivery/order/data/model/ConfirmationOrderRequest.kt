package org.yarokovisty.delivery.common.delivery.order.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.common.delivery.person.data.model.PersonInfoRequest
import org.yarokovisty.delivery.common.delivery.point.data.model.AddressRequest

@Serializable
internal data class ConfirmationOrderRequest(
    @SerialName("packageId")
    val packageId: String,
    @SerialName("optionType")
    val optionType: String,
    @SerialName("senderPointId")
    val senderPointId: String,
    @SerialName("senderAddress")
    val senderAddress: AddressRequest,
    @SerialName("sender")
    val sender: PersonInfoRequest,
    @SerialName("receiverPointId")
    val receiverPointId: String,
    @SerialName("receiverAddress")
    val receiverAddress: AddressRequest,
    @SerialName("receiver")
    val receiver: PersonInfoRequest,
    @SerialName("payer")
    val payer: String
)
