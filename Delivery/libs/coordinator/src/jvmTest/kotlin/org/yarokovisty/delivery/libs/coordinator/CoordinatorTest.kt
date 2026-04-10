package org.yarokovisty.delivery.libs.coordinator

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CoordinatorTest {

    private data class TestResult(val value: String)
    private data class OtherResult(val number: Int)

    @Test
    fun `publish and await same type EXPECT value received`() = runTest {
        val expected = TestResult("test")

        val deferred = async { Coordinator.await<TestResult>() }
        Coordinator.publish(expected)

        val actual = deferred.await()
        assertEquals(expected, actual)
    }

    @Test
    fun `publish string await string EXPECT string received`() = runTest {
        val expected = "test-string"

        val deferred = async { Coordinator.await<String>() }
        Coordinator.publish(expected)

        val actual = deferred.await()
        assertEquals(expected, actual)
    }

    @Test
    fun `publish int await int EXPECT int received`() = runTest {
        val expected = 42

        val deferred = async { Coordinator.await<Int>() }
        Coordinator.publish(expected)

        val actual = deferred.await()
        assertEquals(expected, actual)
    }

    @Test
    fun `publish multiple types EXPECT await filters by type`() = runTest {
        val testResult = TestResult("result")
        val otherResult = OtherResult(42)

        val deferred = async { Coordinator.await<TestResult>() }
        Coordinator.publish(otherResult)
        Coordinator.publish(testResult)

        val actual = deferred.await()
        assertEquals(testResult, actual)
    }

    @Test
    fun `await before publish EXPECT suspends until value published`() = runTest {
        val expected = TestResult("delayed")
        var received: TestResult? = null

        val job = launch {
            received = Coordinator.await<TestResult>()
        }

        assertEquals(null, received)

        Coordinator.publish(expected)
        job.join()

        assertEquals(expected, received)
    }

    @Test
    fun `multiple awaits EXPECT all receive same value`() = runTest {
        val expected = TestResult("broadcast")

        val deferred1 = async { Coordinator.await<TestResult>() }
        val deferred2 = async { Coordinator.await<TestResult>() }
        val deferred3 = async { Coordinator.await<TestResult>() }

        Coordinator.publish(expected)

        assertEquals(expected, deferred1.await())
        assertEquals(expected, deferred2.await())
        assertEquals(expected, deferred3.await())
    }

    @Test
    fun `publish after await started EXPECT value received by first call only`() = runTest {
        val first = TestResult("first")
        val second = TestResult("second")

        val deferred1 = async { Coordinator.await<TestResult>() }
        Coordinator.publish(first)
        val actual1 = deferred1.await()

        val deferred2 = async { Coordinator.await<TestResult>() }
        Coordinator.publish(second)
        val actual2 = deferred2.await()

        assertEquals(first, actual1)
        assertEquals(second, actual2)
    }
}
