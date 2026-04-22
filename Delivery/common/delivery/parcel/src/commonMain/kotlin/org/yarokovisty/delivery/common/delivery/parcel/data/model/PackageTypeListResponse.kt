package org.yarokovisty.delivery.common.delivery.parcel.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PackageTypeListResponse(
    @SerialName("packages")
    val packages: List<PackageTypeResponse>
)
