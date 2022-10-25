package de.tobiasschuerg.money

import java.text.NumberFormat
import java.util.HashMap

/**
 * Static helper for quickly getting currency formatter.
 *
 * Created by tobias on 9/10/17.
 */

object CurrencyHelper {

    private val currencyFormatter: HashMap<String, NumberFormat> = hashMapOf()

    fun getCurrencyFormatter(currencyCode: CurrencyCode): NumberFormat {
        if (currencyFormatter.containsKey(currencyCode.code)) {
            return currencyFormatter.getValue(currencyCode.code)
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
            error("Unsupported currency format")
        }
    }
}
