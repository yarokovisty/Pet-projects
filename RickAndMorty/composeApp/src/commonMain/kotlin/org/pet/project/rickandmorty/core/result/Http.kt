package org.pet.project.rickandmorty.core.result

class HttpException(
    val statusCode: Int,
    val statusMessage: String? = null,
    val url: String? = null,
    cause: Throwable? = null
) : Exception(null, cause)


interface IHttpResponse {

    val statusCode: Int

    val statusMessage: String?

    val url: String?
}
