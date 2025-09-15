package org.pet.project.rickandmorty.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.pet.project.rickandmorty.core.di.networkModule
import org.pet.project.rickandmorty.feature.character.di.characterModule

fun initKoin(config : KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(
            networkModule,
            characterModule
        )
    }
}