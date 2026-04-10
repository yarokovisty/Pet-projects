package org.yarokovisty.delivery.feature.profile.main.impl.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.yarokovisty.delivery.common.validation.error.EmailValidationError
import org.yarokovisty.delivery.common.validation.validator.EmailValidator
import org.yarokovisty.delivery.feature.profile.main.api.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.impl.domain.usecase.GetUserUseCase
import org.yarokovisty.delivery.feature.profile.main.impl.domain.usecase.UpdateUserUseCase
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.intent.ProfileIntent
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.router.ProfileRouter
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.ContentState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.EmailFieldState
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.EmailFieldStatus
import org.yarokovisty.delivery.feature.profile.main.impl.presentation.state.initial
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import org.yarokovisty.delivery.util.validation.validated.invalid
import org.yarokovisty.delivery.util.validation.validated.valid
import kotlin.test.assertEquals

class ProfileViewModelTest {

    private val getUserUseCase: GetUserUseCase = mockk()
    private val updateUserUseCase: UpdateUserUseCase = mockk(relaxed = true)
    private val emailValidator: EmailValidator = mockk()
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

    private fun createViewModel() = ProfileViewModel(
        getUserUseCase,
        updateUserUseCase,
        emailValidator,
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
        coEvery { getUserUseCase() } returns user

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
        coEvery { getUserUseCase() } throws Exception("Network error")

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
        coEvery { getUserUseCase() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.LoadData)
        advanceUntilIdle()

        coVerify(exactly = 2) { getUserUseCase() }
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
        coEvery { getUserUseCase() } returns user

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
        coEvery { getUserUseCase() } returns user

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
        coEvery { getUserUseCase() } returns user

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
        val expected = EmailFieldState(
            text = newEmail,
            status = EmailFieldStatus.NotValidated
        )
        coEvery { getUserUseCase() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.InputEmail(newEmail))
        advanceUntilIdle()

        val actual = viewModel.state.value.content?.email
        assertEquals(expected, actual)
    }

    @Test
    fun `click update data with valid email EXPECT update user use case called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = TEST_EMAIL,
            city = null
        )
        coEvery { getUserUseCase() } returns user
        every { emailValidator.validate(TEST_EMAIL, required = true) } returns valid(TEST_EMAIL)
        coEvery { updateUserUseCase(any()) } returns Unit

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ClickUpdateData)
        advanceUntilIdle()

        coVerify { updateUserUseCase(any()) }
    }

    @Test
    fun `click update data with invalid email EXPECT update user use case not called`() = runTest {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = null,
            middlename = null,
            email = "invalid",
            city = null
        )
        coEvery { getUserUseCase() } returns user
        every {
            emailValidator.validate("invalid", required = true)
        } returns invalid(EmailValidationError.NOT_MATCH_THE_PATTERN)

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ClickUpdateData)
        advanceUntilIdle()

        coVerify(exactly = 0) { updateUserUseCase(any()) }
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
        coEvery { getUserUseCase() } returns user
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
        coEvery { getUserUseCase() } returns user
        every { emailValidator.validate(TEST_EMAIL, required = true) } returns valid(TEST_EMAIL)
        coEvery { updateUserUseCase(any()) } returns Unit

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
        coEvery { getUserUseCase() } returns user

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(ProfileIntent.ClickCity)
        advanceUntilIdle()

        verify { router.openDirectionScreen() }
    }
}
