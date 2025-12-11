package org.pet.project.rickandmorty.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import org.pet.project.rickandmorty.common.presentation.Event

@Suppress("ComposableNaming")
@Composable
infix fun <T : Event> Flow<T>.observe(
    action: suspend CoroutineScope.(T) -> Unit
) {
    LaunchedEffect(Unit) {
        collect { action(it) }
    }
}