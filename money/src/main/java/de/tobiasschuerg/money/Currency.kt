package de.tobiasschuerg.money

import java.math.BigDecimal
import java.math.MathContext
import java.text.DecimalFormat
import java.text.NumberFormat

data class Currency(
        var currencyCode: CurrencyCode,
        var name: String,
        var rate: BigDecimal = BigDecimal.ONE
) {

    constructor(code: String, name: String) : this(CurrencyCode(code), name)
    constructor(code: String, name: String, rate: Double) : this(CurrencyCode(code), name, BigDecimal(rate))

    operator fun compareTo(another: Currency): Int {
        return this.name.compareTo(another.name)
    }

    fun getFormatter(): NumberFormat {
        try {
            return CurrencyHelper.getCurrencyFormatter(currencyCode)
        } catch (throwable: Throwable) {
            // throwable.printStackTrace()
            return DecimalFormat("#.########## " + currencyCode.getSymbol())
        }
    }

    /**
     * Calculated the conversion rate from this currency into a target currency.
     */
    fun conversionTo(targetCurrency: Currency): BigDecimal {
        return BigDecimal.ONE
                .divide(rate, MathContext.DECIMAL128)
                .multiply(targetCurrency.rate)
    }

    companion object {
        val NONE = Currency(code = "NONE", name = "NONE")
    }

    override fun toString(): String {
        return currencyCode.getSymbol().let { symbol ->
            if (symbol != currencyCode.code) {
                return "$currencyCode: $name ($symbol)"
            } else {
                return "$currencyCode: $name"
            }
        }

    }

}
