package org.yarokovisty.delivery.libs.encryption

interface StringEncryptor {
    suspend fun encrypt(plaintext: String): String

    suspend fun decrypt(ciphertext: String): String?
}
