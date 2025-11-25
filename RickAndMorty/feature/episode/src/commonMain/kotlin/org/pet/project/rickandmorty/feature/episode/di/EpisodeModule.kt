package org.pet.project.rickandmorty.feature.episode.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.episode.data.datasource.EpisodeRemoteDataSource
import org.pet.project.rickandmorty.feature.episode.data.datasource.EpisodeRemoteDataSourceImpl
import org.pet.project.rickandmorty.feature.episode.data.repository.EpisodeRepositoryImpl
import org.pet.project.rickandmorty.feature.episode.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.feature.episode.presentation.viewmodel.CharacterEpisodeViewModel

val episodeModule = module {
    // data
    factoryOf(::EpisodeRemoteDataSourceImpl) bind EpisodeRemoteDataSource::class

    // domain
    factoryOf(::EpisodeRepositoryImpl) bind EpisodeRepository::class

    // presentation
    viewModelOf(::CharacterEpisodeViewModel)
}