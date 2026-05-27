package org.yarokovisty.delivery.feature.delivery.order.presentation.viewmodel

import org.yarokovisty.delivery.common.auth.domain.usecase.IsUserAuthorizedUseCase
import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.feature.delivery.order.navigation.SuccessOrderRouter
import org.yarokovisty.delivery.feature.delivery.order.presentation.intent.SuccessOrderIntent
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.SuccessOrderState

internal class SuccessOrderViewModel(
    private val isUserAuthorizedUseCase: IsUserAuthorizedUseCase,
    private val router: SuccessOrderRouter,
) : BaseViewModel<SuccessOrderState, SuccessOrderIntent, Nothing>(SuccessOrderState) {

    override fun onIntent(intent: SuccessOrderIntent) {
        when (intent) {
            SuccessOrderIntent.Back -> back()
            SuccessOrderIntent.CheckStatus -> openNextScreen()
        }
    }

    private fun back() {
        router.backToMain()
    }

    private fun openNextScreen() {
        launch {
            if (isUserAuthorizedUseCase()) {
                router.openHistoryMainScreen()
            } else {
                router.openLoginScreen()
            }
        }
    }
}
