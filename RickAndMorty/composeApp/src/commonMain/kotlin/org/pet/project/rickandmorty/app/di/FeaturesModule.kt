package org.pet.project.rickandmorty.app.di

import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.character.di.characterModule
import org.pet.project.rickandmorty.feature.location.di.locationModule

val featureModule = module {
    includes(
        characterModule,
        locationModule
    )
}