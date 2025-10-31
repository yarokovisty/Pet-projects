package org.pet.project.rickandmorty.app.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes
import org.pet.project.rickandmorty.core.di.coreModule
import org.pet.project.rickandmorty.feature.di.featureModule

fun initKoin(config : KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(
            coreModule, featureModule
        )
    }
}