package org.pet.project.rickandmorty.design.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AppTitleToolbar(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Medium
    )
}