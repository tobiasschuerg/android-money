package de.tobiasschuerg.money

import de.tobiasschuerg.money.Currencies.EURO
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MoneyUtilsTest {
    @Test
    fun `test sum of money list`() {
        val list =
            listOf(
                Money(10.0, EURO),
                Money(20.0, EURO),
                Money(30.0, EURO),
            )
        val sum = list.sum()
        assertEquals(60.0, sum.toDouble(), 0.001)
        assertEquals(EURO, sum.currency)
    }

    @Test
    fun `test sum of empty list returns ZERO`() {
        val sum = emptyList<Money>().sum()
        assertTrue(sum.isZero())
    }

    @Test
    fun `test sum with zero entries skips them`() {
        val list =
            listOf(
                Money(10.0, EURO),
                Money.ZERO,
                Money(5.0, EURO),
            )
        val sum = list.sum()
        assertEquals(15.0, sum.toDouble(), 0.001)
    }

    @Test
    fun `test toFloat conversion`() {
        val money = Money(12.5, EURO)
        assertEquals(12.5f, money.toFloat())
    }

    @Test
    fun `test toDouble conversion`() {
        val money = Money(12.5, EURO)
        assertEquals(12.5, money.toDouble(), 0.001)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test sum of mixed currencies throws`() {
        val list =
            listOf(
                Money(10.0, EURO),
                Money(20.0, Currencies.USDOLLAR),
            )
        list.sum()
    }
}
