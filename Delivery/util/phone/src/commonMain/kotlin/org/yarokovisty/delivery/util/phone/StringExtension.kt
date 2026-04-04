package org.yarokovisty.delivery.util.phone

fun String.clearPhoneNumber(): String {
    val regex = Regex("[+() -]")
    return this.replace(regex, "")
}
