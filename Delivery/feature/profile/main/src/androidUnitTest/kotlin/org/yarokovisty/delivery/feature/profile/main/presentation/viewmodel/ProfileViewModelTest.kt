package org.yarokovisty.delivery.feature.profile.main.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.yarokovisty.delivery.common.logout.domain.usecase.LogoutUseCase
import org.yarokovisty.delivery.common.profile.main.domain.entity.User
import org.yarokovisty.delivery.common.profile.main.domain.repository.UserRepository
import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.common.validation.validator.EmailValidator
import org.yarokovisty.delivery.feature.profile.main.navigation.ProfileRouter
import org.yarokovisty.delivery.feature.profile.main.presentation.intent.ProfileIntent
import org.yarokovisty.delivery.feature.profile.main.presentation.state.ContentState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.EmailFieldState
import org.yarokovisty.delivery.feature.profile.main.presentation.state.EmailFieldStatus
import org.yarokovisty.delivery.feature.profile.main.presentation.state.initial
import org.yarokovisty.delivery.util.phone.PhoneNumberFormatter
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProfileViewModelTest {

    private val userRepository: UserRepository = mockk()
    private val logoutUseCase: LogoutUseCase = mockk(relaxed = true)
    private val emailValidator: EmailValidator = mockk()
    private val phoneNumberFormatter: PhoneNumberFormatter = mockk {
        every { format(TEST_PHONE) } returns TEST_PHONE_FORMATTED
    }
    private val router = mockk<ProfileRouter>(relaxed = true)

    private companion object {
        const val TEST_ID = "user123"
        const val TEST_PHONE = "79123456789"
        const val TEST_PHONE_FORMATTED = "+7 912 345 67 89"
        const val TEST_FIRSTNAME = "Ivan"
        const val TEST_LASTNAME = "Ivanov"
        const val TEST_MIDDLENAME = "Ivanovich"
        const val TEST_EMAIL = "ivan@example.com"
        const val TEST_CITY = "Moscow"
    }

    private fun createViewModel() =
        ProfileViewModel(
            userRepository,
            logoutUseCase,
            emailValidator,
            phoneNumberFormatter,
            router
        )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init EXPECT loading state`() = runTest {
        val expected = initial().copy(loading = true)

        val viewModel = createViewModel()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading data is success EXPECT content state`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        val expected = initial().copy(
            loading = false,
            content = ContentState(
                user = user,
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                city = TEST_CITY,
                phone = TEST_PHONE_FORMATTED,
                email = EmailFieldState(
                    text = TEST_EMAIL,
                    status = EmailFieldStatus.NotValidated
                ),
                downloadingDataUpdate = false
            )
        )
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading data is error EXPECT error state`() = runTest {
        val expected = initial().copy(
            loading = false,
            error = true
        )
        coEvery { userRepository.get() } throws Exception("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `load data intent EXPECT data reloaded`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.LoadData)
        advanceUntilIdle()

        coVerify(exactly = 2) { userRepository.get() }
    }

    @Test
    fun `input firstname intent EXPECT firstname field updated`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        val newFirstname = "Petr"
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.InputFirstname(newFirstname))
        advanceUntilIdle()

        val actual = viewModel.state.value.content?.firstname
        assertEquals(newFirstname, actual)
    }

    @Test
    fun `input lastname intent EXPECT lastname field updated`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = TEST_LASTNAME,
            middlename = null,
            email = null,
            city = null
        )
        val newLastname = "Petrov"
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.InputLastname(newLastname))
        advanceUntilIdle()

        val actual = viewModel.state.value.content?.lastname
        assertEquals(newLastname, actual)
    }

    @Test
    fun `input middlename intent EXPECT middlename field updated`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = TEST_MIDDLENAME,
            email = null,
            city = null
        )
        val newMiddlename = "Petrovich"
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.InputMiddlename(newMiddlename))
        advanceUntilIdle()

        val actual = viewModel.state.value.content?.middlename
        assertEquals(newMiddlename, actual)
    }

    @Test
    fun `input email intent EXPECT email field updated with not validated status`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        val newEmail = "new@example.com"
        val expected =
            EmailFieldState(
                text = newEmail,
                status = EmailFieldStatus.NotValidated
            )
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.InputEmail(newEmail))
        advanceUntilIdle()

        val actual = viewModel.state.value.content?.email
        assertEquals(expected, actual)
    }

    @Test
    fun `click update data with valid email EXPECT user repository update called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { userRepository.get() } returns user
        every { emailValidator.validate(TEST_EMAIL, required = true) } returns valid(TEST_EMAIL)
        coEvery { userRepository.update(any()) } returns Unit

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ClickUpdateData)
        advanceUntilIdle()

        coVerify { userRepository.update(any()) }
    }

    @Test
    fun `click update data with invalid email EXPECT user repository update not called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = "invalid",
            city = null
        )
        coEvery { userRepository.get() } returns user
        every {
            emailValidator.validate("invalid", required = true)
        } returns invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ClickUpdateData)
        advanceUntilIdle()

        coVerify(exactly = 0) { userRepository.update(any()) }
    }

    @Test
    fun `click update data with invalid email EXPECT email status is invalid`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = "invalid",
            city = null
        )
        val expected = EmailFieldStatus.Invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)
        coEvery { userRepository.get() } returns user
        every {
            emailValidator.validate("invalid", required = true)
        } returns invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ClickUpdateData)
        advanceUntilIdle()

        val actual = viewModel.state.value.content?.email?.status
        assertEquals(expected, actual)
    }

    @Test
    fun `click update data with valid email EXPECT email status is valid`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        val expected = EmailFieldStatus.Valid
        coEvery { userRepository.get() } returns user
        every { emailValidator.validate(TEST_EMAIL, required = true) } returns valid(TEST_EMAIL)
        coEvery { userRepository.update(any()) } returns Unit

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ClickUpdateData)
        advanceUntilIdle()

        val actual = viewModel.state.value.content?.email?.status
        assertEquals(expected, actual)
    }

    @Test
    fun `click city intent EXPECT router opens direction screen`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ClickCity)
        advanceUntilIdle()

        verify { router.openDirectionScreen() }
    }

    @Test
    fun `show logout screen intent EXPECT logout screen visible is true`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ShowLogoutScreen)
        advanceUntilIdle()

        val actual = viewModel.state.value.logoutScreenVisible
        assertTrue(actual)
    }

    @Test
    fun `close logout screen intent EXPECT logout screen visible is false`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { userRepository.get() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ShowLogoutScreen)
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.CloseLogoutScreen)
        advanceUntilIdle()

        val actual = viewModel.state.value.logoutScreenVisible
        assertFalse(actual)
    }

    @Test
    fun `confirm logout intent EXPECT logout use case called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { userRepository.get() } returns user
        coEvery { logoutUseCase() } just runs

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ConfirmLogout)
        advanceUntilIdle()

        coVerify { logoutUseCase() }
    }

    @Test
    fun `confirm logout intent EXPECT router opens delivery main tab`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { userRepository.get() } returns user
        coEvery { logoutUseCase() } just runs

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ConfirmLogout)
        advanceUntilIdle()

        verify { router.openDeliveryMainTab() }
    }

    @Test
    fun `confirm logout intent EXPECT logout screen visible is false`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        coEvery { userRepository.get() } returns user
        coEvery { logoutUseCase() } just runs

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ShowLogoutScreen)
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ConfirmLogout)
        advanceUntilIdle()

        val actual = viewModel.state.value.logoutScreenVisible
        assertFalse(actual)
    }
}
