package org.yarokovisty.delivery.presentation.viewmodel

import org.yarokovisty.delivery.core.common.presentation.BaseViewModel
import org.yarokovisty.delivery.presentation.intent.AppIntent
import org.yarokovisty.delivery.presentation.router.AppRouter
import org.yarokovisty.delivery.presentation.state.AppState

class AppViewModel(
    private val router: AppRouter
) : BaseViewModel<AppState, AppIntent, Nothing>(
    AppState.initial(router.globalBackStack.backStack)
) {

    override fun onIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.Back -> back()
        }
    }

    private fun back() {
        router.back()
    }
}
