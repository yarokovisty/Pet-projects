package org.yarokovisty.delivery.di.module

import org.koin.core.module.Module
import org.koin.dsl.module
import org.yarokovisty.delivery.libs.encryption.EncryptionKeyProvider
import org.yarokovisty.delivery.libs.encryption.createPlatformEncryptionKeyProvider

actual val encryptionKeyProviderModule = module {
    single<EncryptionKeyProvider> { createPlatformEncryptionKeyProvider() }
}