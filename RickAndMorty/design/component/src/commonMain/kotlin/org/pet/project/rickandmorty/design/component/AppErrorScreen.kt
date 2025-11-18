package org.pet.project.rickandmorty.design.component

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
import rickandmorty.design.component.generated.resources.Res
import rickandmorty.design.component.generated.resources.error_text
import rickandmorty.design.component.generated.resources.error_text_button_refresh
import rickandmorty.design.resources.generated.resources.pickle_rick
import rickandmorty.design.resources.generated.resources.Res as R

@Composable
internal fun AppErrorScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonText: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(R.drawable.pickle_rick),
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
            onClick = onClick
        ) {
            val text = buttonText ?: stringResource(Res.string.error_text_button_refresh)
            Text(text)

        }

    }
}

