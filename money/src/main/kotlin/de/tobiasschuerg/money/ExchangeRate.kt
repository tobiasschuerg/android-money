package de.tobiasschuerg.money

import java.math.BigDecimal
import java.math.MathContext

/**
 * Represents a conversion rate from one currency to another.
 * E.g., `ExchangeRate(EUR, USD, 1.09)` means 1 EUR = 1.09 USD.
 */
data class ExchangeRate(
    val from: Currency,
    val to: Currency,
    val rate: BigDecimal,
) {
    constructor(from: Currency, to: Currency, rate: Double) : this(from, to, BigDecimal.valueOf(rate))

    /**
     * Returns the inverse rate (e.g., EUR->USD becomes USD->EUR).
     */
    fun inverse(): ExchangeRate = ExchangeRate(to, from, BigDecimal.ONE.divide(rate, MathContext.DECIMAL128))

    /**
     * Chains this rate with [other] to produce a transitive rate.
     * E.g., `(EUR->USD).chain(USD->JPY)` returns `EUR->JPY`.
     *
     * Requires that this rate's [to] currency matches [other]'s [from] currency.
     */
    fun chain(other: ExchangeRate): ExchangeRate {
        require(to.currencyCode == other.from.currencyCode) {
            "Cannot chain: this rate's target ($to) does not match other rate's source (${other.from})"
        }
        val combined = rate.multiply(other.rate, MathContext.DECIMAL128)
        return ExchangeRate(from, other.to, combined)
    }
}
