package org.pet.project.rickandmorty.core.di

import org.koin.dsl.module
import org.pet.project.rickandmorty.core.networking.networkModule

val coreModule = module {
    includes(networkModule)
}