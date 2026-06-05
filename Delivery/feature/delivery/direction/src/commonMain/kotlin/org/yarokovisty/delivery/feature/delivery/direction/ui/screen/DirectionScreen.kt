package org.yarokovisty.delivery.feature.delivery.direction.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigationevent.NavigationEventInfo
import androidx.navigationevent.compose.NavigationBackHandler
import androidx.navigationevent.compose.rememberNavigationEventState
import delivery.design.resources.generated.resources.ic_close
import delivery.feature.delivery.direction.generated.resources.Res
import delivery.feature.delivery.direction.generated.resources.topbar_title_from
import delivery.feature.delivery.direction.generated.resources.topbar_title_to
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullLoadingScreen
import org.yarokovisty.delivery.feature.delivery.direction.presentation.intent.DirectionIntent
import org.yarokovisty.delivery.feature.delivery.direction.presentation.state.DirectionState
import org.yarokovisty.delivery.feature.delivery.direction.presentation.viewmodel.DirectionViewModel
import org.yarokovisty.delivery.feature.delivery.direction.ui.component.ContentScreen
import org.yarokovisty.delivery.feature.delivery.direction.ui.component.FailureScreen
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun DirectionScreen(directionType: DirectionType) {
    val viewModel = koinViewModel<DirectionViewModel> { parametersOf(directionType) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    DirectionScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
}

@Composable
private fun DirectionScreen(
    state: DirectionState,
    onIntent: (DirectionIntent) -> Unit
) {
    NavigationBackHandler(
        state = rememberNavigationEventState(NavigationEventInfo.None),
        onBackCompleted = { onIntent(DirectionIntent.Back) }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = getTitle(state.directionType),
                navigationIcon = painterResource(DrawableRes.drawable.ic_close),
                onNavIconClick = { onIntent(DirectionIntent.Back) }
            )
        },
        containerColor = DeliveryTheme.colorScheme.bgPrimary
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                state.loading -> FullLoadingScreen()
                state.error -> FailureScreen(onIntent)
                state.content != null -> ContentScreen(state.content, onIntent)
            }
        }
    }
}

@Composable
private fun getTitle(directionType: DirectionType): String =
    when (directionType) {
        DirectionType.FROM -> stringResource(Res.string.topbar_title_from)
        DirectionType.TO -> stringResource(Res.string.topbar_title_to)
    }
