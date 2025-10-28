package org.pet.project.rickandmorty.feature.location.domain.entity

import org.pet.project.rickandmorty.feature.character.domain.entity.Character

class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val characters: List<Character>
)