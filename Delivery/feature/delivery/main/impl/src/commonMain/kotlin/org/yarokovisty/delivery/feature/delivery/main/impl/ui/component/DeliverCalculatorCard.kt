package org.yarokovisty.delivery.feature.delivery.main.impl.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.main.impl.generated.resources.Res
import delivery.feature.delivery.main.impl.generated.resources.calculator_card_button_calculate
import delivery.feature.delivery.main.impl.generated.resources.calculator_card_package_size_default_item
import delivery.feature.delivery.main.impl.generated.resources.calculator_card_package_size_title
import delivery.feature.delivery.main.impl.generated.resources.calculator_card_point_default_item
import delivery.feature.delivery.main.impl.generated.resources.calculator_card_point_from_title
import delivery.feature.delivery.main.impl.generated.resources.calculator_card_point_to_title
import delivery.feature.delivery.main.impl.generated.resources.calculator_card_title
import delivery.feature.delivery.main.impl.generated.resources.ic_arrow_drop_down
import delivery.feature.delivery.main.impl.generated.resources.ic_email
import delivery.feature.delivery.main.impl.generated.resources.ic_marker
import delivery.feature.delivery.main.impl.generated.resources.ic_pointer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.PrimaryButton
import org.yarokovisty.delivery.design.uikit.PrimaryCard
import org.yarokovisty.delivery.design.uikit.SelectCategoryDefault
import org.yarokovisty.delivery.design.uikit.TitleH2
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.intent.DeliveryMainIntent
import org.yarokovisty.delivery.feature.delivery.main.impl.presentation.state.DeliveryCalculatorContent
import org.yarokovisty.delivery.util.modifier.shimmerable

@Composable
internal fun DeliveryCalculatorCardSkeleton() {
    PrimaryCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            TitleH2(
                text = stringResource(Res.string.calculator_card_title),
                color = DeliveryTheme.colorScheme.textPrimary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            SelectCategoryDefault(
                label = "Label",
                text = "Text",
                alternatives = listOf("Text1"),
                modifier = Modifier.shimmerable(
                    color = DeliveryTheme.colorScheme.bgSecondary,
                    shape = RoundedCornerShape(8.dp)
                )
            )

            SelectCategoryDefault(
                label = "Label",
                text = "Text",
                alternatives = listOf("Text1"),
                modifier = Modifier.shimmerable(
                    color = DeliveryTheme.colorScheme.bgSecondary,
                    shape = RoundedCornerShape(8.dp)
                )
            )

            SelectCategoryDefault(
                label = "Label",
                text = "Text",
                modifier = Modifier.shimmerable(
                    color = DeliveryTheme.colorScheme.bgSecondary,
                    shape = RoundedCornerShape(8.dp)
                )
            )

            PrimaryButton(
                text = stringResource(Res.string.calculator_card_button_calculate),
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                onClick = { },
            )
        }
    }
}

@Composable
internal fun DeliveryCalculatorCard(
    state: DeliveryCalculatorContent,
    onIntent: (DeliveryMainIntent) -> Unit
) {
    PrimaryCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            TitleH2(
                text = stringResource(Res.string.calculator_card_title),
                color = DeliveryTheme.colorScheme.textPrimary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            SelectCategoryDefault(
                label = stringResource(Res.string.calculator_card_point_from_title),
                text = state.selectedPointFrom?.name ?: "",
                defaultText = stringResource(Res.string.calculator_card_point_default_item),
                startIcon = painterResource(Res.drawable.ic_pointer),
                endIcon = painterResource(Res.drawable.ic_arrow_drop_down),
                onClickContent = { onIntent(DeliveryMainIntent.SelectDeliveryPointFrom) },
                alternatives = state.alternativePointsFrom,
                onClickAlternative = { onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointFrom(it)) }
            )

            SelectCategoryDefault(
                label = stringResource(Res.string.calculator_card_point_to_title),
                text = state.selectedPointTo?.name ?: "",
                defaultText = stringResource(Res.string.calculator_card_point_default_item),
                startIcon = painterResource(Res.drawable.ic_marker),
                endIcon = painterResource(Res.drawable.ic_arrow_drop_down),
                onClickContent = { onIntent(DeliveryMainIntent.SelectDeliveryPointTo) },
                alternatives = state.alternativePointsTo,
                onClickAlternative = { onIntent(DeliveryMainIntent.SelectAlternativeDeliveryPointTo(it)) }
            )

            SelectCategoryDefault(
                label = stringResource(Res.string.calculator_card_package_size_title),
                text = state.selectedParcelType?.name ?: "",
                defaultText = stringResource(Res.string.calculator_card_package_size_default_item),
                startIcon = painterResource(Res.drawable.ic_email),
                endIcon = painterResource(Res.drawable.ic_arrow_drop_down),
                onClickContent = { onIntent(DeliveryMainIntent.SelectParcelType) }
            )

            PrimaryButton(
                text = stringResource(Res.string.calculator_card_button_calculate),
                modifier = Modifier.fillMaxWidth(),
                onClick = { onIntent(DeliveryMainIntent.CalculateDelivery) },
            )
        }
    }
}
