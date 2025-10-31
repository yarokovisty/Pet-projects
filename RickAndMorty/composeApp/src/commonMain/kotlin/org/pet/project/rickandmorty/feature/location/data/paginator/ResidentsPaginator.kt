package org.pet.project.rickandmorty.feature.location.data.paginator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.feature.location.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.data.model.ResidentResponse

internal class ResidentsPaginator(
	private val remoteLocationDataSource: RemoteLocationDataSource
) {

	private var residentUrls: List<String>? = null
	private var startIndex = 0

	private val _residentsFlow: MutableSharedFlow<List<Result<ResidentResponse>>> = MutableSharedFlow()
	val residentsFlow: Flow<List<Result<ResidentResponse>>> = _residentsFlow

	fun setResidentsUrls(urls: List<String>) {
		residentUrls = urls
	}

	suspend fun loadNextResidents() {
		val urls = residentUrls ?: return
		val totalSize = urls.size

		if (startIndex >= totalSize) {
			_residentsFlow.emit(emptyList())
			return
		}

		val endIndex = getEndIndex(totalSize)
		val pageUrls = urls.subList(startIndex, endIndex)
		val residents = fetchResidents(pageUrls)

		_residentsFlow.emit(residents)
		startIndex = endIndex
	}

	private fun getEndIndex(listSize: Int): Int = (startIndex + PAGE_SIZE).coerceAtMost(listSize)

	private suspend fun fetchResident(id: Int): Result<ResidentResponse> {
		return remoteLocationDataSource.getResidents(id)
	}

	private suspend fun fetchResidents(urls: List<String>): List<Result<ResidentResponse>> =
		withContext(Dispatchers.IO) {
			urls.map { url ->
				async { fetchResident(url.fetchCharacterId()) }
			}.awaitAll()
		}

	private companion object {

		const val PAGE_SIZE = 20
	}
}

private fun String.fetchCharacterId(): Int = substringAfterLast("/").toInt()
