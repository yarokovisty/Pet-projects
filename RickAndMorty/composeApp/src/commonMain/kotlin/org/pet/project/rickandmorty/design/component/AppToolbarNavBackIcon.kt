package org.pet.project.rickandmorty.design.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.painterResource
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.ic_arrow_back

@Composable
fun AppToolbarNavBackIcon(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_arrow_back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )
    }
}