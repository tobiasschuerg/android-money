package de.tobiasschuerg.money

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Represents an amount of money in a specific currency.
 */
@Suppress("TooManyFunctions")
data class Money(
    val amount: BigDecimal = BigDecimal.ZERO,
    val currency: Currency,
) : Comparable<Money> {
    constructor(amount: Double, currency: Currency) : this(BigDecimal(amount), currency)
    constructor(amount: Long, currency: Currency) : this(BigDecimal(amount), currency)

    operator fun plus(money: Money): Money =
        when {
            this.isZero() -> money
            money.isZero() -> this
            else -> {
                requireSameCurrency(money)
                val sum = amount.add(money.amount)
                Money(sum, currency)
            }
        }

    /**
     * Compares amounts. Both values must be in the same currency.
     */
    override operator fun compareTo(other: Money): Int {
        requireSameCurrency(other)
        return amount.compareTo(other.amount)
    }

    override fun toString(): String =
        if (currency == Currency.NONE) {
            val formatter = DecimalFormat("#.########## (no currency)")
            formatter.format(amount)
        } else {
            currency.getFormatter().format(amount)
        }

    /**
     * Convert this money using the given [exchangeRate].
     * The rate's [ExchangeRate.from] must match this money's currency.
     *
     * Example:
     * ```
     * val rate = ExchangeRate(EUR, USD, 1.09)
     * Money(100, EUR).convertInto(rate) // -> Money(109, USD)
     * ```
     */
    fun convertInto(exchangeRate: ExchangeRate): Money {
        require(currency.currencyCode == exchangeRate.from.currencyCode) {
            "ExchangeRate.from (${exchangeRate.from}) does not match currency ($currency)"
        }
        val converted = amount.multiply(exchangeRate.rate, MathContext.DECIMAL128)
        return Money(converted, exchangeRate.to)
    }

    operator fun times(number: Long): Money = times(BigDecimal.valueOf(number))

    operator fun times(number: Double): Money = times(BigDecimal.valueOf(number))

    operator fun times(decimal: BigDecimal): Money {
        val result = amount.multiply(decimal, MathContext.DECIMAL128)
        return Money(result, currency)
    }

    operator fun div(number: Long): Money = div(BigDecimal.valueOf(number))

    operator fun div(number: BigDecimal): Money {
        val result = amount.divide(number, MathContext.DECIMAL128)
        return Money(result, currency)
    }

    operator fun minus(money: Money): Money =
        if (money.isZero()) {
            this
        } else {
            requireSameCurrency(money)
            val result = amount.subtract(money.amount)
            Money(result, currency)
        }

    private fun requireSameCurrency(money: Money) {
        require(currency.currencyCode == money.currency.currencyCode) {
            "Currencies do not match: $currency and ${money.currency}"
        }
    }

    fun isPositive(): Boolean = amount.signum() == 1

    fun isNegative(): Boolean = amount.signum() == -1

    fun isZero(): Boolean = amount.setScale(MoneyConfig.SCALE, RoundingMode.HALF_DOWN).signum() == 0

    fun isNonZero(): Boolean = !isZero()

    fun abs(): Money = Money(amount.abs(), currency)

    override fun equals(other: Any?): Boolean =
        if (other is Money) {
            val codeEquals = currency.currencyCode == other.currency.currencyCode
            val thisAmount = amount.setScale(MoneyConfig.SCALE, RoundingMode.HALF_DOWN)
            val otherAmount = other.amount.setScale(MoneyConfig.SCALE, RoundingMode.HALF_DOWN)
            val amountEquals = thisAmount == otherAmount
            codeEquals && amountEquals
        } else {
            false
        }

    override fun hashCode(): Int {
        var result = amount.setScale(MoneyConfig.SCALE, RoundingMode.HALF_DOWN).hashCode()
        result = 31 * result + currency.currencyCode.hashCode()
        return result
    }

    companion object {
        val ZERO = Money(BigDecimal.ZERO, Currency.NONE)
    }
}
