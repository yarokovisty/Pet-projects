package org.pet.project.rickandmorty.feature.location.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.pet.project.rickandmorty.design.component.AppToolbarNavBackIcon
import org.pet.project.rickandmorty.feature.location.domain.entity.Location
import org.pet.project.rickandmorty.feature.location.presentation.intent.LocationItemIntent

@Composable
internal fun LocationItemContentScreen(
    location: Location,
    onIntent: (LocationItemIntent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Toolbar(
            title = location.name,
            onBack = {  }
        )


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    title: String,
    onBack: () -> Unit
) {
    TopAppBar(
        title = { TitleToolbar(title) },
        navigationIcon = { AppToolbarNavBackIcon(onClick = onBack) }
    )
}

@Composable
private fun TitleToolbar(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Medium
    )
}
