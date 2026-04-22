package org.yarokovisty.delivery.common.delivery.calculator.domain.repository

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option

interface CalculatorRepository {

    suspend fun getOptionList(
        parcelInfo: ParcelInfo,
        senderPoint: DeliveryPoint,
        receiverPoint: DeliveryPoint
    ): List<Option>

    fun getOption(): Option?

    fun setOption(option: Option)
}
