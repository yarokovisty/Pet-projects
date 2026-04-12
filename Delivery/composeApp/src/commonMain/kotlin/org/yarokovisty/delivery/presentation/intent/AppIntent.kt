package org.yarokovisty.delivery.presentation.intent

import org.yarokovisty.delivery.core.common.presentation.Intent

interface AppIntent : Intent {

    data object Back : AppIntent
}
