package org.yarokovisty.common.delivery.calculator.data.datasource

import io.ktor.client.HttpClient
import org.yarokovisty.common.delivery.calculator.data.model.CalculatorRequest
import org.yarokovisty.common.delivery.calculator.data.model.OptionListResponse
import org.yarokovisty.delivery.core.network.client.post

internal class CalculatorRemoteDataSource(private val httpClient: HttpClient) {

    suspend fun getOptionList(request: CalculatorRequest): OptionListResponse =
        httpClient.post("/api/delivery/calc", request)
}
