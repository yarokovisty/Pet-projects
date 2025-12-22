package org.pet.project.rickandmorty.app.di

import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.character.impl.di.characterModule
import org.pet.project.rickandmorty.feature.episode.impl.di.episodeModule
import org.pet.project.rickandmorty.feature.location.impl.di.locationModule

val featureModule = module {
    includes(
        characterModule,
        episodeModule,
        locationModule
    )
}