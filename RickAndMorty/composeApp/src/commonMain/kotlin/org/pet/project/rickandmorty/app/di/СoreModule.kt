package org.pet.project.rickandmorty.app.di

import org.koin.dsl.module
import org.pet.project.rickandmorty.core.network.networkModule

val coreModule = module {
    includes(networkModule)
}