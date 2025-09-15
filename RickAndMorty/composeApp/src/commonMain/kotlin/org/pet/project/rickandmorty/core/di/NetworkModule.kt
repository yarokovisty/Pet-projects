package org.pet.project.rickandmorty.core.di

import org.koin.dsl.module
import org.pet.project.rickandmorty.core.networking.client
import org.pet.project.rickandmorty.core.networking.json

val networkModule = module {
    single { json }
    single { client }
}
