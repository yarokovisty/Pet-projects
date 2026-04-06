package org.yarokovisty.delivery.libs.encryption

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalEncodingApi::class)
class AesGcmStringEncryptorTest {

    private class TestKeyProvider : EncryptionKeyProvider {
        private var cachedKey: ByteArray? = null

        override suspend fun provideKey(): ByteArray {
            return cachedKey ?: Random.nextBytes(KEY_SIZE_BYTES).also { cachedKey = it }
        }

        private companion object {
            const val KEY_SIZE_BYTES = 32
        }
    }

    @Test
    fun testEncryptDecryptRoundTrip() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = "Hello, World!"

        val encrypted = encryptor.encrypt(plaintext)
        val decrypted = encryptor.decrypt(encrypted)

        assertEquals(plaintext, decrypted)
    }

    @Test
    fun testEncryptedTextIsDifferent() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = "Secret message"

        val encrypted = encryptor.encrypt(plaintext)

        assertNotEquals(plaintext, encrypted)
    }

    @Test
    fun testEncryptedTextIsBase64() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = "Test data"

        val encrypted = encryptor.encrypt(plaintext)

        // Verify the output is valid Base64
        val decoded = Base64.decode(encrypted)
        assertTrue(decoded.isNotEmpty())
        assertNotNull(encrypted)
    }

    @Test
    fun testDecryptInvalidDataReturnsNull() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val invalidCiphertext = "InvalidBase64!@#"

        val decrypted = encryptor.decrypt(invalidCiphertext)

        assertNull(decrypted)
    }

    @Test
    fun testDecryptCorruptedDataReturnsNull() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = "Original message"

        val encrypted = encryptor.encrypt(plaintext)
        val corrupted = encrypted.dropLast(5) + "XXXXX"

        val decrypted = encryptor.decrypt(corrupted)

        assertNull(decrypted)
    }

    @Test
    fun testDecryptWithDifferentKeyReturnsNull() = runTest {
        val encryptor1 = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val encryptor2 = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = "Sensitive data"

        val encrypted = encryptor1.encrypt(plaintext)
        val decrypted = encryptor2.decrypt(encrypted)

        assertNull(decrypted)
    }

    @Test
    fun testEncryptEmptyString() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = ""

        val encrypted = encryptor.encrypt(plaintext)
        val decrypted = encryptor.decrypt(encrypted)

        assertEquals(plaintext, decrypted)
    }

    @Test
    fun testEncryptLongString() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = "A".repeat(10000)

        val encrypted = encryptor.encrypt(plaintext)
        val decrypted = encryptor.decrypt(encrypted)

        assertEquals(plaintext, decrypted)
    }

    @Test
    fun testEncryptUnicodeCharacters() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = "Hello 世界 🌍 Привет"

        val encrypted = encryptor.encrypt(plaintext)
        val decrypted = encryptor.decrypt(encrypted)

        assertEquals(plaintext, decrypted)
    }

    @Test
    fun testMultipleEncryptionsProduceDifferentCiphertexts() = runTest {
        val encryptor = createStringEncryptor(TestKeyProvider(), Dispatchers.Default)
        val plaintext = "Same message"

        val encrypted1 = encryptor.encrypt(plaintext)
        val encrypted2 = encryptor.encrypt(plaintext)

        assertNotEquals(encrypted1, encrypted2)

        val decrypted1 = encryptor.decrypt(encrypted1)
        val decrypted2 = encryptor.decrypt(encrypted2)

        assertEquals(plaintext, decrypted1)
        assertEquals(plaintext, decrypted2)
    }
}
