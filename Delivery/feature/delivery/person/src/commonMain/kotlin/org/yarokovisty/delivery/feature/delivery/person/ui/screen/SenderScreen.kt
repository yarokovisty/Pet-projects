package org.yarokovisty.delivery.feature.delivery.person.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import delivery.design.resources.generated.resources.ic_arrow_left
import delivery.feature.delivery.person.generated.resources.Res
import delivery.feature.delivery.person.generated.resources.sender_topbar_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.yarokovisty.delivery.design.uikit.TopBar
import org.yarokovisty.delivery.design.uikit.screen.FullScreen
import org.yarokovisty.delivery.feature.delivery.person.navigation.PersonScreenType
import org.yarokovisty.delivery.feature.delivery.person.presentation.intent.PersonIntent
import org.yarokovisty.delivery.feature.delivery.person.presentation.state.PersonState
import org.yarokovisty.delivery.feature.delivery.person.presentation.viewmodel.SenderViewModel
import org.yarokovisty.delivery.feature.delivery.person.ui.component.PersonContent
import org.yarokovisty.delivery.feature.delivery.person.ui.component.PersonLinearStepIndicator
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun SenderScreen(screenType: PersonScreenType) {
    val viewModel: SenderViewModel = koinViewModel { parametersOf(screenType) }
    val state by viewModel.state.collectAsState()

    SenderScreen(state, viewModel::onIntent)
}

@Composable
private fun SenderScreen(
    state: PersonState,
    onIntent: (PersonIntent) -> Unit
) {
    FullScreen {
        Column(modifier = Modifier.fillMaxSize()) {
            SenderTopBar(onBackClick = { onIntent(PersonIntent.Back) })

            PersonLinearStepIndicator(state.stepState)

            PersonContent(state.contentState, onIntent)
        }
    }
}

@Composable
private fun SenderTopBar(onBackClick: () -> Unit) {
    TopBar(
        title = stringResource(Res.string.sender_topbar_title),
        navigationIcon = painterResource(DrawableRes.drawable.ic_arrow_left),
        onClickNavIcon = onBackClick
    )
}
