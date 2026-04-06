package org.yarokovisty.delivery.common.auth.data.datasource

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.core.storage.encryption.EncryptedPreferencesStorage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class AuthLocalDataSourceTest {

    private val encryptedStorage: EncryptedPreferencesStorage = mockk()
    private val dataSource = AuthLocalDataSource(encryptedStorage)

    private companion object {

        const val TEST_TOKEN = "test_token_abc123"
        const val TOKEN_KEY = "token"
    }

    @Test
    fun `save token EXPECT invoke put string by storage`() = runTest {
        coEvery { encryptedStorage.putEncryptedString(any(), any()) } returns Unit

        dataSource.saveToken(TEST_TOKEN)

        coVerify { encryptedStorage.putEncryptedString(TOKEN_KEY, TEST_TOKEN) }
    }

    @Test
    fun `get token EXPECT token from storage`() = runTest {
        coEvery { encryptedStorage.getDecryptedString(TOKEN_KEY) } returns TEST_TOKEN

        val actual = dataSource.getToken()

        assertEquals(TEST_TOKEN, actual)
    }

    @Test
    fun `get token EXPECT invoke get string by storage`() = runTest {
        coEvery { encryptedStorage.getDecryptedString(any()) } returns TEST_TOKEN

        dataSource.getToken()

        coVerify { encryptedStorage.getDecryptedString(TOKEN_KEY) }
    }

    @Test
    fun `get token when storage returns null EXPECT null`() = runTest {
        coEvery { encryptedStorage.getDecryptedString(TOKEN_KEY) } returns null

        val actual = dataSource.getToken()

        assertNull(actual)
    }

    @Test
    fun `clear token EXPECT invoke remove by storage`() = runTest {
        coEvery { encryptedStorage.remove(any()) } returns Unit

        dataSource.clearToken()

        coVerify { encryptedStorage.remove(TOKEN_KEY) }
    }
}
