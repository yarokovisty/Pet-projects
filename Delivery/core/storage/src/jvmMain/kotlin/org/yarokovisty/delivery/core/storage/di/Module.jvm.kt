package org.yarokovisty.delivery.core.storage.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.yarokovisty.delivery.core.storage.datastore.createDataStore
import org.yarokovisty.delivery.libs.encryption.EncryptionKeyProvider
import org.yarokovisty.delivery.libs.encryption.createPlatformEncryptionKeyProvider

internal actual val dataStoreModule: Module = module {
    singleOf(::createDataStore)
}

internal actual val encryptionModule: Module = module {
    single<EncryptionKeyProvider> { createPlatformEncryptionKeyProvider() }
}
