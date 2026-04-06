package org.yarokovisty.delivery.libs.encryption

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

fun createStringEncryptor(
    keyProvider: EncryptionKeyProvider,
    dispatcher: CoroutineDispatcher,
): StringEncryptor = AesGcmStringEncryptor(keyProvider, dispatcher)

@OptIn(ExperimentalEncodingApi::class)
internal class AesGcmStringEncryptor(
    private val keyProvider: EncryptionKeyProvider,
    private val dispatcher: CoroutineDispatcher,
) : StringEncryptor {
    private val mutex = Mutex()
    private var key: AES.GCM.Key? = null

    private suspend fun getKey(): AES.GCM.Key {
        return key ?: mutex.withLock {
            key ?: run {
                val keyBytes = keyProvider.provideKey()
                val provider = CryptographyProvider.Default
                val gcm = provider.get(AES.GCM)
                gcm.keyDecoder().decodeFromByteArray(AES.Key.Format.RAW, keyBytes).also { key = it }
            }
        }
    }

    override suspend fun encrypt(plaintext: String): String = withContext(dispatcher) {
        val key = getKey()
        val plaintextBytes = plaintext.encodeToByteArray()
        val ciphertext = key.cipher().encryptBlocking(plaintextBytes)
        Base64.encode(ciphertext)
    }

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    override suspend fun decrypt(ciphertext: String): String? = withContext(dispatcher) {
        try {
            val key = getKey()
            val ciphertextBytes = Base64.decode(ciphertext)
            val plaintextBytes = key.cipher().decryptBlocking(ciphertextBytes)
            plaintextBytes.decodeToString()
        } catch (e: Throwable) {
            // Decryption can fail for various reasons: corrupted data, wrong key, invalid format
            // Return null to indicate failure as per the fail-safe design
            null
        }
    }
}
