package org.pet.project.rickandmorty.feature.character.domain.entity

data class Character(
    val id: Int,
    val name: String,
    val image: String,
    val gender: Gender,
    val status: Status,
    val species: String,
    val origin: String,
    val location: String
)
