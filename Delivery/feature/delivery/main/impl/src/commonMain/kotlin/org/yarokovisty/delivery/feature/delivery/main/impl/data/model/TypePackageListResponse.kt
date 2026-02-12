package org.yarokovisty.delivery.feature.delivery.main.impl.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TypePackageListResponse(
    @SerialName("packages")
    val packages: List<TypePackageResponse>
)
