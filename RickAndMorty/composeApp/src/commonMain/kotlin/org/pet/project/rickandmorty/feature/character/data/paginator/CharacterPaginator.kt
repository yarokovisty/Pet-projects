package org.pet.project.rickandmorty.feature.character.data.paginator

import org.pet.project.rickandmorty.common.utils.Result
import org.pet.project.rickandmorty.feature.character.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.data.model.CharacterListResponse
import org.pet.project.rickandmorty.feature.character.data.model.getNextPage
import org.pet.project.rickandmorty.common.data.Paginator

internal class CharacterPaginator(
    private val remoteDataSource: RemoteCharacterDataSource
) : Paginator<Int, CharacterListResponse>(1) {

    override suspend fun fetchData(page: Int): Result<CharacterListResponse> {
        return remoteDataSource.getCharactersPage(page)
    }

    override fun getNextKey(
        currentKey: Int,
        result: CharacterListResponse
    ): Int {
        val nextKey = result.info.getNextPage() ?: (currentKey + 1)
        return nextKey
    }

    override fun checkEndReached(
        currentKey: Int,
        result: CharacterListResponse
    ): Boolean {
        val allPages = result.info.pages
        return currentKey > allPages
    }
}