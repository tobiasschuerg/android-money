package de.tobiasschuerg.money

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat

/**
 * Represents an amount of money in a specific currency.
 *
 * Created by Tobias Schürg on 28.07.2017.
 */
@Suppress("TooManyFunctions")
data class Money(val amount: BigDecimal = BigDecimal.ZERO, val currency: Currency) : Comparable<Money> {

    constructor(amount: Double, currency: Currency) : this(BigDecimal(amount), currency)
    constructor(amount: Long, currency: Currency) : this(BigDecimal(amount), currency)

    operator fun plus(money: Money): Money = when {
        this.isZero() -> money
        money.isZero() -> this
        else -> {
            requireSameCurrency(money)
            val sum = amount.add(money.amount)
            Money(sum, currency)
        }
    }

    override operator fun compareTo(other: Money): Int {
        val thisBaseCurrencyAmount = amount.divide(currency.rate, MathContext.DECIMAL32)
        val otherBaseCurrencyAmount = other.amount.divide(other.currency.rate, MathContext.DECIMAL32)
        return thisBaseCurrencyAmount.compareTo(otherBaseCurrencyAmount)
    }

    override fun toString(): String {
        return if (currency == Currency.NONE) {
            val formatter = DecimalFormat("#.########## (no currency)")
            formatter.format(amount)
        } else {
            currency.getFormatter().format(amount)
        }
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
        return if (money.isZero()) this
        else {
            requireSameCurrency(money)
            val result = amount.subtract(money.amount)
            Money(result, currency)
        }
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
        return amount.setScale(MoneyConfig.scale, RoundingMode.HALF_DOWN).signum() == 0
    }

    fun isNonZero(): Boolean = !isZero()

    fun abs(): Money = Money(amount.abs(), currency)

    override fun equals(other: Any?): Boolean {
        return if (other is Money) {
            val codeEquals = currency.currencyCode == other.currency.currencyCode
            val thisAmount = amount.setScale(MoneyConfig.scale, RoundingMode.HALF_DOWN)
            val otherAmount = other.amount.setScale(MoneyConfig.scale, RoundingMode.HALF_DOWN)
            val amountEquals = thisAmount == otherAmount
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
