package org.yarokovisty.delivery.feature.delivery.calculator.di

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.common.delivery.step.MaxStepQualifier
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.viewmodel.CalculatorViewModel

val deliveryCalculatorModule = module {

    viewModel { (parcelInfo: ParcelInfo, sender: DeliveryPoint, receiver: DeliveryPoint) ->
        CalculatorViewModel(
            calculatorRepository = get(),
            router = get(),
            parcelInfo = parcelInfo,
            senderPoint = sender,
            receiverPoint = receiver,
            maxSteps = get(named<MaxStepQualifier>())
        )
    }
}
