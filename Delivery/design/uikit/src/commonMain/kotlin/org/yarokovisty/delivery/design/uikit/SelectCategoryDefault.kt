package org.yarokovisty.delivery.design.uikit

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.yarokovisty.delivery.design.theme.DeliveryTheme

@Composable
fun SelectCategoryDefault(
    text: String = "",
    defaultText: String = "",
    label: String = "",
    onClickContent: () -> Unit = {},
    alternatives: List<String> = emptyList(),
    onClickAlternative: (String) -> Unit = {},
    startIcon: Painter? = null,
    endIcon: Painter? = null,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Label(label)
        }

        Content(text, defaultText, startIcon, endIcon, onClickContent)

        if (alternatives.isNotEmpty()) {
            Alternatives(alternatives, onClickAlternative)
        }
    }
}

@Composable
private fun Label(text: String) {
    Paragraph14Medium(text, color = DeliveryTheme.colorScheme.textSecondary)

    VerticalGap(6.dp)
}

@Composable
private fun Content(
    text: String,
    defaultText: String,
    startIcon: Painter?,
    endIcon: Painter?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .border(width = 1.dp, color = DeliveryTheme.colorScheme.borderLight, shape = RoundedCornerShape(8.dp))
            .padding(start = 12.dp, top = 10.dp, end = 8.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (startIcon != null) {
            Icon(
                painter = startIcon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = DeliveryTheme.colorScheme.indicatorMedium
            )

            HorizontalGap(4.dp)
        }

        Box(modifier = Modifier.weight(1f)) {
            when {
                text.isNotEmpty() ->
                    Paragraph16Regular(text, color = DeliveryTheme.colorScheme.textSecondary)

                defaultText.isNotEmpty() ->
                    Paragraph16Regular(defaultText, color = DeliveryTheme.colorScheme.textTertiary)
            }
        }

        if (endIcon != null) {
            HorizontalGap(8.dp)

            Icon(
                painter = endIcon,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = DeliveryTheme.colorScheme.indicatorMedium
            )
        }
    }
}

@Composable
private fun Alternatives(
    alternatives: List<String>,
    onClick: (String) -> Unit
) {
    VerticalGap(4.dp)

    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(alternatives) { alternative ->
            Paragraph14Underline(
                text = alternative,
                color = DeliveryTheme.colorScheme.textTertiary,
                modifier = Modifier.clickable(onClick = { onClick(alternative) })
            )
        }
    }
}

@Preview
@Composable
private fun SelectCategoryDefaultPreview() {
    DeliveryTheme {
        SelectCategoryDefault(
            label = "Label",
            text = "Text",
            onClickContent = {},
            alternatives = listOf("Text1", "Text2", "Text3"),
        )
    }
}
