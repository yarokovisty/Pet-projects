package org.yarokovisty.delivery.common.delivery.payer.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.payer.data.datasource.PayerLocalDataSource
import org.yarokovisty.delivery.common.delivery.payer.domain.entity.Payer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PayerRepositoryImplTest {

    private val localDataSource: PayerLocalDataSource = mockk(relaxed = true)
    private val repository = PayerRepositoryImpl(localDataSource)

    @Test
    fun `get payer when payer exists EXPECT payer returned`() = runTest {
        coEvery { localDataSource.getPayer() } returns Payer.SENDER

        val result = repository.getPayer()

        assertEquals(Payer.SENDER, result)
    }

    @Test
    fun `get payer when payer not exists EXPECT null returned`() = runTest {
        coEvery { localDataSource.getPayer() } returns null

        val result = repository.getPayer()

        assertNull(result)
    }

    @Test
    fun `get payer EXPECT invoke get payer by local data source`() = runTest {
        coEvery { localDataSource.getPayer() } returns Payer.RECEIVER

        repository.getPayer()

        coVerify { localDataSource.getPayer() }
    }

    @Test
    fun `set payer EXPECT invoke set payer by local data source`() = runTest {
        repository.setPayer(Payer.RECEIVER)

        coVerify { localDataSource.setPayer(Payer.RECEIVER) }
    }
}
