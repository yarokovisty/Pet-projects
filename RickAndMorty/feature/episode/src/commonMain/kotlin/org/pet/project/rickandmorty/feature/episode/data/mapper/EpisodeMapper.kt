package org.pet.project.rickandmorty.feature.episode.data.mapper

import org.pet.project.rickandmorty.common.data.PaginatorState
import org.pet.project.rickandmorty.feature.episode.data.model.EpisodeListResponse
import org.pet.project.rickandmorty.feature.episode.data.model.EpisodeResponse
import org.pet.project.rickandmorty.feature.episode.domain.entity.Episode
import org.pet.project.rickandmorty.feature.episode.domain.entity.EpisodeState
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

internal fun EpisodeListResponse.toItem(): Map<Int, List<Episode>> {
    return results.map(EpisodeResponse::toItem).groupBy { it.season }
}

internal fun PaginatorState<EpisodeListResponse>.toItem(): EpisodeState {
    return when(this) {
        PaginatorState.End -> EpisodeState.End
        is PaginatorState.Error -> EpisodeState.Error(this.throwable)
        PaginatorState.Initial -> EpisodeState.Initial
        PaginatorState.Loading -> EpisodeState.Loading
        is PaginatorState.Success<EpisodeListResponse> -> {
            val seasons = this.value.toItem()
            EpisodeState.Success(seasons)
        }
    }
}