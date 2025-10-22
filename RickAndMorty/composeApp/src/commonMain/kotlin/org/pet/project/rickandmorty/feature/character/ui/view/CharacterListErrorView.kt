package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.pet.project.rickandmorty.design.component.AppSpacer
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.error_text
import rickandmorty.composeapp.generated.resources.error_text_button_refresh
import rickandmorty.composeapp.generated.resources.pickle_rick

@Composable
internal fun CharacterListErrorView(
    onRefresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(Res.drawable.pickle_rick),
                null,
                modifier = Modifier.fillMaxWidth()
            )
            AppSpacer(height = 20.dp)
            Text(
                text = stringResource(Res.string.error_text),
                fontWeight = FontWeight.SemiBold
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onRefresh
        ) {
            Text(stringResource(Res.string.error_text_button_refresh))
        }
    }
}