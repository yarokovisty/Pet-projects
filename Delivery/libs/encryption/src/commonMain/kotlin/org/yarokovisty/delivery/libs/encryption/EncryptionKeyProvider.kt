package org.yarokovisty.delivery.libs.encryption

interface EncryptionKeyProvider {
    suspend fun provideKey(): ByteArray
}

expect fun createPlatformEncryptionKeyProvider(context: Any? = null): EncryptionKeyProvider
