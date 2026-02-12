package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.viewmodel

import kotlinx.coroutines.async
import org.yarokovisty.delivery.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase.GetAlternativeDeliveryPointsUseCase
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryMainState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.changeTrackerState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.contentState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.errorState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.loadingState

internal class DeliveryMainViewModel(
    private val deliveryRepository: DeliveryRepository,
    private val getAlternativeDeliveryPointsUseCase: GetAlternativeDeliveryPointsUseCase,
) : BaseViewModel<DeliveryMainState, DeliveryMainIntent, Nothing>() {

    init {
        loadData()
    }

    override fun initState(): DeliveryMainState =
        DeliveryMainState.INITIAL

    override fun onIntent(intent: DeliveryMainIntent) {
        when (intent) {
            DeliveryMainIntent.LoadData -> loadData()
            DeliveryMainIntent.SelectDeliveryPointFrom -> TODO()
            is DeliveryMainIntent.SelectAlternativeDeliveryPointFrom -> TODO()
            DeliveryMainIntent.SelectDeliveryPointTo -> TODO()
            is DeliveryMainIntent.SelectAlternativeDeliveryPointTo -> TODO()
            DeliveryMainIntent.SelectParcelType -> TODO()
            DeliveryMainIntent.CalculateDelivery -> TODO()
            is DeliveryMainIntent.ChangeInputParcelId -> changeInputParcelId(intent.id)
            is DeliveryMainIntent.TrackParcel -> TODO()
        }
    }

    private fun loadData() {
        updateState { loadingState() }

        launchTrying {
            val deliveryPointsDeferred = async { deliveryRepository.getDeliveryPoints() }
            val parcelTypesDeferred = async { deliveryRepository.getParcelTypes() }

            val deliveryPoints = deliveryPointsDeferred.await()
            val parcelTypes = parcelTypesDeferred.await()

            val alternativePoints = getAlternativeDeliveryPointsUseCase(deliveryPoints).map { it.name }

            updateState {
                contentState(deliveryPoints, alternativePoints, alternativePoints, parcelTypes)
            }
        } handle { handleError() }
    }
    private fun handleError() {
        updateState { errorState() }
    }

    private fun changeInputParcelId(id: String) {
        updateState { changeTrackerState(id) }
    }
}
