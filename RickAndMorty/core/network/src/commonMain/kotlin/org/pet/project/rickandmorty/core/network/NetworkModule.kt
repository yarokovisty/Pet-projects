package org.pet.project.rickandmorty.core.network

import org.koin.dsl.module

val networkModule = module {
    single { json }
    single { client }
}
