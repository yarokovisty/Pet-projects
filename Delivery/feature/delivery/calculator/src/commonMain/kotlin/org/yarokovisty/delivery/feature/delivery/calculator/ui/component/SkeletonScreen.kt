package org.yarokovisty.delivery.feature.delivery.calculator.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import delivery.feature.delivery.calculator.generated.resources.Res
import delivery.feature.delivery.calculator.generated.resources.ic_plane
import org.jetbrains.compose.resources.painterResource
import org.yarokovisty.delivery.design.theme.DeliveryTheme
import org.yarokovisty.delivery.design.uikit.VerticalGap
import org.yarokovisty.delivery.design.uikit.lines.TwoLine
import org.yarokovisty.delivery.design.uikit.text.Paragraph12Regular
import org.yarokovisty.delivery.util.modifier.shimmerable

private const val NUM_SKELETON_THRESHOLD = 2

@Composable
internal fun SkeletonScreen() {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp),
    ) {
        repeat(NUM_SKELETON_THRESHOLD) {
            OptionCard(
                modifier = Modifier.shimmerable(
                    color = DeliveryTheme.colorScheme.bgSecondary,
                    shape = RoundedCornerShape(24.dp)
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    TwoLine(
                        title = "",
                        titleStyle = DeliveryTheme.typography.paragraph12Regular,
                        subtitle = "",
                        subtitleStyle = DeliveryTheme.typography.titleH3,
                        startIcon = painterResource(Res.drawable.ic_plane),
                    )

                    VerticalGap(24.dp)

                    Paragraph12Regular("")
                }
            }
        }
    }
}
