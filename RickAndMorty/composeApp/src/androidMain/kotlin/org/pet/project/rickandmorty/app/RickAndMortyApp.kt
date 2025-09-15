package org.pet.project.rickandmorty.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.component.KoinComponent
import org.pet.project.rickandmorty.di.initKoin

class RickAndMortyApp : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger()
            androidContext(this@RickAndMortyApp)
        }
    }
}