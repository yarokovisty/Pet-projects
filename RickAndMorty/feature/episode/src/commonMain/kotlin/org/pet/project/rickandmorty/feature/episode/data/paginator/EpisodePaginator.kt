package org.pet.project.rickandmorty.feature.episode.data.paginator

import org.pet.project.rickandmorty.common.data.Paginator
import org.pet.project.rickandmorty.feature.episode.data.datasource.EpisodeRemoteDataSource
import org.pet.project.rickandmorty.feature.episode.data.model.EpisodeListResponse
import org.pet.project.rickandmorty.library.result.Result

internal class EpisodePaginator(
	private val remoteDataSource: EpisodeRemoteDataSource
) : Paginator<Int, EpisodeListResponse>(1) {

	override suspend fun fetchData(page: Int): Result<EpisodeListResponse> {
		return remoteDataSource.getEpisodesByPage(page)
	}

	override fun getNextKey(currentKey: Int, result: EpisodeListResponse): Int {
		val nextKey = result.info.getNextPage() ?: (currentKey + 1)
		return nextKey
	}

	override fun checkEndReached(currentKey: Int, result: EpisodeListResponse): Boolean {
		val allPages = result.info.pages
		return currentKey > allPages
	}
}