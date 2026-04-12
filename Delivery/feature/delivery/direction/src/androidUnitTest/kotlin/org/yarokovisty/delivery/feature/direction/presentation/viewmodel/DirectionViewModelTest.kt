package org.yarokovisty.delivery.feature.direction.presentation.viewmodel

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.yarokovisty.common.delivery.direction.domain.entity.DeliveryPoint
import org.yarokovisty.common.delivery.direction.domain.entity.DirectionType
import org.yarokovisty.common.delivery.direction.domain.repository.DirectionRepository
import org.yarokovisty.delivery.feature.direction.navigation.DirectionRouter
import org.yarokovisty.delivery.feature.direction.presentation.intent.DirectionIntent
import org.yarokovisty.delivery.feature.direction.presentation.state.DirectionContentState
import org.yarokovisty.delivery.feature.direction.presentation.state.initial
import org.yarokovisty.delivery.util.unitTest.MainDispatcherRule
import kotlin.test.assertEquals

class DirectionViewModelTest {

    private val directionRepository: DirectionRepository = mockk()
    private val router = mockk<DirectionRouter>(relaxed = true)

    private val points = listOf(
        DeliveryPoint(
            id = "0",
            name = "name0",
            latitude = 0.0,
            longitude = 0.0
        ),
        DeliveryPoint(
            id = "1",
            name = "name1",
            latitude = 1.0,
            longitude = 1.0
        ),
        DeliveryPoint(
            id = "2",
            name = "name2",
            latitude = 2.0,
            longitude = 2.0
        )
    )

    private fun createViewModel(directionType: DirectionType = DirectionType.FROM) =
        DirectionViewModel(
            directionRepository,
            router,
            directionType
        )

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `init EXPECT loading state`() = runTest {
        val directionType = DirectionType.FROM
        val expected = initial(directionType).copy(loading = true)

        val viewModel = createViewModel(directionType)

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading data is success EXPECT content state`() = runTest {
        val directionType = DirectionType.TO
        val expected = initial(directionType).copy(
            loading = false,
            content = DirectionContentState(deliveryPoints = points)
        )
        coEvery { directionRepository.getDeliveryPointList() } returns points

        val viewModel = createViewModel(directionType)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `loading data is error EXPECT error state`() = runTest {
        val directionType = DirectionType.FROM
        val expected = initial(directionType).copy(
            loading = false,
            error = true
        )
        coEvery { directionRepository.getDeliveryPointList() } throws Exception("error")

        val viewModel = createViewModel(directionType)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
    }

    @Test
    fun `back intent EXPECT router back called`() = runTest {
        val intent = DirectionIntent.Back
        coEvery { directionRepository.getDeliveryPointList() } returns points

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(intent)

        verify { router.back() }
    }

    @Test
    fun `load data intent EXPECT data reloaded`() = runTest {
        val directionType = DirectionType.FROM
        val expected = initial(directionType).copy(
            loading = false,
            content = DirectionContentState(deliveryPoints = points)
        )
        val intent = DirectionIntent.LoadData
        coEvery { directionRepository.getDeliveryPointList() } returns points

        val viewModel = createViewModel(directionType)
        advanceUntilIdle()
        viewModel.onIntent(intent)
        advanceUntilIdle()

        val actual = viewModel.state.value
        assertEquals(expected, actual)
        coVerify(exactly = 2) { directionRepository.getDeliveryPointList() }
    }

    @Test
    fun `select delivery point EXPECT router back called`() = runTest {
        val selectedPoint = points[1]
        val intent = DirectionIntent.SelectDeliveryPoint(selectedPoint)
        coEvery { directionRepository.getDeliveryPointList() } returns points

        val viewModel = createViewModel()
        advanceUntilIdle()
        viewModel.onIntent(intent)
        advanceUntilIdle()

        verify { router.back() }
    }
}
