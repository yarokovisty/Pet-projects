package org.pet.project.rickandmorty.feature.character.api.domain.entity

import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import rickandmorty.design.resources.generated.resources.ic_gender_female
import rickandmorty.design.resources.generated.resources.ic_gender_male
import rickandmorty.design.resources.generated.resources.ic_gender_unknown
import rickandmorty.feature.character.api.generated.resources.Res
import rickandmorty.feature.character.api.generated.resources.character_gender_female
import rickandmorty.feature.character.api.generated.resources.character_gender_genderless
import rickandmorty.feature.character.api.generated.resources.character_gender_male
import rickandmorty.feature.character.api.generated.resources.character_gender_unknown
import rickandmorty.design.resources.generated.resources.Res as R

enum class Gender(
    val value: StringResource,
    val icon: Pair<DrawableResource, Color>,
) : CharacterFilter {
    MALE(
        value = Res.string.character_gender_male,
        icon = Pair(R.drawable.ic_gender_male, Color.Cyan)
    ),
    FEMALE(
        value = Res.string.character_gender_female,
        icon = Pair(R.drawable.ic_gender_female, Color.Magenta)
    ),
    GENDERLESS(
        value = Res.string.character_gender_genderless,
        icon = Pair(R.drawable.ic_gender_unknown, Color.Black)
    ),
    UNKNOWN(
        value = Res.string.character_gender_unknown,
        icon = Pair(R.drawable.ic_gender_unknown, Color.Black)
    );
}