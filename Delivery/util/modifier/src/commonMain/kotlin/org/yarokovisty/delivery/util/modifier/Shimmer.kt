package org.yarokovisty.delivery.util.modifier

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import com.valentinilk.shimmer.shimmer

@Composable
fun Modifier.shimmerable(
    enabled: Boolean = true,
    color: Color = Color.Unspecified,
    shape: Shape = RectangleShape,
): Modifier {
    if (!enabled) {
        return this
    }

    return this
        .shimmer()
        .background(color, shape)
        .drawWithContent {
            // Do not draw the actual content.
        }
}
