package org.yarokovisty.delivery.core.network.util

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
internal actual val isDebug: Boolean =
    Platform.isDebugBinary
