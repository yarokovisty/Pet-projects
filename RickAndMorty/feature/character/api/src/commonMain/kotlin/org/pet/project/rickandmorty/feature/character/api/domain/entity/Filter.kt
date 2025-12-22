package org.pet.project.rickandmorty.feature.character.api.domain.entity

data class Filter(
    val amount: Int,
    val name: String,
    val selected: Boolean,
    val filter: CharacterFilter,
)