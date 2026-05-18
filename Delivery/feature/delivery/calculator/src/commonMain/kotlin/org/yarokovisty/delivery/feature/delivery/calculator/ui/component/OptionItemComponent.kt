package org.yarokovisty.delivery.feature.delivery.calculator.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.design.resources.generated.resources.ic_arrow_small_right
import delivery.design.resources.generated.resources.ic_bus
import delivery.design.resources.generated.resources.ic_plane
import delivery.feature.delivery.calculator.generated.resources.Res
import delivery.feature.delivery.calculator.generated.resources.calculator_default_delivery_title
import delivery.feature.delivery.calculator.generated.resources.calculator_delivery_cost
import delivery.feature.delivery.calculator.generated.resources.calculator_delviery_one_work_day
import delivery.feature.delivery.calculator.generated.resources.calculator_delviery_some_work_days
import delivery.feature.delivery.calculator.generated.resources.calculator_express_delivery_title
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.HorizontalGap
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.lines.TwoLine
import org.yarokovisty.delivery.design.uikit.text.Paragraph12Regular
import delivery.design.resources.generated.resources.Res as DrawableRes

@Composable
internal fun OptionItemComponent(
    option: Option,
    onClick: () -> Unit
) {
    OptionCard(onClick = onClick) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Description(option)

            VerticalGap(24.dp)

            CountWorkDays(option.days)
        }
    }
}

@Composable
private fun Description(option: Option) {
    TwoLine(
        title = getTitle(option.type),
        titleStyle = DeliveryTheme.typography.paragraph12Regular,
        titleColor = DeliveryTheme.colorScheme.textTertiary,
        subtitle = stringResource(Res.string.calculator_delivery_cost, option.price),
        subtitleStyle = DeliveryTheme.typography.titleH3,
        subtitleColor = DeliveryTheme.colorScheme.textPrimary,
        startIcon = getIcon(option.type),
        endIcon = painterResource(DrawableRes.drawable.ic_arrow_small_right)
    )
}

@Composable
private fun getTitle(optionType: OptionType): String =
    when (optionType) {
        OptionType.DEFAULT -> stringResource(Res.string.calculator_default_delivery_title)
        OptionType.EXPRESS -> stringResource(Res.string.calculator_express_delivery_title)
    }

@Composable
private fun getIcon(optionType: OptionType): Painter =
    when (optionType) {
        OptionType.DEFAULT -> painterResource(DrawableRes.drawable.ic_bus)
        OptionType.EXPRESS -> painterResource(DrawableRes.drawable.ic_plane)
    }

@Composable
private fun CountWorkDays(days: Int) {
    Row(modifier = Modifier.fillMaxWidth()) {
        HorizontalGap(64.dp)

        Paragraph12Regular(
            text = getWorkDaysString(days),
            color = DeliveryTheme.colorScheme.textTertiary
        )
    }
}

private const val ONE_WORK_DAY = 1

@Composable
private fun getWorkDaysString(days: Int): String =
    if (days == ONE_WORK_DAY) {
        stringResource(Res.string.calculator_delviery_one_work_day, days)
    } else {
        stringResource(Res.string.calculator_delviery_some_work_days, days)
    }

@Preview
@Composable
private fun OptionItemComponentPreview() {
    val option = Option(
        id = "1",
        price = 1.0,
        days = 1,
        type = OptionType.DEFAULT
    )

    DeliveryTheme {
        Box(modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)) {
            OptionItemComponent(option, onClick = {})
        }
    }
}
