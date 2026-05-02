package org.yarokovisty.delivery.util.phone

class PhoneNumberFormatter(private val phoneNumberMask: PhoneNumberMask) {

    private companion object {
        const val PLACEHOLDER = '#'
    }

    val maxDigits: Int = phoneNumberMask.mask.count { it == PLACEHOLDER }

    fun format(phoneNumber: String): String {
        if (phoneNumber.isEmpty()) return ""

        val normalizedDigits = phoneNumber
            .clearPhoneNumber()
            .let(::normalize)
            .take(maxDigits)

        val result = StringBuilder()
        var index = 0

        for (maskChar in phoneNumberMask.mask) {
            if (index >= normalizedDigits.length) break

            if (maskChar == PLACEHOLDER) {
                result.append(normalizedDigits[index])
                index++
            } else {
                result.append(maskChar)
            }
        }

        return result.toString()
    }

    fun unformat(formatted: String): String = formatted.clearPhoneNumber()

    fun normalize(digits: String): String = phoneNumberMask.normalize(digits)
}
