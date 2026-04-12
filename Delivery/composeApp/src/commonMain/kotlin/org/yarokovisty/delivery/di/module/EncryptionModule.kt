package org.yarokovisty.delivery.di.module

import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.yarokovisty.delivery.core.common.coroutines.DispatchersQualifier
import org.yarokovisty.delivery.libs.encryption.StringEncryptor
import org.yarokovisty.delivery.libs.encryption.createStringEncryptor

val encryptionModule = module {
    includes(encryptionKeyProviderModule)
    single<StringEncryptor> {
        createStringEncryptor(
            keyProvider = get(),
            dispatcher = get(named(DispatchersQualifier.DEFAULT))
        )
    }
}

expect val encryptionKeyProviderModule: Module
