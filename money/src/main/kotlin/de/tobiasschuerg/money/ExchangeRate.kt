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
}
