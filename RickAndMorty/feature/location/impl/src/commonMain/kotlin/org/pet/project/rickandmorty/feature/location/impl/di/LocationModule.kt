package org.pet.project.rickandmorty.feature.location.impl.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.location.api.domain.repository.LocationRepository
import org.pet.project.rickandmorty.feature.location.api.domain.usecase.LoadNextResidentsUseCase
import org.pet.project.rickandmorty.feature.location.impl.data.datasource.RemoteLocationDataSource
import org.pet.project.rickandmorty.feature.location.impl.data.datasource.RemoteLocationDataSourceImpl
import org.pet.project.rickandmorty.feature.location.impl.data.paginator.ResidentsPaginator
import org.pet.project.rickandmorty.feature.location.impl.data.repository.LocationRepositoryImpl
import org.pet.project.rickandmorty.feature.location.impl.presentation.viewmodel.LocationItemViewModel

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