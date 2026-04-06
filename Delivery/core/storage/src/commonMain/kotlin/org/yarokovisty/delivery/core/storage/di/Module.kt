package org.yarokovisty.delivery.core.storage.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.core.storage.encryption.EncryptedPreferencesStorage
import org.yarokovisty.delivery.core.storage.encryption.EncryptedPreferencesStorageImpl
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorageImpl

val storageModule = module {
    includes(dataStoreModule)

    singleOf(::PreferencesStorageImpl) bind PreferencesStorage::class
    singleOf(::EncryptedPreferencesStorageImpl) bind EncryptedPreferencesStorage::class
}

internal expect val dataStoreModule: Module
