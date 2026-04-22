package org.yarokovisty.delivery.feature.delivery.main.presentation.viewmodel

import kotlinx.coroutines.async
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.delivery.common.delivery.direction.domain.repository.DirectionRepository
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.parcel.domain.repository.ParcelRepository
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.main.domain.usecase.GetAlternativeDeliveryPointsUseCase
import org.yarokovisty.delivery.feature.delivery.main.domain.usecase.GetDeliveryPointByNameUseCase
import org.yarokovisty.delivery.feature.delivery.main.navigation.DeliveryRouter
import org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.DeliveryMainState
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.changeTracker
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.closeSelectParcelTypeScreen
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.contentState
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.errorState
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.initial
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.loadingState
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.selectDeliveryPointFrom
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.selectDeliveryPointTo
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.selectParcelType
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.showSelectParcelTypeScreen

internal class DeliveryMainViewModel(
    private val deliveryRepository: ParcelRepository,
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
            is DeliveryMainIntent.SelectDeliveryPointTo -> openDirectionToScreen()
            is DeliveryMainIntent.SelectAlternativeDeliveryPointTo ->
                selectAlternativeDeliveryPointTo(intent.pointName)
            is DeliveryMainIntent.OpenParcelTypeScreen -> openSelectParcelTypeScreen()
            is DeliveryMainIntent.CloseParcelTypeScreen -> closeSelectParcelTypeScreen()
            is DeliveryMainIntent.SelectParcelType -> selectParcelType(intent.parcelInfo)
            is DeliveryMainIntent.CalculateDelivery -> openCalculatorScreen()
            is DeliveryMainIntent.ChangeInputParcelId -> changeInputParcelId(intent.id)
            is DeliveryMainIntent.TrackParcel -> TODO()
        }
    }

    private fun loadData() {
        updateState { loadingState() }

        launchTrying {
            val deliveryPointsDeferred = async { directionRepository.getDeliveryPointList() }
            val parcelTypesDeferred = async { deliveryRepository.getParcelInfoList() }

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

    private fun openCalculatorScreen() {
        val contentState = stateValue.deliveryCalculatorContent ?: return
        val parcelInfo = contentState.selectedParcelInfo
        val senderPoint = contentState.selectedPointFrom
        val receiverPoint = contentState.selectedPointTo

        if (parcelInfo == null || senderPoint == null || receiverPoint == null) return

        router.openCalculatorScreen(parcelInfo, senderPoint, receiverPoint)
    }

    private fun changeInputParcelId(id: String) {
        updateState { changeTracker(id) }
    }
}
