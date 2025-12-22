package org.pet.project.rickandmorty.feature.location.api.domain.entity

class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val amountResidents: Int
)