package org.pet.project.rickandmorty.feature.character.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.character.data.datasource.RemoteCharacterDataSource
import org.pet.project.rickandmorty.feature.character.data.datasource.RemoteCharacterDataSourceImpl
import org.pet.project.rickandmorty.feature.character.data.paginator.CharacterPaginator
import org.pet.project.rickandmorty.feature.character.data.repository.CharacterRepositoryImpl
import org.pet.project.rickandmorty.feature.character.domain.repository.CharacterRepository
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterItemViewModel
import org.pet.project.rickandmorty.feature.character.presentation.viewmodel.CharacterListViewModel

val characterModule = module {
    // data
    factory { RemoteCharacterDataSourceImpl(get(), get()) } bind RemoteCharacterDataSource::class
    factory { CharacterPaginator(get()) }

    // domain
    factory {  CharacterRepositoryImpl(get(), get()) } bind CharacterRepository::class

    // presentation
    viewModelOf(::CharacterListViewModel)
    viewModelOf(::CharacterItemViewModel)
}