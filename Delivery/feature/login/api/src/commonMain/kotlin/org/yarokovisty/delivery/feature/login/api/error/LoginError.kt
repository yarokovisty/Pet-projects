package org.yarokovisty.delivery.feature.login.api.error

sealed class LoginError : Throwable() {

    object InvalidOtp : LoginError()

    object Unknown : LoginError()
}
