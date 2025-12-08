package org.pet.project.rickandmorty.feature.character.impl.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.character.api.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.impl.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.impl.data.datasource.RemoteCharacterDataSourceImpl
import org.pet.project.rickandmorty.feature.character.impl.data.paginator.CharacterPaginator
import org.pet.project.rickandmorty.feature.character.impl.data.repository.CharacterRepositoryImpl
import org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel.CharacterItemViewModel
import org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel.CharacterListViewModel
import org.pet.project.rickandmorty.feature.character.impl.presentation.viewmodel.CharacterSearchViewModel

val characterModule = module {
    // data
    factoryOf(::RemoteCharacterDataSourceImpl) bind RemoteCharacterDataSource::class
    factoryOf(::CharacterPaginator)

    // domain
    factoryOf(::CharacterRepositoryImpl) bind CharacterRepository::class


    // presentation
    viewModelOf(::CharacterListViewModel)
    viewModelOf(::CharacterItemViewModel)
    viewModelOf(::CharacterSearchViewModel)
}