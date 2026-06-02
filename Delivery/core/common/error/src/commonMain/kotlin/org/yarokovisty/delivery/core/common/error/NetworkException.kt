package org.yarokovisty.delivery.core.common.error

sealed class NetworkException : Exception() {

    class Unauthorized : NetworkException()
    class Unknown : NetworkException()
}
