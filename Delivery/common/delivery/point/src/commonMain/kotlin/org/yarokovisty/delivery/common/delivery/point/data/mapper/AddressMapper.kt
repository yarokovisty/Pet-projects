package org.yarokovisty.delivery.common.delivery.point.data.mapper

import org.yarokovisty.delivery.common.delivery.point.data.model.AddressRequest
import org.yarokovisty.delivery.common.delivery.point.data.model.AddressResponse
import org.yarokovisty.delivery.common.delivery.point.domain.entity.Address

fun AddressResponse.toItem(): Address =
    Address(
        street = street,
        house = house,
        apartment = apartment,
        comment = comment,
        nonContacted = nonContacted
    )

fun Address.toRequest(): AddressRequest =
    AddressRequest(
        street = street,
        house = house,
        apartment = apartment,
        comment = comment,
        nonContacted = nonContacted
    )
