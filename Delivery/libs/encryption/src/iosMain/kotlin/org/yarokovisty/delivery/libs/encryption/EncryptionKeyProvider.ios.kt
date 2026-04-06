package org.yarokovisty.delivery.libs.encryption

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.reinterpret
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import platform.Foundation.NSData
import platform.Foundation.NSUserDefaults
import platform.Foundation.create
import platform.Security.SecRandomCopyBytes
import platform.Security.kSecRandomDefault

actual fun createPlatformEncryptionKeyProvider(context: Any?): EncryptionKeyProvider =
    IOSUserDefaultsEncryptionKeyProvider()

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private class IOSUserDefaultsEncryptionKeyProvider : EncryptionKeyProvider {
    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val mutex = Mutex()
    private var cachedKey: ByteArray? = null

    override suspend fun provideKey(): ByteArray {
        return cachedKey ?: mutex.withLock {
            cachedKey ?: getOrCreateKeyBytes().also { cachedKey = it }
        }
    }

    private fun getOrCreateKeyBytes(): ByteArray {
        val existingKey = retrieveKey()
        if (existingKey != null) {
            return existingKey
        }

        val newKey = ByteArray(KEY_SIZE_BYTES)
        newKey.usePinned { pinned ->
            SecRandomCopyBytes(kSecRandomDefault, KEY_SIZE_BYTES.toULong(), pinned.addressOf(0))
        }
        storeKey(newKey)
        return newKey
    }

    private fun retrieveKey(): ByteArray? {
        val data = userDefaults.dataForKey(KEY_NAME) ?: return null
        val bytes = data.bytes?.reinterpret<kotlinx.cinterop.ByteVar>() ?: return null
        val length = data.length.toInt()
        return ByteArray(length) { index -> bytes[index] }
    }

    private fun storeKey(keyBytes: ByteArray) = memScoped {
        val data = NSData.create(
            bytes = allocArrayOf(keyBytes),
            length = keyBytes.size.toULong(),
        )
        userDefaults.setObject(data, forKey = KEY_NAME)
        userDefaults.synchronize()
    }

    private companion object {
        const val KEY_NAME = "org.yarokovisty.delivery.storage.encryption_key"
        const val KEY_SIZE_BYTES = 32
    }
}
