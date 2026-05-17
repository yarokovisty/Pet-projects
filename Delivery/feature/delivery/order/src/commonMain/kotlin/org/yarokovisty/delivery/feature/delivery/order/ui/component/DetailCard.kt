package org.yarokovisty.delivery.feature.delivery.order.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.order.generated.resources.Res
import delivery.feature.delivery.order.generated.resources.ic_edit
import org.jetbrains.compose.resources.painterResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.lines.TwoLine
import org.yarokovisty.delivery.design.uikit.text.Paragraph16Medium
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.DetailItem
import org.yarokovisty.delivery.feature.delivery.order.presentation.state.DetailType

@Composable
internal fun DetailCard(
    item: DetailItem,
    onEditClick: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = DeliveryTheme.colorScheme.bgSecondary,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Title(item.title, onClick = onEditClick)

                TwoLine(
                    title = item.subtitle1,
                    titleStyle = DeliveryTheme.typography.paragraph12Regular,
                    titleColor = DeliveryTheme.colorScheme.textTertiary,
                    subtitle = item.description1.ifEmpty { "—" },
                    subtitleStyle = DeliveryTheme.typography.paragraph16Regular,
                    subtitleColor = DeliveryTheme.colorScheme.textPrimary
                )

                TwoLine(
                    title = item.subtitle2,
                    titleStyle = DeliveryTheme.typography.paragraph12Regular,
                    titleColor = DeliveryTheme.colorScheme.textTertiary,
                    subtitle = item.description2.ifEmpty { "—" },
                    subtitleStyle = DeliveryTheme.typography.paragraph16Regular,
                    subtitleColor = DeliveryTheme.colorScheme.textPrimary
                )
            }
        }
    }
}

@Composable
private fun Title(
    title: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Paragraph16Medium(
            text = title,
            color = DeliveryTheme.colorScheme.textPrimary,
            modifier = Modifier.weight(1f),
        )

        Icon(
            painter = painterResource(Res.drawable.ic_edit),
            contentDescription = null,
            tint = DeliveryTheme.colorScheme.textQuaternary,
            modifier = Modifier.clickable(onClick = onClick)
        )
    }
}

@Preview
@Composable
private fun DetailCardPreview() {
    val item = DetailItem(
        type = DetailType.SENDER,
        title = "Title",
        subtitle1 = "Subtitle 1",
        description1 = "Description 1",
        subtitle2 = "Subtitle 2",
        description2 = "Description 2"
    )

    DeliveryTheme {
        Box(modifier = Modifier.background(DeliveryTheme.colorScheme.bgPrimary)) {
            DetailCard(item, onEditClick = {})
        }
    }
}
