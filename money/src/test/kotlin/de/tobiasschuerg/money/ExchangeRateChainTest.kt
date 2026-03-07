package de.tobiasschuerg.money

import de.tobiasschuerg.money.Currencies.EURO
import de.tobiasschuerg.money.Currencies.USDOLLAR
import org.junit.Assert.assertEquals
import org.junit.Test

class ExchangeRateChainTest {
    private val jpy = Currency("JPY", "Japanese Yen")

    @Test
    fun `test chain EUR to USD to JPY`() {
        val eurToUsd = ExchangeRate(EURO, USDOLLAR, 1.10)
        val usdToJpy = ExchangeRate(USDOLLAR, jpy, 150.0)

        val eurToJpy = eurToUsd.chain(usdToJpy)

        assertEquals(EURO, eurToJpy.from)
        assertEquals(jpy, eurToJpy.to)
        assertEquals(165.0, eurToJpy.rate.toDouble(), 0.01)
    }

    @Test
    fun `test chained rate produces correct conversion`() {
        val eurToUsd = ExchangeRate(EURO, USDOLLAR, 1.10)
        val usdToJpy = ExchangeRate(USDOLLAR, jpy, 150.0)
        val eurToJpy = eurToUsd.chain(usdToJpy)

        val euros = Money(100.0, EURO)
        val yen = euros.convertInto(eurToJpy)

        assertEquals(16500.0, yen.toDouble(), 0.01)
        assertEquals(jpy, yen.currency)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test chain with mismatched currencies throws`() {
        val eurToUsd = ExchangeRate(EURO, USDOLLAR, 1.10)
        val eurToJpy = ExchangeRate(EURO, jpy, 165.0)

        eurToUsd.chain(eurToJpy) // should throw: USD != EUR
    }

    @Test
    fun `test chain is associative`() {
        val gbp = Currency("GBP", "British Pound")
        val eurToUsd = ExchangeRate(EURO, USDOLLAR, 1.10)
        val usdToGbp = ExchangeRate(USDOLLAR, gbp, 0.79)
        val gbpToJpy = ExchangeRate(gbp, jpy, 190.0)

        val leftFirst = eurToUsd.chain(usdToGbp).chain(gbpToJpy)
        val rightFirst = eurToUsd.chain(usdToGbp.chain(gbpToJpy))

        assertEquals(leftFirst.rate.toDouble(), rightFirst.rate.toDouble(), 0.0001)
        assertEquals(leftFirst.from, rightFirst.from)
        assertEquals(leftFirst.to, rightFirst.to)
    }
}
