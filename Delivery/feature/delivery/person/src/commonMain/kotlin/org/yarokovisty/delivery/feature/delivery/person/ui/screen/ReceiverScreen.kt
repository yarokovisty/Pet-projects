package org.yarokovisty.delivery.feature.delivery.person.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import delivery.feature.delivery.person.generated.resources.Res
import delivery.feature.delivery.person.generated.resources.ic_arrow_left
import delivery.feature.delivery.person.generated.resources.receiver_topbar_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.person.presentation.intent.PersonIntent
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.PersonState
import org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel.ReceiverViewModel
import org.yarokovisty.delivery.feature.delivery.person.ui.component.PersonContent
import org.yarokovisty.delivery.feature.delivery.person.ui.component.PersonLinearStepIndicator

@Composable
fun ReceiverScreen() {
    val viewModel: ReceiverViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    ReceiverScreen(
        state = state,
        onIntent = viewModel::onIntent
    )
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
        navigationIcon = painterResource(Res.drawable.ic_arrow_left),
        onClickNavIcon = onBackClick
    )
}
