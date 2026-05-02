package org.yarokovisty.delivery.common.profile.main.data.mapper

import org.yarokovisty.delivery.common.profile.main.data.model.ProfileRequest
import org.yarokovisty.delivery.common.profile.main.data.model.UserRequest
import org.yarokovisty.delivery.common.profile.main.data.model.UserResponse
import org.yarokovisty.delivery.common.profile.main.domain.entity.User

internal fun UserResponse.toItem(): User =
    User(
        id = id,
        phone = phone,
        firstname = firstname,
        lastname = lastname,
        middlename = middlename,
        email = email,
        city = city
    )

internal fun User.toRequest(): UserRequest =
    UserRequest(
        profile = ProfileRequest(
            firstname = firstname,
            lastname = lastname,
            middlename = middlename,
            email = email,
            city = city
        ),
        phone = phone
    )
