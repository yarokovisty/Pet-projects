package org.yarokovisty.delivery.feature.login.domain.error

sealed class LoginError : Throwable() {

    object InvalidOtp : LoginError()

    object Unknown : LoginError()
}
