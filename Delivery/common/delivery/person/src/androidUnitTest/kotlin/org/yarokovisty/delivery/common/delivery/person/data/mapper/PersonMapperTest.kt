package org.yarokovisty.delivery.common.delivery.person.data.mapper

import org.yarokovisty.delivery.common.delivery.person.data.model.PersonInfoRequest
import org.yarokovisty.delivery.common.delivery.person.data.model.PersonInfoResponse
import org.yarokovisty.delivery.common.delivery.person.domain.entity.PersonInfo
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PersonMapperTest {

    private companion object {
        const val TEST_FIRSTNAME = "Ivan"
        const val TEST_LASTNAME = "Ivanov"
        const val TEST_MIDDLENAME = "Ivanovich"
        const val TEST_PHONE = "+79991234567"
    }

    // region toItem

    @Test
    fun `to item with all fields EXPECT person info with all fields`() {
        val response = PersonInfoResponse(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            phone = TEST_PHONE
        )
        val expected = PersonInfo(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            phone = TEST_PHONE
        )

        val actual = response.toItem()

        assertEquals(expected, actual)
    }

    @Test
    fun `to item with null middlename EXPECT person info with null middlename`() {
        val response = PersonInfoResponse(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = null,
            phone = TEST_PHONE
        )
        val expected = PersonInfo(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = null,
            phone = TEST_PHONE
        )

        val actual = response.toItem()

        assertEquals(expected, actual)
    }

    @Test
    fun `to item with empty middlename EXPECT person info with null middlename`() {
        val response = PersonInfoResponse(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = "",
            phone = TEST_PHONE
        )

        val actual = response.toItem()

        assertNull(actual.middlename)
    }

    @Test
    fun `to item with whitespace middlename EXPECT person info with whitespace middlename`() {
        val response = PersonInfoResponse(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = "   ",
            phone = TEST_PHONE
        )
        val expected = PersonInfo(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = "   ",
            phone = TEST_PHONE
        )

        val actual = response.toItem()

        assertEquals(expected, actual)
    }

    // endregion

    // region toRequest

    @Test
    fun `to request with all fields EXPECT request with all fields`() {
        val personInfo = PersonInfo(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            phone = TEST_PHONE
        )
        val expected = PersonInfoRequest(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            phone = TEST_PHONE
        )

        val actual = personInfo.toRequest()

        assertEquals(expected, actual)
    }

    @Test
    fun `to request with null middlename EXPECT request with empty middlename`() {
        val personInfo = PersonInfo(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = null,
            phone = TEST_PHONE
        )
        val expected = PersonInfoRequest(
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = "",
            phone = TEST_PHONE
        )

        val actual = personInfo.toRequest()

        assertEquals(expected, actual)
    }

    // endregion
}
