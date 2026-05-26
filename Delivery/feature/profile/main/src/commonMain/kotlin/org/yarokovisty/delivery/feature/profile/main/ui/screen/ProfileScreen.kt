package org.yarokovisty.delivery.feature.profile.main.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import delivery.design.resources.generated.resources.ic_exit
import delivery.feature.profile.main.generated.resources.Res
import delivery.feature.profile.main.generated.resources.profile_topbar_title
import delivery.feature.profile.main.generated.resources.user_update_data_error
import delivery.feature.profile.main.generated.resources.user_update_data_success
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.profile.main.presentation.event.ProfileEvent
import org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
import org.yarokovisty.delivery.feature.profile.main.presentation.state.ProfileState
import org.yarokovisty.delivery.feature.profile.main.presentation.viewmodel.ProfileViewModel
import org.yarokovisty.delivery.feature.profile.main.ui.component.FailureScreen
import org.yarokovisty.delivery.feature.profile.main.ui.component.LoadingScreen
import org.yarokovisty.delivery.feature.profile.main.ui.component.ProfileContent
import org.yarokovisty.delivery.feature.profile.main.ui.component.ProfileSnacbarHost
import org.yarokovisty.delivery.util.flow.observe
import delivery.design.resources.generated.resources.Res as designRes

@Composable
internal fun ProfileScreen() {
    val viewModel = koinViewModel<ProfileViewModel>()
    val state by viewModel.state.collectAsState()

    ProfileScreen(state, viewModel.events, viewModel::onIntent)
}

@Composable
private fun ProfileScreen(
    state: ProfileState,
    events: Flow<ProfileEvent>,
    onIntent: (ProfileIntent) -> Unit
) {
    val successSnackbarHostState = remember { SnackbarHostState() }
    val errorSnackbarHostState = remember { SnackbarHostState() }

    FullScreen(
        containerColor = DeliveryTheme.colorScheme.bgPrimary,
        paddingValues = WindowInsets.statusBars.asPaddingValues(),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ProfileTopBar(onExitClick = { onIntent(ProfileIntent.ShowLogoutScreen) })

            when {
                state.loading -> LoadingScreen()
                state.error -> FailureScreen(onIntent)
                state.content != null -> ProfileContent(state.content, onIntent)
            }
        }

        LogoutScreen(state.logoutScreenVisible, onIntent)

        ProfileSnacbarHost(
            successSnackbarHostState = successSnackbarHostState,
            errorSnackbarHostState = errorSnackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

    events observe { event ->
        when (event) {
            ProfileEvent.UpdateUserDataSuccess -> {
                val message = getString(Res.string.user_update_data_success)
                successSnackbarHostState.showSnackbar(message)
            }

            ProfileEvent.UpdateUserDataError -> {
                val message = getString(Res.string.user_update_data_error)
                errorSnackbarHostState.showSnackbar(message)
            }
        }
    }
}

@Composable
private fun ProfileTopBar(onExitClick: () -> Unit) {
    TopBar(
        title = stringResource(Res.string.profile_topbar_title),
        actions = {
            TopBarActions(onExitClick = onExitClick)
        }
    )
}

@Composable
private fun TopBarActions(onExitClick: () -> Unit) {
    IconButton(onClick = onExitClick) {
        Icon(
            painter = painterResource(designRes.drawable.ic_exit),
            contentDescription = null,
            tint = DeliveryTheme.colorScheme.brandIndicator
        )
    }
}
