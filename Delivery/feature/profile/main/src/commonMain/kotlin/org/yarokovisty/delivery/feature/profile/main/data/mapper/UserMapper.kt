package org.yarokovisty.delivery.feature.profile.main.data.mapper

import org.yarokovisty.delivery.feature.profile.main.domain.entity.User

internal fun org.yarokovisty.delivery.feature.profile.main.data.model.UserResponse.toItem(): User =
    User(
        id = id,
        phone = phone,
        firstname = firstname,
        lastname = lastname,
        middlename = middlename,
        email = email,
        city = city
    )

internal fun User.toRequest(): org.yarokovisty.delivery.feature.profile.main.data.model.UserRequest =
    _root_ide_package_.org.yarokovisty.delivery.feature.profile.main.data.model.UserRequest(
        profile = _root_ide_package_.org.yarokovisty.delivery.feature.profile.main.data.model.ProfileRequest(
            firstname = firstname,
            lastname = lastname,
            middlename = middlename,
            email = email,
            city = city
        ),
        phone = phone
    )
