package org.yarokovisty.delivery.feature.delivery.main.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.main.generated.resources.Res
import delivery.feature.delivery.main.generated.resources.calculator_card_button_calculate
import delivery.feature.delivery.main.generated.resources.calculator_card_title
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.button.PrimaryButton
import org.yarokovisty.delivery.design.uikit.selector.SelectCategory
import org.yarokovisty.delivery.design.uikit.text.TitleH2
import org.yarokovisty.delivery.util.modifier.shimmerable

@Composable
internal fun DeliveryCalculatorCardSkeleton() {
    ContentCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            TitleH2(
                text = stringResource(Res.string.calculator_card_title),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            SelectCategory(
                label = "Label",
                text = "Text",
                alternatives = listOf("Text1"),
                modifier = Modifier.shimmerable(
                    color = DeliveryTheme.colorScheme.bgSecondary,
                    shape = RoundedCornerShape(8.dp)
                )
            )

            SelectCategory(
                label = "Label",
                text = "Text",
                alternatives = listOf("Text1"),
                modifier = Modifier.shimmerable(
                    color = DeliveryTheme.colorScheme.bgSecondary,
                    shape = RoundedCornerShape(8.dp)
                )
            )

            SelectCategory(
                label = "Label",
                text = "Text",
                modifier = Modifier.shimmerable(
                    color = DeliveryTheme.colorScheme.bgSecondary,
                    shape = RoundedCornerShape(8.dp)
                )
            )

            PrimaryButton(
                text = stringResource(Res.string.calculator_card_button_calculate),
                enabled = false,
                onClick = { },
            )
        }
    }
}
