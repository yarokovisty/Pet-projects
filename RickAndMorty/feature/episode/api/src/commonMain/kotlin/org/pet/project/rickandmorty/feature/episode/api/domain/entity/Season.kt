package org.pet.project.rickandmorty.feature.episode.api.domain.entity

class Season(
    val num: Int,
    val episodes: List<Episode>,
    val amountEpisodes: Int
)