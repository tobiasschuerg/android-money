package de.tobiasschuerg.money

import java.text.NumberFormat

/**
 * Static helper for quickly getting currency formatter.
 *
 * Created by tobias on 9/10/17.
 */
object CurrencyHelper {
    private val currencyFormatter: MutableMap<String, NumberFormat> = mutableMapOf()

    fun getCurrencyFormatter(currencyCode: CurrencyCode): NumberFormat =
        currencyFormatter.getOrPut(currencyCode.code) {
            val currency = java.util.Currency.getInstance(currencyCode.code)
            NumberFormat
                .getAvailableLocales()
                .asSequence()
                .map { NumberFormat.getCurrencyInstance(it) }
                .firstOrNull { it.currency == currency }
                ?: error("Unsupported currency format: $currencyCode")
        }
}
