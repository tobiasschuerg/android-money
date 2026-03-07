package de.tobiasschuerg.money

import de.tobiasschuerg.money.Currencies.EURO
import de.tobiasschuerg.money.Currencies.USDOLLAR
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.RoundingMode
import java.util.Locale

class MoneyFeaturesTest {
    // --- Unary minus ---

    @Test
    fun `test unary minus negates positive`() {
        val money = Money(10.0, EURO)
        val negated = -money
        assertEquals(-10.0, negated.toDouble(), 0.001)
        assertEquals(EURO, negated.currency)
    }

    @Test
    fun `test unary minus negates negative`() {
        val money = Money(-5.0, EURO)
        val negated = -money
        assertEquals(5.0, negated.toDouble(), 0.001)
    }

    @Test
    fun `test unary minus of zero is zero`() {
        val money = Money(0.0, EURO)
        assertTrue((-money).isZero())
    }

    // --- Rounding ---

    @Test
    fun `test rounding to 2 decimal places`() {
        val money = Money(12.3456, EURO)
        val rounded = money.rounded()
        assertEquals(12.35, rounded.toDouble(), 0.001)
    }

    @Test
    fun `test rounding to 0 decimal places`() {
        val money = Money(12.7, EURO)
        val rounded = money.rounded(0)
        assertEquals(13.0, rounded.toDouble(), 0.001)
    }

    @Test
    fun `test rounding with FLOOR mode`() {
        val money = Money(12.999, EURO)
        val rounded = money.rounded(2, RoundingMode.FLOOR)
        assertEquals(12.99, rounded.toDouble(), 0.001)
    }

    @Test
    fun `test rounding preserves currency`() {
        val money = Money(12.3456, EURO)
        assertEquals(EURO, money.rounded().currency)
    }

    // --- Percentage ---

    @Test
    fun `test 15 percent of 200`() {
        val money = Money(200.0, EURO)
        val result = money.percent(15)
        assertEquals(30.0, result.toDouble(), 0.001)
    }

    @Test
    fun `test 100 percent returns same amount`() {
        val money = Money(50.0, EURO)
        val result = money.percent(100)
        assertEquals(50.0, result.toDouble(), 0.001)
    }

    @Test
    fun `test 0 percent returns zero`() {
        val money = Money(50.0, EURO)
        val result = money.percent(0)
        assertTrue(result.isZero())
    }

    @Test
    fun `test fractional percentage`() {
        val money = Money(1000.0, EURO)
        val result = money.percent(7.5)
        assertEquals(75.0, result.toDouble(), 0.001)
    }

    @Test
    fun `test percentage preserves currency`() {
        val money = Money(100.0, EURO)
        assertEquals(EURO, money.percent(10).currency)
    }

    // --- Locale formatting ---

    @Test
    fun `test format with US locale`() {
        val money = Money(1234.56, USDOLLAR)
        val formatted = money.format(Locale.US)
        assertTrue(formatted.contains("1,234.56") || formatted.contains("1234.56"))
        assertTrue(formatted.contains("$"))
    }

    @Test
    fun `test format with German locale`() {
        val money = Money(1234.56, EURO)
        val formatted = money.format(Locale.GERMANY)
        assertTrue(formatted.isNotEmpty())
    }

    @Test
    fun `test format with unknown currency falls back`() {
        val bitcoin = Currencies.BITCOIN
        val money = Money(1.5, bitcoin)
        val formatted = money.format(Locale.US)
        assertTrue(formatted.contains("XBT"))
    }

    // --- MoneyRange ---

    @Test
    fun `test money in range`() {
        val range = Money(100.0, EURO)..Money(500.0, EURO)
        assertTrue(Money(250.0, EURO) in range)
    }

    @Test
    fun `test money at range boundaries`() {
        val range = Money(100.0, EURO)..Money(500.0, EURO)
        assertTrue(Money(100.0, EURO) in range)
        assertTrue(Money(500.0, EURO) in range)
    }

    @Test
    fun `test money outside range`() {
        val range = Money(100.0, EURO)..Money(500.0, EURO)
        assertFalse(Money(50.0, EURO) in range)
        assertFalse(Money(600.0, EURO) in range)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test range with different currencies throws`() {
        Money(100.0, EURO)..Money(500.0, USDOLLAR)
    }
}
