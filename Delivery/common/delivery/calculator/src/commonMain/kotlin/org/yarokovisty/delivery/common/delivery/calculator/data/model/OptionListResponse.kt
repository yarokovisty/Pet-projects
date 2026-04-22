package org.yarokovisty.delivery.common.delivery.calculator.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class OptionListResponse(
    val options: List<OptionResponse>
)
