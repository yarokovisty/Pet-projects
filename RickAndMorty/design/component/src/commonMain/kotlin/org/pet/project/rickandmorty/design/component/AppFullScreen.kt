package org.pet.project.rickandmorty.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pet.project.rickandmorty.util.edgeToEdgePadding

@Composable
fun AppFullScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .edgeToEdgePadding(),
        content = { content() }
    )
}