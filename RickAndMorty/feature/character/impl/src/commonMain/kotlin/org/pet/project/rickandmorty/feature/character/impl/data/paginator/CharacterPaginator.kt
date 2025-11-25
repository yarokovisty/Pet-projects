package org.pet.project.rickandmorty.feature.character.impl.data.paginator

import org.pet.project.rickandmorty.common.data.Paginator
import org.pet.project.rickandmorty.feature.character.impl.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.impl.data.model.CharacterListResponse
import org.pet.project.rickandmorty.library.result.Result

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