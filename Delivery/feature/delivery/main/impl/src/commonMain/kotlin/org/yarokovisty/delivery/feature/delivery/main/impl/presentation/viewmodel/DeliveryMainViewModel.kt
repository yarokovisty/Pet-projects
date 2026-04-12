package org.yarokovisty.delivery.feature.delivery.main.impl.presentation.viewmodel

import kotlinx.coroutines.async
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.main.api.domain.entity.ParcelInfo
import org.yarokovisty.delivery.feature.delivery.main.api.domain.repository.DeliveryRepository
import org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase.GetAlternativeDeliveryPointsUseCase
import org.yarokovisty.delivery.feature.delivery.main.impl.domain.usecase.GetDeliveryPointByNameUseCase
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.router.DeliveryRouter
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryMainState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.changeTracker
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.closeSelectParcelTypeScreen
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.contentState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.errorState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.initial
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.loadingState
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.selectDeliveryPointFrom
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.selectDeliveryPointTo
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.selectParcelType
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.showSelectParcelTypeScreen
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DirectionType
import org.yarokovisty.delivery.feature.direction.api.domain.repository.DirectionRepository

internal class DeliveryMainViewModel(
    private val deliveryRepository: DeliveryRepository,
    private val directionRepository: DirectionRepository,
    private val getAlternativeDeliveryPointsUseCase: GetAlternativeDeliveryPointsUseCase,
    private val getDeliveryPointByNameUseCase: GetDeliveryPointByNameUseCase,
    private val router: DeliveryRouter,
) : BaseViewModel<DeliveryMainState, DeliveryMainIntent, Nothing>(initial()) {

    init {
        loadData()
    }

    override fun onIntent(intent: DeliveryMainIntent) {
        when (intent) {
            is DeliveryMainIntent.LoadData -> loadData()
            is DeliveryMainIntent.SelectDeliveryPointFrom -> openDirectionFromScreen()
            is DeliveryMainIntent.SelectAlternativeDeliveryPointFrom ->
                selectAlternativeDeliveryPointFrom(intent.pointName)
            DeliveryMainIntent.SelectDeliveryPointTo -> openDirectionToScreen()
            is DeliveryMainIntent.SelectAlternativeDeliveryPointTo ->
                selectAlternativeDeliveryPointTo(intent.pointName)
            is DeliveryMainIntent.OpenParcelTypeScreen -> openSelectParcelTypeScreen()
            is DeliveryMainIntent.CloseParcelTypeScreen -> closeSelectParcelTypeScreen()
            is DeliveryMainIntent.SelectParcelType -> selectParcelType(intent.parcelInfo)
            is DeliveryMainIntent.CalculateDelivery -> TODO()
            is DeliveryMainIntent.ChangeInputParcelId -> changeInputParcelId(intent.id)
            is DeliveryMainIntent.TrackParcel -> TODO()
        }
    }

    private fun loadData() {
        updateState { loadingState() }

        launchTrying {
            val deliveryPointsDeferred = async { directionRepository.getDeliveryPoints() }
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

    private fun openDirectionFromScreen() {
        launch {
            val point = awaitResult<DeliveryPoint>()
            updateState { selectDeliveryPointFrom(point) }
        }
        router.openDirectionScreen(DirectionType.FROM)
    }

    private fun selectAlternativeDeliveryPointFrom(pointName: String) {
        val content = stateValue.deliveryCalculatorContent ?: return

        launch {
            val selectedPoint = getDeliveryPointByNameUseCase(content.points, pointName)
            updateState { selectDeliveryPointFrom(selectedPoint) }
        }
    }

    private fun openDirectionToScreen() {
        launch {
            val point = awaitResult<DeliveryPoint>()
            updateState { selectDeliveryPointTo(point) }
        }
        router.openDirectionScreen(DirectionType.TO)
    }

    private fun selectAlternativeDeliveryPointTo(pointName: String) {
        val content = stateValue.deliveryCalculatorContent ?: return

        launch {
            val selectedPoint = getDeliveryPointByNameUseCase(content.points, pointName)
            updateState { selectDeliveryPointTo(selectedPoint) }
        }
    }

    private fun openSelectParcelTypeScreen() {
        updateState { showSelectParcelTypeScreen() }
    }

    private fun closeSelectParcelTypeScreen() {
        updateState { closeSelectParcelTypeScreen() }
    }

    private fun selectParcelType(parcelInfo: ParcelInfo) {
        updateState { selectParcelType(parcelInfo) }
    }

    private fun changeInputParcelId(id: String) {
        updateState { changeTracker(id) }
    }
}
