package de.tobiasschuerg.money

import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Represents a currency identity (code and name).
 * Does not hold exchange rates — use [Money.convertInto] with an explicit rate.
 */
data class Currency(
    val currencyCode: CurrencyCode,
    val name: String,
) {
    constructor(code: String, name: String) : this(CurrencyCode(code), name)

    operator fun compareTo(another: Currency): Int = this.name.compareTo(another.name)

    fun getFormatter(): NumberFormat =
        try {
            CurrencyHelper.getCurrencyFormatter(currencyCode)
        } catch (throwable: IllegalStateException) {
            println("money: $throwable no currency formatter found for $currencyCode")
            DecimalFormat("#.########## " + currencyCode.getSymbol())
        }

    companion object {
        val NONE = Currency(code = "NONE", name = "NONE")
    }

    override fun toString(): String {
        currencyCode.getSymbol().let { symbol ->
            return if (symbol != currencyCode.code) {
                "$currencyCode: $name ($symbol)"
            } else {
                "$currencyCode: $name"
            }
        }
    }
}
