package org.yarokovisty.delivery.feature.delivery.main.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.ic_arrow_drop_down
import delivery.design.resources.generated.resources.ic_email
import delivery.design.resources.generated.resources.ic_marker
import delivery.design.resources.generated.resources.ic_pointer
import delivery.feature.delivery.main.generated.resources.Res
import delivery.feature.delivery.main.generated.resources.calculator_card_button_calculate
import delivery.feature.delivery.main.generated.resources.calculator_card_package_size_default_item
import delivery.feature.delivery.main.generated.resources.calculator_card_package_size_title
import delivery.feature.delivery.main.generated.resources.calculator_card_point_default_item
import delivery.feature.delivery.main.generated.resources.calculator_card_point_from_title
import delivery.feature.delivery.main.generated.resources.calculator_card_point_to_title
import delivery.feature.delivery.main.generated.resources.calculator_card_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.selector.SelectCategory
import org.yarokovisty.delivery.design.uikit.text.TitleH2
import org.yarokovisty.delivery.feature.delivery.main.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.presentation.state.DeliveryCalculatorContent
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun DeliveryCalculatorCard(
    state: DeliveryCalculatorContent,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    ContentCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Title()

            PointFromSelector(state, onIntent)

            PointToSelector(state, onIntent)

            PackageTypeSelector(state, onIntent)

            CalculateButton(
                enabled = state.calculateButtonEnabled,
                onClick = { onIntent(DeliveryMainIntent.CalculateDelivery) }
            )
        }
    }
}

@Composable
private fun ColumnScope.Title() {
    TitleH2(
        text = stringResource(Res.string.calculator_card_title),
        color = DeliveryTheme.colorScheme.textPrimary,
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )
}

@Composable
private fun PointFromSelector(
    state: DeliveryCalculatorContent,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    SelectCategory(
        label = stringResource(Res.string.calculator_card_point_from_title),
        text = state.selectedPointFrom?.name ?: "",
        defaultText = stringResource(Res.string.calculator_card_point_default_item),
        startIcon = painterResource(DrawableRes.drawable.ic_marker),
        endIcon = painterResource(DrawableRes.drawable.ic_arrow_drop_down),
        onClick = { onIntent(DeliveryMainIntent.SelectDeliveryPointFrom) },
        alternatives = state.alternativePointsFrom,
        onClickAlternative = { onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointFrom(it)) }
    )
}

@Composable
private fun PointToSelector(
    state: DeliveryCalculatorContent,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    SelectCategory(
        label = stringResource(Res.string.calculator_card_point_to_title),
        text = state.selectedPointTo?.name ?: "",
        defaultText = stringResource(Res.string.calculator_card_point_default_item),
        startIcon = painterResource(DrawableRes.drawable.ic_pointer),
        endIcon = painterResource(DrawableRes.drawable.ic_arrow_drop_down),
        onClick = { onIntent(DeliveryMainIntent.SelectDeliveryPointTo) },
        alternatives = state.alternativePointsTo,
        onClickAlternative = { onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointTo(it)) }
    )
}

@Composable
private fun PackageTypeSelector(
    state: DeliveryCalculatorContent,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    SelectCategory(
        label = stringResource(Res.string.calculator_card_package_size_title),
        text = state.selectedParcelInfo?.name ?: "",
        defaultText = stringResource(Res.string.calculator_card_package_size_default_item),
        startIcon = painterResource(DrawableRes.drawable.ic_email),
        endIcon = painterResource(DrawableRes.drawable.ic_arrow_drop_down),
        onClick = { onIntent(DeliveryMainIntent.OpenParcelTypeScreen) }
    )
}

@Composable
private fun CalculateButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    PrimaryButton(
        text = stringResource(Res.string.calculator_card_button_calculate),
        enabled = enabled,
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
    )
}
