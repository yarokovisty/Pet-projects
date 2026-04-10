package org.yarokovisty.delivery.feature.profile.main.impl.data.mapper

import org.yarokovisty.delivery.feature.profile.main.api.domain.entity.User
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.ProfileRequest
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.UserRequest
import org.yarokovisty.delivery.feature.profile.main.impl.data.model.UserResponse

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
