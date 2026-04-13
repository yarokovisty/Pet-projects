package org.yarokovisty.delivery.feature.delivery.direction.presentation.viewmodel

import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.common.delivery.direction.domain.repository.DirectionRepository
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.direction.navigation.DirectionRouter
import org.yarokovisty.delivery.feature.delivery.direction.presentation.intent.DirectionIntent
import org.yarokovisty.delivery.feature.delivery.direction.presentation.state.DirectionState
import org.yarokovisty.delivery.feature.delivery.direction.presentation.state.contentState
import org.yarokovisty.delivery.feature.delivery.direction.presentation.state.errorState
import org.yarokovisty.delivery.feature.delivery.direction.presentation.state.initial
import org.yarokovisty.delivery.feature.delivery.direction.presentation.state.loadingState

internal class DirectionViewModel(
    private val directionRepository: DirectionRepository,
    private val router: DirectionRouter,
    directionType: DirectionType
) : BaseViewModel<DirectionState, DirectionIntent, Nothing>(initial(directionType)) {

    init {
        loadData()
    }

    override fun onIntent(intent: DirectionIntent) {
        when (intent) {
            is DirectionIntent.Back -> back()
            is DirectionIntent.LoadData -> loadData()
            is DirectionIntent.SelectDeliveryPoint -> selectDeliveryPoint(intent.point)
        }
    }

    private fun loadData() {
        updateState { loadingState() }

        launchTrying {
            val deliveryPoints = directionRepository.getDeliveryPointList()
            updateState { contentState(deliveryPoints) }
        } handle { handleError() }
    }

    private fun handleError() {
        updateState { errorState() }
    }

    private fun back() {
        router.back()
    }

    private fun selectDeliveryPoint(point: DeliveryPoint) {
        launch {
            publishResult(point)
            router.back()
        }
    }
}
