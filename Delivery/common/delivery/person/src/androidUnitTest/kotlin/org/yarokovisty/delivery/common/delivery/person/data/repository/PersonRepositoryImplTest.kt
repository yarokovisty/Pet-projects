package org.yarokovisty.delivery.common.delivery.person.data.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.yarokovisty.delivery.common.delivery.person.data.datasource.PersonLocalDataSource
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PersonRepositoryImplTest {

    private val localDataSource: PersonLocalDataSource = mockk(relaxUnitFun = true)
    private val repository = PersonRepositoryImpl(localDataSource)

    private val personInfo = PersonInfo(
        firstname = "Ivan",
        lastname = "Ivanov",
        middlename = "Ivanovich",
        phone = "+79991234567"
    )

    @Test
    fun `get receiver when receiver is set EXPECT person info`() = runTest {
        coEvery { localDataSource.getReceiver() } returns personInfo

        val actual = repository.getReceiver()

        assertEquals(personInfo, actual)
    }

    @Test
    fun `get receiver when receiver is not set EXPECT null`() = runTest {
        coEvery { localDataSource.getReceiver() } returns null

        val actual = repository.getReceiver()

        assertNull(actual)
    }

    @Test
    fun `set receiver EXPECT invoke local data source`() = runTest {
        repository.setReceiver(personInfo)

        coVerify { localDataSource.setReceiver(personInfo) }
    }

    @Test
    fun `get sender when sender is set EXPECT person info`() = runTest {
        coEvery { localDataSource.getSender() } returns personInfo

        val actual = repository.getSender()

        assertEquals(personInfo, actual)
    }

    @Test
    fun `get sender when sender is not set EXPECT null`() = runTest {
        coEvery { localDataSource.getSender() } returns null

        val actual = repository.getSender()

        assertNull(actual)
    }

    @Test
    fun `set sender EXPECT invoke local data source`() = runTest {
        repository.setSender(personInfo)

        coVerify { localDataSource.setSender(personInfo) }
    }
}
