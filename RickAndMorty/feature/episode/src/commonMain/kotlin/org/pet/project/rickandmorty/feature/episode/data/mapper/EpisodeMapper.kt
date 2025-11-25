package org.pet.project.rickandmorty.feature.episode.data.mapper

import org.pet.project.rickandmorty.feature.episode.data.model.EpisodeResponse
import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode
import org.pet.project.rickandmorty.feature.episode.domain.entity.Season

internal fun List<EpisodeResponse>.toSeasons(): List<Season> {
    return this
        .map(EpisodeResponse::toItem)
        .groupBy { it.season }
        .map { (seasonNum, episodes) ->
            Season(
                num = seasonNum,
                episodes = episodes,
                amountEpisodes = episodes.size
            )
        }
}

internal fun EpisodeResponse.toItem(): Episode {
    val (season, seria) = episode.parseSeasonEpisode()
    return Episode(
        id = this.id,
        name = this.name,
        season = season,
        seria = seria,
        airDate = airDate
    )
}

private fun String.parseSeasonEpisode(): Pair<Int, Int> {
    val regex = Regex("""S(\d+)E(\d+)""", RegexOption.IGNORE_CASE)
    val match = regex.find(this) ?: error("Invalid format: expected 'S##E##', got '$this'")
    val (season, episode) = match.destructured
    return Pair(season.toInt(), episode.toInt())
}