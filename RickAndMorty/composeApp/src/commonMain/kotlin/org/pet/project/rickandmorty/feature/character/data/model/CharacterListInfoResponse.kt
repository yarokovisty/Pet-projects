package org.pet.project.rickandmorty.feature.character.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class CharacterListInfoResponse(
    @SerialName("count")
    val count: Int,

    @SerialName("pages")
    val pages: Int,

    @SerialName("next")
    val next: String?,

    @SerialName("prev")
    val prev: String?
)

internal fun CharacterListInfoResponse.getNextPage(): Int? {
    val urlNextPage = this.next ?: ""
    return Regex("page=(\\d+)")
        .find(urlNextPage)
        ?.groupValues?.get(1)
        ?.toIntOrNull()
}

internal fun CharacterListInfoResponse.getPrevPage(): Int? {
    val urlNextPage = this.prev ?: ""
    return Regex("page=(\\d+)")
        .find(urlNextPage)
        ?.groupValues?.get(1)
        ?.toIntOrNull()
}