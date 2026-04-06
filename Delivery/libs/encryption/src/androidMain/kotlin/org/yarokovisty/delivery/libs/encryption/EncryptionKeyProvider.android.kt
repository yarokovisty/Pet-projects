package org.yarokovisty.delivery.libs.encryption

import android.annotation.SuppressLint
import android.content.Context
import android.util.Base64
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.security.SecureRandom

actual fun createPlatformEncryptionKeyProvider(context: Any?): EncryptionKeyProvider {
    require(context is Context) { "Android platform requires Context parameter" }
    return AndroidSharedPrefsEncryptionKeyProvider(context)
}

private class AndroidSharedPrefsEncryptionKeyProvider(
    private val context: Context,
) : EncryptionKeyProvider {
    private val mutex = Mutex()
    private var cachedKey: ByteArray? = null

    override suspend fun provideKey(): ByteArray {
        return cachedKey ?: mutex.withLock {
            cachedKey ?: getOrCreateKeyBytes().also { cachedKey = it }
        }
    }

    @SuppressLint("UseKtx")
    private fun getOrCreateKeyBytes(): ByteArray {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val existingKey = prefs.getString(KEY_NAME, null)
        if (existingKey != null) {
            return Base64.decode(existingKey, Base64.NO_WRAP)
        }

        val newKey = ByteArray(KEY_SIZE_BYTES)
        SecureRandom().nextBytes(newKey)

        prefs.edit()
            .putString(KEY_NAME, Base64.encodeToString(newKey, Base64.NO_WRAP))
            .apply()

        return newKey
    }

    private companion object {
        const val PREFS_NAME = "org.yarokovisty.delivery.encryption"
        const val KEY_NAME = "encryption_key"
        const val KEY_SIZE_BYTES = 32
    }
}
