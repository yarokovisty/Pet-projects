package org.yarokovisty.delivery.feature.delivery.calculator.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.feature.delivery.calculator.navigation.CalculatorRouter
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.intent.CalculatorIntent
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.CalculatorState
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.StepState
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.state.initial
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CalculatorViewModelTest {

    private val calculatorRepository: CalculatorRepository = mockk()
    private val router: CalculatorRouter = mockk(relaxed = true)

    private val parcelInfo = ParcelInfo(
        id = "envelope",
        type = PackageType.ENVELOPE,
        name = "Envelope",
        length = 10,
        width = 5,
        height = 2,
        weight = 1
    )

    private val senderPoint = DeliveryPoint(
        id = "sender-1",
        name = "Sender",
        latitude = 55.0,
        longitude = 37.0
    )

    private val receiverPoint = DeliveryPoint(
        id = "receiver-1",
        name = "Receiver",
        latitude = 59.0,
        longitude = 30.0
    )

    private val options = listOf(
        Option(id = "opt-1", price = 500.0, days = 3, type = OptionType.DEFAULT),
        Option(id = "opt-2", price = 1000.0, days = 1, type = OptionType.EXPRESS)
    )

    private val maxSteps = 3

    private fun createViewModel() = CalculatorViewModel(
        calculatorRepository = calculatorRepository,
        router = router,
        parcelInfo = parcelInfo,
        senderPoint = senderPoint,
        receiverPoint = receiverPoint,
        maxSteps = maxSteps
    )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init EXPECT loading state`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()

        val actual = viewModel.state.value
        assertTrue(actual.skeleton)
    }

    @Test
    fun `init EXPECT correct step state`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()

        val actual = viewModel.state.value
        assertEquals(StepState(progress = 1, maxProgress = maxSteps), actual.stepState)
    }

    @Test
    fun `loading data is success EXPECT content state with options`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(options, actual.options)
    }

    @Test
    fun `loading data is success EXPECT skeleton is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertFalse(actual.skeleton)
    }

    @Test
    fun `loading data is error EXPECT error state`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertTrue(actual.error)
    }

    @Test
    fun `loading data is error EXPECT skeleton is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertFalse(actual.skeleton)
    }

    @Test
    fun `back intent EXPECT router back called`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(CalculatorIntent.Back)

        verify { router.back() }
    }

    @Test
    fun `load data intent EXPECT data reloaded`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(CalculatorIntent.LoadData)
        advanceUntilIdle()

        coVerify(exactly = 2) {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        }
    }

    @Test
    fun `load data intent after error EXPECT content state`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        viewModel.onIntent(CalculatorIntent.LoadData)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(options, actual.options)
    }

    @Test
    fun `load data intent after error EXPECT error is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        viewModel.onIntent(CalculatorIntent.LoadData)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertFalse(actual.error)
    }

    @Test
    fun `select option intent EXPECT repository set option called`() = runTest {
        val selectedOption = options[0]
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(CalculatorIntent.SelectOption(selectedOption))
        advanceUntilIdle()

        verify { calculatorRepository.setOption(selectedOption) }
    }
}
