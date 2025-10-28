package org.pet.project.rickandmorty.feature.location.data.mapper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.pet.project.rickandmorty.core.result.Result
import org.pet.project.rickandmorty.core.result.asSuccess
import org.pet.project.rickandmorty.core.result.isSuccess
import org.pet.project.rickandmorty.core.result.map
import org.pet.project.rickandmorty.feature.character.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.domain.entity.Character
import org.pet.project.rickandmorty.feature.location.data.model.LocationResponse
import org.pet.project.rickandmorty.feature.location.data.model.toItem
import org.pet.project.rickandmorty.feature.character.data.model.toItem
import org.pet.project.rickandmorty.feature.location.domain.entity.Location

internal class LocationMapper(
    val remoteCharacterDataSource: RemoteCharacterDataSource
) {

    suspend fun fromResponseToItem(response: LocationResponse): Location =
        withContext(Dispatchers.IO) {
            coroutineScope {
                val charactersDeferred = response.characters.map { url ->
                    async { fetchCharacter(url) }
                }

                val characters = charactersDeferred.awaitAll().asSequence()
                    .filter { characterResponse -> characterResponse.isSuccess() }
                    .map { characterResponse -> characterResponse.asSuccess().value }
                    .toList()

                response.toItem(characters)
            }
        }

    private suspend fun fetchCharacter(characterUrl: String): Result<Character> {
        return remoteCharacterDataSource
            .getCharacter(characterUrl.fetchCharacterId())
            .map { it.toItem() }
    }
}

private fun String.fetchCharacterId(): Int = substringAfterLast("/").toInt()
