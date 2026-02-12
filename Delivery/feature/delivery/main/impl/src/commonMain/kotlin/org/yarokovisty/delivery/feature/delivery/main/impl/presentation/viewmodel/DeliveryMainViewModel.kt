package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.viewmodel

import kotlinx.coroutines.async
import org.yarokovisty.delivery.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.main.api.domain.model.DeliveryPoint
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryMainState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.changeTrackerState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.contentState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.errorState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.loadingState

internal class DeliveryMainViewModel(
    private val deliveryRepository: DeliveryRepository,
) : BaseViewModel<DeliveryMainState, DeliveryMainIntent, Nothing>() {

    private companion object {
        const val START_INDEX_OFFER_DELIVERY_POINT = 0
        const val END_INDEX_OFFER_DELIVERY_POINT = 3
    }

    init {
        loadData()
    }

    override fun initState(): DeliveryMainState =
        DeliveryMainState.INITIAL

    override fun onIntent(intent: DeliveryMainIntent) {
        when (intent) {
            DeliveryMainIntent.SelectDeliveryPointFrom -> TODO()
            is DeliveryMainIntent.SelectAlternativeDeliveryPointFrom -> TODO()
            DeliveryMainIntent.SelectDeliveryPointTo -> TODO()
            is DeliveryMainIntent.SelectAlternativeDeliveryPointTo -> TODO()
            DeliveryMainIntent.SelectParcelType -> TODO()
            DeliveryMainIntent.CalculateDelivery -> TODO()

            is DeliveryMainIntent.ChangeInputParcelId -> changeInputParcelId(intent.id)
            is DeliveryMainIntent.TrackParcel -> TODO()

            DeliveryMainIntent.RepeatLoad -> loadData()
        }
    }

    private fun loadData() {
        updateState { loadingState() }

        launchTrying {
            val deliveryPointsDeferred = async { deliveryRepository.getDeliveryPoints() }
            val parcelTypesDeferred = async { deliveryRepository.getParcelTypes() }

            val deliveryPoints = deliveryPointsDeferred.await()
            val alternativePoints = getAlternativeDeliveryPoints(deliveryPoints)
            val parcelTypes = parcelTypesDeferred.await()

            updateState {
                contentState(deliveryPoints, alternativePoints, alternativePoints, parcelTypes)
            }
        } handle ::handleError
    }

    private fun getAlternativeDeliveryPoints(points: List<DeliveryPoint>): List<String> =
        points.subList(START_INDEX_OFFER_DELIVERY_POINT, END_INDEX_OFFER_DELIVERY_POINT).map {
            it.name
        }

    private fun handleError(error: Exception) {
        updateState { errorState() }
    }

    private fun changeInputParcelId(id: String) {
        updateState { changeTrackerState(id) }
    }
}
