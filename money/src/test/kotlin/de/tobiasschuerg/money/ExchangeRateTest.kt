package de.tobiasschuerg.money

import de.tobiasschuerg.money.Currencies.EURO
import de.tobiasschuerg.money.Currencies.USDOLLAR
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class ExchangeRateTest {
    @Test
    fun `test exchange rate creation from Double`() {
        val rate = ExchangeRate(EURO, USDOLLAR, 1.09)
        assertEquals(EURO, rate.from)
        assertEquals(USDOLLAR, rate.to)
        assertEquals(0, BigDecimal.valueOf(1.09).compareTo(rate.rate))
    }

    @Test
    fun `test exchange rate creation from BigDecimal`() {
        val rate = ExchangeRate(EURO, USDOLLAR, BigDecimal("1.09"))
        assertEquals(0, BigDecimal("1.09").compareTo(rate.rate))
    }

    @Test
    fun `test inverse rate`() {
        val eurToUsd = ExchangeRate(EURO, USDOLLAR, 2.0)
        val usdToEur = eurToUsd.inverse()

        assertEquals(USDOLLAR, usdToEur.from)
        assertEquals(EURO, usdToEur.to)
        assertEquals(0.5, usdToEur.rate.toDouble(), 0.0001)
    }

    @Test
    fun `test inverse of inverse returns original rate`() {
        val eurToUsd = ExchangeRate(EURO, USDOLLAR, 1.09)
        val roundTrip = eurToUsd.inverse().inverse()

        assertEquals(eurToUsd.from, roundTrip.from)
        assertEquals(eurToUsd.to, roundTrip.to)
        assertEquals(eurToUsd.rate.toDouble(), roundTrip.rate.toDouble(), 0.0001)
    }

    @Test
    fun `test data class equality`() {
        val rate1 = ExchangeRate(EURO, USDOLLAR, BigDecimal("1.09"))
        val rate2 = ExchangeRate(EURO, USDOLLAR, BigDecimal("1.09"))
        assertEquals(rate1, rate2)
    }
}
