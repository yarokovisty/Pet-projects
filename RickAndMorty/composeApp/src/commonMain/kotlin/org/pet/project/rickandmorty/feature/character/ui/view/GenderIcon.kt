package org.pet.project.rickandmorty.feature.character.ui.view

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.pet.project.rickandmorty.feature.character.domain.entity.Gender
import rickandmorty.composeapp.generated.resources.Res
import rickandmorty.composeapp.generated.resources.ic_gender_female
import rickandmorty.composeapp.generated.resources.ic_gender_male
import rickandmorty.composeapp.generated.resources.ic_gender_unknown

@Composable
internal fun GenderIcon(gender: Gender) {
    val (icon, tint) = gender.toUi()

    Icon(
        painter = painterResource(icon),
        contentDescription = null,
        tint = tint
    )
}

private fun Gender.toUi(): Pair<DrawableResource, Color> {
    return when(this) {
        Gender.MALE -> Pair(Res.drawable.ic_gender_male, Color.Cyan)
        Gender.FEMALE -> Pair(Res.drawable.ic_gender_female, Color.Magenta)
        Gender.GENDERLESS, Gender.UNKNOWN -> Pair(Res.drawable.ic_gender_unknown, Color.Black)
    }
}