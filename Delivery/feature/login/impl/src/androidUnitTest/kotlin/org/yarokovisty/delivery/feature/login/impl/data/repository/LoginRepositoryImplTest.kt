package org.yarokovisty.delivery.feature.login.impl.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.feature.login.api.error.LoginError
import org.yarokovisty.delivery.feature.login.impl.data.datasource.LoginRemoteDataSource
import org.yarokovisty.delivery.feature.login.impl.data.model.OtpRequest
import org.yarokovisty.delivery.feature.login.impl.data.model.OtpResponse
import org.yarokovisty.delivery.feature.login.impl.data.model.SigninRequest
import org.yarokovisty.delivery.feature.login.impl.data.model.SigninResponse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class LoginRepositoryImplTest {

    private val remoteDataSource: LoginRemoteDataSource = mockk()
    private val repository = LoginRepositoryImpl(remoteDataSource)

    private companion object {
        const val TEST_PHONE = "79123456789"
        const val TEST_OTP_CODE = 123456
        const val TEST_TOKEN = "test_token_abc123"
        const val TEST_RETRY_DELAY = 60000L
        const val INVALID_OTP_REASON = "Неправильный отп код"
    }

    @Test
    fun `request otp when success true EXPECT return retry delay`() = runTest {
        val response = OtpResponse(success = true, retryDelay = TEST_RETRY_DELAY)
        coEvery { remoteDataSource.otp(any()) } returns response

        val actual = repository.requestOtp(TEST_PHONE)

        assertEquals(TEST_RETRY_DELAY, actual)
    }

    @Test
    fun `request otp EXPECT invoke otp by remote data source with request`() = runTest {
        val response = OtpResponse(success = true, retryDelay = TEST_RETRY_DELAY)
        coEvery { remoteDataSource.otp(any()) } returns response

        repository.requestOtp(TEST_PHONE)

        coVerify { remoteDataSource.otp(OtpRequest(TEST_PHONE)) }
    }

    @Test
    fun `request otp when success false EXPECT error thrown`() = runTest {
        val response = OtpResponse(success = false, retryDelay = 0L)
        coEvery { remoteDataSource.otp(any()) } returns response

        assertFailsWith<IllegalStateException> {
            repository.requestOtp(TEST_PHONE)
        }
    }

    @Test
    fun `signin when success true and token exists EXPECT return token`() = runTest {
        val response = SigninResponse(success = true, token = TEST_TOKEN)
        coEvery { remoteDataSource.signin(any()) } returns response

        val actual = repository.signin(TEST_PHONE, TEST_OTP_CODE)

        assertEquals(TEST_TOKEN, actual)
    }

    @Test
    fun `signin EXPECT invoke signin by remote data source with request`() = runTest {
        val response = SigninResponse(success = true, token = TEST_TOKEN)
        coEvery { remoteDataSource.signin(any()) } returns response

        repository.signin(TEST_PHONE, TEST_OTP_CODE)

        coVerify { remoteDataSource.signin(SigninRequest(TEST_PHONE, TEST_OTP_CODE)) }
    }

    @Test
    fun `signin when reason is invalid otp EXPECT throw invalid otp error`() = runTest {
        val response = SigninResponse(success = false, reason = INVALID_OTP_REASON)
        coEvery { remoteDataSource.signin(any()) } returns response

        assertFailsWith<LoginError.InvalidOtp> {
            repository.signin(TEST_PHONE, TEST_OTP_CODE)
        }
    }

    @Test
    fun `signin when success false and no specific reason EXPECT throw unknown error`() = runTest {
        val response = SigninResponse(success = false, reason = "Some other error")
        coEvery { remoteDataSource.signin(any()) } returns response

        assertFailsWith<LoginError.Unknown> {
            repository.signin(TEST_PHONE, TEST_OTP_CODE)
        }
    }

    @Test
    fun `signin when success true but token is null EXPECT throw unknown error`() = runTest {
        val response = SigninResponse(success = true, token = null)
        coEvery { remoteDataSource.signin(any()) } returns response

        assertFailsWith<LoginError.Unknown> {
            repository.signin(TEST_PHONE, TEST_OTP_CODE)
        }
    }
}
