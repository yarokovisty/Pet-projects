package org.yarokovisty.delivery.util.logger

expect object DeliveryLogger {
    fun e(tag: String, message: String, throwable: Throwable? = null)
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
}
