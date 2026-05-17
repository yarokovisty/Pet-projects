package org.yarokovisty.delivery.feature.login.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import delivery.feature.login.generated.resources.Res
import delivery.feature.login.generated.resources.ic_close
import delivery.feature.login.generated.resources.login_error
import delivery.feature.login.generated.resources.login_topbar_title
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.feature.login.presentation.event.LoginEvent
import org.yarokovisty.delivery.feature.login.presentation.intent.LoginIntent
import org.yarokovisty.delivery.feature.login.presentation.state.LoginState
import org.yarokovisty.delivery.feature.login.presentation.viewmodel.LoginViewModel
import org.yarokovisty.delivery.feature.login.ui.component.LoginContent
import org.yarokovisty.delivery.util.flow.observe

@Composable
internal fun LoginScreen() {
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LoginScreen(state, viewModel.events, viewModel::onIntent)
}

@Composable
private fun LoginScreen(
    state: LoginState,
    events: Flow<LoginEvent>,
    onIntent: (LoginIntent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    NavigationBackHandler(
        state = rememberNavigationEventState(NavigationEventInfo.None),
        onBackCompleted = { onIntent(LoginIntent.Back) }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(Res.string.login_topbar_title),
                navigationIcon = painterResource(Res.drawable.ic_close),
                onClickNavIcon = { onIntent(LoginIntent.Back) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = DeliveryTheme.colorScheme.bgPrimary
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LoginContent(state, onIntent)
        }
    }

    events observe { event ->
        when (event) {
            LoginEvent.OtpRequestError -> {
                val message = getString(Res.string.login_error)
                snackbarHostState.showSnackbar(message)
            }
            LoginEvent.SigninError -> {
                val message = getString(Res.string.login_error)
                snackbarHostState.showSnackbar(message)
            }
        }
    }
}
