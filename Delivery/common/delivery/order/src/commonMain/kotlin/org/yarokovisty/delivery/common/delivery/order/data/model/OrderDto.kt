package org.yarokovisty.delivery.common.delivery.order.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.yarokovisty.delivery.common.delivery.direction.data.model.DeliveryPointResponse
import org.yarokovisty.delivery.common.delivery.parcel.data.model.PackageTypeResponse
import org.yarokovisty.delivery.common.delivery.person.data.model.PersonInfoResponse
import org.yarokovisty.delivery.common.delivery.point.data.model.AddressResponse

@Serializable
internal data class OrderDto(
    @SerialName("_id")
    val id: String,
    @SerialName("price")
    val price: Double,
    @SerialName("package")
    val packageType: PackageTypeResponse,
    @SerialName("option")
    val option: String,
    @SerialName("senderPoint")
    val senderPoint: DeliveryPointResponse,
    @SerialName("senderAddress")
    val senderAddress: AddressResponse,
    @SerialName("sender")
    val sender: PersonInfoResponse,
    @SerialName("receiverPoint")
    val receiverPoint: DeliveryPointResponse,
    @SerialName("receiverAddress")
    val receiverAddress: AddressResponse,
    @SerialName("receiver")
    val receiver: PersonInfoResponse,
    @SerialName("payer")
    val payer: String,
    @SerialName("status")
    val status: Int,
    @SerialName("cancellable")
    val cancellable: Boolean
)
