package de.tobiasschuerg.money

import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

/**
 * Represents an amount of money in a specific currency.
 *
 * Created by Tobias Schürg on 28.07.2017.
 */
data class Money(val amount: BigDecimal = BigDecimal.ZERO, val currency: Currency) {

    constructor(amount: Double, currency: Currency) : this(BigDecimal(amount), currency)
    constructor(amount: Long, currency: Currency) : this(BigDecimal(amount), currency)

    operator fun plus(money: Money): Money {
        requireSameCurrency(money)
        val sum = amount.add(money.amount)
        return Money(sum, currency)
    }

    operator fun compareTo(money: Money): Int {
        requireSameCurrency(money)
        return this.amount.compareTo(money.amount)
    }

    override fun toString(): String {
        return currency.getFormatter().format(amount)
    }

    fun convertInto(targetCurrency: Currency): Money {
        val rate = currency.conversionTo(targetCurrency)
        return Money(amount.multiply(rate), targetCurrency)
    }

    operator fun times(number: Long): Money {
        return times(BigDecimal.valueOf(number))
    }

    operator fun times(number: Double): Money {
        return times(BigDecimal.valueOf(number))
    }

    operator fun times(decimal: BigDecimal): Money {
        val result = amount.multiply(decimal, MathContext.DECIMAL128)
        return Money(result, currency)
    }

    operator fun div(number: Long): Money {
        return div(BigDecimal.valueOf(number))
    }

    operator fun div(number: BigDecimal): Money {
        val result = amount.divide(number, MathContext.DECIMAL128)
        return Money(result, currency)
    }

    operator fun minus(money: Money): Money {
        requireSameCurrency(money)
        val result = amount.subtract(money.amount)
        return Money(result, currency)
    }

    private fun requireSameCurrency(money: Money) {
        if (currency.currencyCode != money.currency.currencyCode) {
            // don't make any implicit conversions
            throw IllegalArgumentException("Currencies do not match: $currency and ${money.currency}")
        }
    }

    fun isPositive(): Boolean = amount.signum() == 1

    fun isNegative(): Boolean = amount.signum() == -1

    fun isZero(): Boolean {
        return amount.setScale(5, RoundingMode.HALF_DOWN).signum() == 0
    }

    fun isNonZero(): Boolean = !isZero()

    fun abs(): Money = Money(amount.abs(), currency)

    override fun equals(other: Any?): Boolean {
        return if (other is Money) {
            val codeEquals = currency.currencyCode == other.currency.currencyCode
            val amountEquals = amount.setScale(MoneyConfig.scale, RoundingMode.HALF_DOWN) == other.amount.setScale(MoneyConfig.scale, RoundingMode.HALF_DOWN)
            codeEquals && amountEquals
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        var result = amount.hashCode()
        result = 31 * result + currency.hashCode()
        return result
    }

    companion object {
        val ZERO = Money(BigDecimal.ZERO, Currency.NONE)
    }

}
