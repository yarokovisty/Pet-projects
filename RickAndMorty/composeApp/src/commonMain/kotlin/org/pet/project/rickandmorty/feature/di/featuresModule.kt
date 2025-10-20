package org.pet.project.rickandmorty.feature.di

import org.koin.dsl.module
import org.pet.project.rickandmorty.feature.character.di.characterModule

val featureModule = module {
    includes(characterModule)
}