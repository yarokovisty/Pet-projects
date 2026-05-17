package org.yarokovisty.delivery.common.delivery.calculator.data.repository

import org.yarokovisty.delivery.common.delivery.calculator.data.datasource.CalculatorLocalDataSource
import org.yarokovisty.delivery.common.delivery.calculator.data.datasource.CalculatorRemoteDataSource
import org.yarokovisty.delivery.common.delivery.calculator.data.mapper.toItem
import org.yarokovisty.delivery.common.delivery.calculator.data.mapper.toRequest
import org.yarokovisty.delivery.common.delivery.calculator.data.model.CalculatorRequest
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo

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

    override suspend fun getOption(): Option? =
        localDataSource.getOption()

    override suspend fun setOption(option: Option) {
        localDataSource.setOption(option)
    }

    override suspend fun clearOption() {
        localDataSource.clearOption()
    }
}
