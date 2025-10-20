package org.pet.project.rickandmorty.core.networking

import org.koin.dsl.module

val networkModule = module {
    single { json }
    single { client }
}
