package org.yarokovisty.delivery.presentation.intent

import org.yarokovisty.delivery.common.presentation.Intent
import org.yarokovisty.delivery.presentation.state.MainTab

sealed interface MainIntent : Intent {

    data class SwitchTab(val tab: MainTab) : MainIntent

    data object Back : MainIntent
}
