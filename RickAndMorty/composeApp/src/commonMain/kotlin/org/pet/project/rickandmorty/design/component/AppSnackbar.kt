package org.pet.project.rickandmorty.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.AppSnackbar(
	hostState: SnackbarHostState,
	action: @Composable (() -> Unit)? = null,
	dismissAction: @Composable (() -> Unit)? = null,
) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(80.dp)
			.align(Alignment.BottomCenter)
	) {
		SnackbarHost(
			hostState = hostState,
			modifier = Modifier
				.align(Alignment.Center)
				.padding(horizontal = 16.dp),
			snackbar = { data ->
				Snackbar(
					action = action,
					dismissAction = dismissAction
				) {
					Text(data.visuals.message)
				}
			}
		)
	}
}