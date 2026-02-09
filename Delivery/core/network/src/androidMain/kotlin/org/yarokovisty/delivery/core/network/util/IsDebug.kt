package org.yarokovisty.delivery.core.network.util

internal actual val isDebug: Boolean =
    android.os.Build.TYPE != android.os.Build.USER
