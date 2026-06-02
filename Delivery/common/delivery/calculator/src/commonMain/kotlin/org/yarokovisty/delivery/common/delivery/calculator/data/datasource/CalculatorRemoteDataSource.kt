package org.yarokovisty.delivery.common.delivery.calculator.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.delivery.common.delivery.calculator.data.model.CalculatorRequest
import org.yarokovisty.delivery.common.delivery.calculator.data.model.OptionListResponse
import org.yarokovisty.delivery.core.network.extenstions.post

internal class CalculatorRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getOptionList(request: CalculatorRequest): OptionListResponse =
        httpClient.post("/api/delivery/calc", request)
}
