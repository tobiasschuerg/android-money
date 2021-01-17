package de.tobiasschuerg.money

import java.text.NumberFormat

/**
 * Static helper for quickly getting currency formatter.
 *
 * Created by tobias on 9/10/17.
 */

object CurrencyHelper {

    private val currencyFormatter = hashMapOf<String, NumberFormat>()

    fun getCurrencyFormatter(currencyCode: CurrencyCode): NumberFormat {
        if (currencyFormatter.containsKey(currencyCode.code)) {
            return currencyFormatter[currencyCode.code]!!
        } else {
            val currency = java.util.Currency.getInstance(currencyCode.code)

            val locales = NumberFormat.getAvailableLocales()

            for (locale in locales) {
                val currencyFormatter = NumberFormat.getCurrencyInstance(locale)

                if (currencyFormatter.currency == currency) {
                    CurrencyHelper.currencyFormatter[currencyCode.code] = currencyFormatter
                    return currencyFormatter
                }
            }
            throw IllegalStateException("Unsupported currency format")
        }
    }
}
