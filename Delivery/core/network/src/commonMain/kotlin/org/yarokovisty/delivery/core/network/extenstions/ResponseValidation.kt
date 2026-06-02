package org.yarokovisty.delivery.core.network.extenstions

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import org.yarokovisty.delivery.core.common.error.NetworkException

internal fun HttpClientConfig<*>.installResponseValidation() {
    HttpResponseValidator {
        validateResponse { response ->
            val status = response.status

            if (!status.isSuccess()) {
                throwError(status)
            }
        }
    }
}

private fun throwError(status: HttpStatusCode) {
    val exception = when (status) {
        HttpStatusCode.Unauthorized -> NetworkException.Unauthorized()
        else -> NetworkException.Unknown()
    }
    throw exception
}
