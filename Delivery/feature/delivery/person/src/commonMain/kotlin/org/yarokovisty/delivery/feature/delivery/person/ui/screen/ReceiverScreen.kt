package org.yarokovisty.delivery.feature.delivery.person.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import delivery.design.resources.generated.resources.ic_arrow_left
import delivery.feature.delivery.person.generated.resources.Res
import delivery.feature.delivery.person.generated.resources.receiver_topbar_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.person.navigation.PersonScreenType
import org.yarokovisty.delivery.feature.delivery.person.presentation.intent.PersonIntent
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.PersonState
import org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel.ReceiverViewModel
import org.yarokovisty.delivery.feature.delivery.person.ui.component.PersonContent
import org.yarokovisty.delivery.feature.delivery.person.ui.component.PersonLinearStepIndicator
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun ReceiverScreen(screenType: PersonScreenType) {
    val viewModel: ReceiverViewModel = koinViewModel { parametersOf(screenType) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    ReceiverScreen(state, viewModel::onIntent)
}

@Composable
private fun ReceiverScreen(
    state: PersonState,
    onIntent: (PersonIntent) -> Unit
) {
    FullScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            ReceiverTopBar(onBackClick = { onIntent(PersonIntent.Back) })

            PersonLinearStepIndicator(state.stepState)

            PersonContent(state.contentState, onIntent)
        }
    }
}

@Composable
private fun ReceiverTopBar(onBackClick: () -> Unit) {
    TopBar(
        title = stringResource(Res.string.receiver_topbar_title),
        navigationIcon = painterResource(DrawableRes.drawable.ic_arrow_left),
        onNavIconClick = onBackClick
    )
}
