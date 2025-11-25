package org.pet.project.rickandmorty.feature.location.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.location.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.data.datasource.RemoteLocationDataSourceImpl
import org.pet.project.rickandmorty.feature.location.data.paginator.ResidentsPaginator
import org.pet.project.rickandmorty.feature.location.data.repository.LocationRepositoryImpl
import org.pet.project.rickandmorty.feature.location.domain.repository.LocationRepository
import org.pet.project.rickandmorty.feature.location.domain.usecase.LoadNextResidentsUseCase
import org.pet.project.rickandmorty.feature.location.presentation.viewmodel.LocationItemViewModel

val locationModule = module {
    // data
    factoryOf(::RemoteLocationDataSourceImpl) bind RemoteLocationDataSource::class
    factoryOf(::ResidentsPaginator)

    // domain
    singleOf(::LocationRepositoryImpl) bind LocationRepository::class
    factoryOf(::LoadNextResidentsUseCase)

    // presentation
    viewModelOf(::LocationItemViewModel)
}