package org.yarokovisty.delivery.presentation.viewmodel

import org.yarokovisty.delivery.common.presentation.BaseViewModel
import org.yarokovisty.delivery.presentation.intent.AppIntent
import org.yarokovisty.delivery.presentation.router.AppRouter
import org.yarokovisty.delivery.presentation.state.AppState

class AppViewModel(
    private val router: AppRouter
) : BaseViewModel<AppState, AppIntent, Nothing>() {

    override fun initState(): AppState =
        AppState.INITIAL

    override fun onIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.Back -> back()
        }
    }

    private fun back() {
        router.back()
        updateBackStack()
    }

    private fun updateBackStack() {
        val backStack = router.globalBackStack.backStack.toList()
        updateState { copy(backStack = backStack) }
    }
}
