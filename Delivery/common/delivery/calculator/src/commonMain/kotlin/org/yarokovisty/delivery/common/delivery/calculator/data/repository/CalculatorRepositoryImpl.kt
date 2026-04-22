package org.yarokovisty.delivery.common.delivery.calculator.data.repository

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.calculator.data.datasource.CalculatorLocalDataSource
import org.yarokovisty.delivery.common.delivery.calculator.data.datasource.CalculatorRemoteDataSource
import org.yarokovisty.delivery.common.delivery.calculator.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.calculator.data.mapper.toRequest
import org.yarokovisty.delivery.common.delivery.calculator.data.model.CalculatorRequest
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository

internal class CalculatorRepositoryImpl(
    private val remoteDataSource: CalculatorRemoteDataSource,
    private val localDataSource: CalculatorLocalDataSource,
) : CalculatorRepository {

    override suspend fun getOptionList(
        parcelInfo: ParcelInfo,
        senderPoint: DeliveryPoint,
        receiverPoint: DeliveryPoint
    ): List<Option> =
        remoteDataSource.getOptionList(
            request = CalculatorRequest(
                packageRequest = parcelInfo.toRequest(),
                senderPoint = senderPoint.toRequest(),
                receiverPoint = receiverPoint.toRequest()
            )
        ).toItem()

    override fun getOption(): Option? =
        localDataSource.getOption()

    override fun setOption(option: Option) {
        localDataSource.setOption(option)
    }
}
