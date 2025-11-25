package org.pet.project.rickandmorty.common.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class InfoResponse(
    @SerialName("count")
    val count: Int,

    @SerialName("pages")
    val pages: Int,

    @SerialName("next")
    val next: String?,

    @SerialName("prev")
    val prev: String?
) {
    fun getNextPage(): Int? {
        val urlNextPage = next ?: return null
        return Regex("page=(\\d+)").find(urlNextPage)?.groupValues?.get(1)?.toIntOrNull()
    }

    fun getPrevPage(): Int? {
        val urlPrevPage = prev ?: return null
        return Regex("page=(\\d+)").find(urlPrevPage)?.groupValues?.get(1)?.toIntOrNull()
    }
}
