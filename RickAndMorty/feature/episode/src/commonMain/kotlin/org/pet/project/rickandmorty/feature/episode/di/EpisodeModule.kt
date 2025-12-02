package org.pet.project.rickandmorty.feature.episode.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.episode.data.datasource.EpisodeRemoteDataSource
import org.pet.project.rickandmorty.feature.episode.data.datasource.EpisodeRemoteDataSourceImpl
import org.pet.project.rickandmorty.feature.episode.data.paginator.EpisodePaginator
import org.pet.project.rickandmorty.feature.episode.data.repository.EpisodeRepositoryImpl
import org.pet.project.rickandmorty.feature.episode.domain.repository.EpisodeRepository
import org.pet.project.rickandmorty.feature.episode.domain.usecase.GetEpisodesUseCase
import org.pet.project.rickandmorty.feature.episode.presentation.viewmodel.AllEpisodesViewModel
import org.pet.project.rickandmorty.feature.episode.presentation.viewmodel.CharacterEpisodeViewModel

val episodeModule = module {
    // data
    factoryOf(::EpisodeRemoteDataSourceImpl) bind EpisodeRemoteDataSource::class
    factoryOf(::EpisodePaginator)

    // domain
    singleOf(::EpisodeRepositoryImpl) bind EpisodeRepository::class
    factoryOf(::GetEpisodesUseCase)

    // presentation
    viewModelOf(::AllEpisodesViewModel)
    viewModelOf(::CharacterEpisodeViewModel)
}