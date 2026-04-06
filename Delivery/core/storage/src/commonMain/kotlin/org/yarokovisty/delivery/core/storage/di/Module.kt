package org.yarokovisty.delivery.core.storage.di

import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.yarokovisty.delivery.core.storage.encryption.EncryptedStringStorage
import org.yarokovisty.delivery.core.storage.encryption.EncryptedStringStorageImpl
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorage
import org.yarokovisty.delivery.core.storage.preferences.PreferencesStorageImpl
import org.yarokovisty.delivery.libs.encryption.StringEncryptor
import org.yarokovisty.delivery.libs.encryption.createStringEncryptor

val storageModule = module {
    includes(dataStoreModule, encryptionModule)
    single<StringEncryptor> {
        createStringEncryptor(
            keyProvider = get(),
            dispatcher = Dispatchers.Default,
        )
    }
    singleOf(::PreferencesStorageImpl) bind PreferencesStorage::class
    singleOf(::EncryptedStringStorageImpl) bind EncryptedStringStorage::class
}

internal expect val dataStoreModule: Module

internal expect val encryptionModule: Module
