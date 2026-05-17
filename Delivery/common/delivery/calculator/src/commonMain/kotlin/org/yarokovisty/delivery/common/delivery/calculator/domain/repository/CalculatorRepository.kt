package org.yarokovisty.delivery.common.delivery.calculator.domain.repository

import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo

interface CalculatorRepository {

    suspend fun getOptionList(
        parcelInfo: ParcelInfo,
        senderPoint: DeliveryPoint,
        receiverPoint: DeliveryPoint
    ): List<Option>

    suspend fun getOption(): Option?

    suspend fun setOption(option: Option)

    suspend fun clearOption()
}
