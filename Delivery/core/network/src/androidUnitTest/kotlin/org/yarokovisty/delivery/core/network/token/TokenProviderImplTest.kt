package org.yarokovisty.delivery.core.network.token

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.core.storage.encryption.EncryptedPreferencesStorage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class TokenProviderImplTest {

    @Test
    fun `get EXPECT invoke getDecryptedString by encrypted storage with token key`() = runTest {
        val encryptedStorage = mockk<EncryptedPreferencesStorage>()
        coEvery { encryptedStorage.getDecryptedString("token") } returns "test_token"
        val tokenProvider = TokenProviderImpl(encryptedStorage)

        tokenProvider.get()

        coVerify { encryptedStorage.getDecryptedString("token") }
    }

    @Test
    fun `get EXPECT token`() = runTest {
        val encryptedStorage = mockk<EncryptedPreferencesStorage>()
        val expectedToken = "test_token_value"
        coEvery { encryptedStorage.getDecryptedString("token") } returns expectedToken
        val tokenProvider = TokenProviderImpl(encryptedStorage)

        val result = tokenProvider.get()

        assertEquals(expectedToken, result)
    }

    @Test
    fun `get when not saved EXPECT null`() = runTest {
        val encryptedStorage = mockk<EncryptedPreferencesStorage>()
        coEvery { encryptedStorage.getDecryptedString("token") } returns null
        val tokenProvider = TokenProviderImpl(encryptedStorage)

        val result = tokenProvider.get()

        assertNull(result)
    }

    @Test
    fun `set EXPECT invoke putEncryptedString by encrypted storage with token key and token`() = runTest {
        val encryptedStorage = mockk<EncryptedPreferencesStorage>(relaxed = true)
        val tokenProvider = TokenProviderImpl(encryptedStorage)
        val token = "new_token_value"

        tokenProvider.set(token)

        coVerify { encryptedStorage.putEncryptedString("token", token) }
    }

    @Test
    fun `clear EXPECT invoke remove by encrypted storage with token key`() = runTest {
        val encryptedStorage = mockk<EncryptedPreferencesStorage>(relaxed = true)
        val tokenProvider = TokenProviderImpl(encryptedStorage)

        tokenProvider.clear()

        coVerify { encryptedStorage.remove("token") }
    }
}
