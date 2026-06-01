package org.yarokovisty.delivery.common.delivery.parcel.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.common.delivery.parcel.data.datasource.DeliveryLocalDataSource
import org.yarokovisty.delivery.common.delivery.parcel.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.delivery.common.delivery.parcel.data.repository.ParcelRepositoryImpl
import org.yarokovisty.delivery.common.delivery.parcel.domain.repository.ParcelRepository
import org.yarokovisty.delivery.core.network.di.defaultHttpClientQualifier

val deliveryParcelModule = module {
    factoryOf(::DeliveryLocalDataSource)
    factory { DeliveryRemoteDataSource(httpClient = get(defaultHttpClientQualifier)) }
    factoryOf(::ParcelRepositoryImpl) bind ParcelRepository::class
}
