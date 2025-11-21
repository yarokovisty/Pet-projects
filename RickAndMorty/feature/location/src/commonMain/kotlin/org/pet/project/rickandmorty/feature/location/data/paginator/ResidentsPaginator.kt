package org.pet.project.rickandmorty.feature.location.data.paginator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext
import org.pet.project.rickandmorty.feature.location.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.data.model.ResidentResponse
import org.pet.project.rickandmorty.library.result.Result

internal class ResidentsPaginator(
	private val remoteLocationDataSource: RemoteLocationDataSource
) {

	private var urls: List<String> = emptyList()
	private var startIndex = 0

	private val _residentsFlow: MutableSharedFlow<RequestResidentState> = MutableSharedFlow()
	val residentsFlow: Flow<RequestResidentState> = _residentsFlow

	fun setResidentsUrls(urls: List<String>) {
		this.urls = urls
	}

	// TODO сделать потокобезопасным
	suspend fun loadNextResidents() {
		if (urls.isEmpty()) {
			_residentsFlow.emit(RequestResidentState.Error)
			return
		}

		val totalSize = urls.size

		if (startIndex >= totalSize) {
			_residentsFlow.emit(RequestResidentState.Ended)
			return
		}

		_residentsFlow.emit(RequestResidentState.Loading)

		val endIndex = getEndIndex(totalSize)
		val pageUrls = urls.subList(startIndex, endIndex)

		val residents = fetchResidents(pageUrls)
		val reached = endIndex >= totalSize

		_residentsFlow.emit(
			RequestResidentState.Success(residents, reached)
		)
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

