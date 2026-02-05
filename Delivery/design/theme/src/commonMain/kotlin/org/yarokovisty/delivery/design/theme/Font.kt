package org.yarokovisty.delivery.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import delivery.design.theme.generated.resources.Res
import delivery.design.theme.generated.resources.inter
import org.jetbrains.compose.resources.Font

@Composable
fun Inter(): FontFamily =
    FontFamily(
        Font(resource = Res.font.inter, style = FontStyle.Normal)
    )
