package org.yarokovisty.delivery.util.flow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Suppress("ComposableNaming")
@Composable
infix fun <T> Flow<T>.observe(
    action: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(Unit) {
        collect { action(it) }
    }
}
