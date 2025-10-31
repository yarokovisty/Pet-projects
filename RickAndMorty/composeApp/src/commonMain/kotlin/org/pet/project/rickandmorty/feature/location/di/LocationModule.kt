package org.pet.project.rickandmorty.feature.location.di

import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.location.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.data.datasource.RemoteLocationDataSourceImpl
import org.pet.project.rickandmorty.feature.location.data.mapper.LocationMapper
import org.pet.project.rickandmorty.feature.location.data.repository.LocationRepositoryImpl
import org.pet.project.rickandmorty.feature.location.domain.repository.LocationRepository
import org.pet.project.rickandmorty.feature.location.presentation.viewmodel.LocationItemViewModel

val locationModule = module {
    // data
    singleOf(::RemoteLocationDataSourceImpl) bind RemoteLocationDataSource::class
    factory { LocationMapper(get()) }

    // domain
    singleOf(::LocationRepositoryImpl) bind LocationRepository::class

    // presentation
    viewModelOf(::LocationItemViewModel)
}