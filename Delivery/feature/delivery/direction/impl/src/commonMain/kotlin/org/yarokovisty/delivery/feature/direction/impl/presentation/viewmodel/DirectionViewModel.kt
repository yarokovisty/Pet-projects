package org.yarokovisty.delivery.feature.direction.impl.presentation.viewmodel

import org.yarokovisty.delivery.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.feature.direction.api.domain.entity.DirectionType
import org.yarokovisty.delivery.feature.direction.api.domain.repository.DirectionRepository
import org.yarokovisty.delivery.feature.direction.impl.presentation.intent.DirectionIntent
import org.yarokovisty.delivery.feature.direction.impl.presentation.router.DirectionRouter
import org.yarokovisty.delivery.feature.direction.impl.presentation.state.DirectionState
import org.yarokovisty.delivery.feature.direction.impl.presentation.state.contentState
import org.yarokovisty.delivery.feature.direction.impl.presentation.state.errorState
import org.yarokovisty.delivery.feature.direction.impl.presentation.state.loadingState

internal class DirectionViewModel(
    private val directionRepository: DirectionRepository,
    private val router: DirectionRouter,
    directionType: DirectionType
) : BaseViewModel<DirectionState, DirectionIntent, Nothing>(
    DirectionState.initial(directionType)
) {

    private companion object {

        const val DELIVERY_POINT_PUBLISH_KEY = "deliveryPoint"
    }

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
            val deliveryPoints = directionRepository.getDeliveryPoints()
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
            publishResult(DELIVERY_POINT_PUBLISH_KEY, point)
        }
        router.back()
    }
}
