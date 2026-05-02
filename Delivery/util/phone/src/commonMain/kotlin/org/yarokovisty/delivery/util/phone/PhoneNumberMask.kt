package org.yarokovisty.delivery.util.phone

enum class PhoneNumberMask(
    val mask: String,
    val countryCode: Char,
    val oldCountryCode: Char? = null,
) {
    RU(
        mask = "+# ### ### ## ##",
        countryCode = '7',
        oldCountryCode = '8',
    );

    fun normalize(digits: String): String {
        if (digits.isEmpty()) return ""

        return when (digits.first()) {
            oldCountryCode -> countryCode + digits.substring(1)
            countryCode -> digits
            else -> countryCode + digits
        }
    }
}
