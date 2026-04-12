package org.yarokovisty.delivery.feature.profile.main.data.mapper

import org.yarokovisty.delivery.feature.profile.main.data.model.ProfileRequest
import org.yarokovisty.delivery.feature.profile.main.data.model.UserRequest
import org.yarokovisty.delivery.feature.profile.main.data.model.UserResponse
import org.yarokovisty.delivery.feature.profile.main.domain.entity.User
import kotlin.test.Test
import kotlin.test.assertEquals

class UserMapperTest {

    private companion object {
        const val TEST_ID = "user123"
        const val TEST_PHONE = "79123456789"
        const val TEST_FIRSTNAME = "Ivan"
        const val TEST_LASTNAME = "Ivanov"
        const val TEST_MIDDLENAME = "Ivanovich"
        const val TEST_EMAIL = "ivan@example.com"
        const val TEST_CITY = "Moscow"
    }

    @Test
    fun `toItem with full user data EXPECT all fields mapped correctly`() {
        val response = UserResponse(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        val expected = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )

        val actual = response.toItem()

        assertEquals(expected, actual)
    }

    @Test
    fun `toItem with minimal user data EXPECT null fields mapped correctly`() {
        val response = UserResponse(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        val expected = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )

        val actual = response.toItem()

        assertEquals(expected, actual)
    }

    @Test
    fun `toRequest with full user data EXPECT all fields mapped correctly`() {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = TEST_FIRSTNAME,
            lastname = TEST_LASTNAME,
            middlename = TEST_MIDDLENAME,
            email = TEST_EMAIL,
            city = TEST_CITY
        )
        val expected = UserRequest(
            profile = ProfileRequest(
                firstname = TEST_FIRSTNAME,
                lastname = TEST_LASTNAME,
                middlename = TEST_MIDDLENAME,
                email = TEST_EMAIL,
                city = TEST_CITY
            ),
            phone = TEST_PHONE
        )

        val actual = user.toRequest()

        assertEquals(expected, actual)
    }

    @Test
    fun `toRequest with minimal user data EXPECT null fields mapped correctly`() {
        val user = User(
            id = TEST_ID,
            phone = TEST_PHONE,
            firstname = null,
            lastname = null,
            middlename = null,
            email = null,
            city = null
        )
        val expected = UserRequest(
            profile = ProfileRequest(
                firstname = null,
                lastname = null,
                middlename = null,
                email = null,
                city = null
            ),
            phone = TEST_PHONE
        )

        val actual = user.toRequest()

        assertEquals(expected, actual)
    }
}
