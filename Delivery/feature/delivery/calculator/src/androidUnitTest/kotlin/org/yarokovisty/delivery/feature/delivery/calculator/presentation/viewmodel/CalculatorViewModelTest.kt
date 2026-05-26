package org.yarokovisty.delivery.feature.delivery.calculator.presentation.viewmodel

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
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.Option
import org.yarokovisty.delivery.common.delivery.calculator.domain.entity.OptionType
import org.yarokovisty.delivery.common.delivery.calculator.domain.repository.CalculatorRepository
import org.yarokovisty.delivery.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.PackageType
import org.yarokovisty.delivery.common.delivery.parcel.domain.entity.ParcelInfo
import org.yarokovisty.delivery.common.delivery.presentation.StepState
import org.yarokovisty.delivery.feature.delivery.calculator.navigation.CalculatorRouter
import org.yarokovisty.delivery.feature.delivery.calculator.presentation.intent.CalculatorIntent
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
    fun `init viewmodel EXPECT step state with current step 1`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()

        val actual = viewModel.state.value.stepState
        assertEquals(StepState(progress = 1, maxProgress = maxSteps), actual)
    }

    @Test
    fun `init viewmodel EXPECT step state with max steps from parameter`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()

        val actual = viewModel.state.value.stepState.maxProgress
        assertEquals(maxSteps, actual)
    }

    @Test
    fun `init viewmodel EXPECT skeleton is true`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()

        val actual = viewModel.state.value.skeleton
        assertTrue(actual)
    }

    @Test
    fun `init viewmodel EXPECT error is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()

        val actual = viewModel.state.value.error
        assertFalse(actual)
    }

    @Test
    fun `init viewmodel EXPECT options are empty`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()

        val actual = viewModel.state.value.options
        assertTrue(actual.isEmpty())
    }

    @Test
    fun `loading data successfully EXPECT skeleton is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value.skeleton
        assertFalse(actual)
    }

    @Test
    fun `loading data successfully EXPECT error is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value.error
        assertFalse(actual)
    }

    @Test
    fun `loading data successfully EXPECT options list is populated`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value.options
        assertEquals(options, actual)
    }

    @Test
    fun `loading data failed EXPECT skeleton is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value.skeleton
        assertFalse(actual)
    }

    @Test
    fun `loading data failed EXPECT error is true`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value.error
        assertTrue(actual)
    }

    @Test
    fun `loading data failed EXPECT options are empty`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        val actual = viewModel.state.value.options
        assertTrue(actual.isEmpty())
    }

    @Test
    fun `back intent dispatched EXPECT router back called`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(CalculatorIntent.Back)

        verify(exactly = 1) { router.back() }
    }

    @Test
    fun `load data intent dispatched EXPECT repository called again`() = runTest {
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
    fun `load data intent dispatched EXPECT skeleton becomes true`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(CalculatorIntent.LoadData)

        val actual = viewModel.state.value.skeleton
        assertTrue(actual)
    }

    @Test
    fun `reload after error EXPECT skeleton is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        viewModel.onIntent(CalculatorIntent.LoadData)
        advanceUntilIdle()

        val actual = viewModel.state.value.skeleton
        assertFalse(actual)
    }

    @Test
    fun `reload after error EXPECT error is false`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        viewModel.onIntent(CalculatorIntent.LoadData)
        advanceUntilIdle()

        val actual = viewModel.state.value.error
        assertFalse(actual)
    }

    @Test
    fun `reload after error EXPECT options populated`() = runTest {
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } throws Exception("Network error")

        val viewModel = createViewModel()
        advanceUntilIdle()

        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options

        viewModel.onIntent(CalculatorIntent.LoadData)
        advanceUntilIdle()

        val actual = viewModel.state.value.options
        assertEquals(options, actual)
    }

    @Test
    fun `select option intent dispatched EXPECT repository set option called`() = runTest {
        val selectedOption = options[0]
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options
        coEvery { calculatorRepository.setOption(selectedOption) } just runs

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(CalculatorIntent.SelectOption(selectedOption))
        advanceUntilIdle()

        coVerify(exactly = 1) { calculatorRepository.setOption(selectedOption) }
    }

    @Test
    fun `select option intent dispatched EXPECT router open receiver screen called`() = runTest {
        val selectedOption = options[1]
        coEvery {
            calculatorRepository.getOptionList(parcelInfo, senderPoint, receiverPoint)
        } returns options
        coEvery { calculatorRepository.setOption(selectedOption) } just runs

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(CalculatorIntent.SelectOption(selectedOption))
        advanceUntilIdle()

        verify(exactly = 1) { router.openReceiverScreen() }
    }
}
