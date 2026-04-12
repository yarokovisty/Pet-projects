package org.yarokovisty.common.delivery.parcel.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.common.delivery.parcel.data.datasource.DeliveryRemoteDataSource
import org.yarokovisty.common.delivery.parcel.data.repository.ParcelRepositoryImpl
import org.yarokovisty.common.delivery.parcel.domain.repository.ParcelRepository

val deliveryParcelModule = module {
    factoryOf(::DeliveryRemoteDataSource)
    factoryOf(::ParcelRepositoryImpl) bind ParcelRepository::class
}