package de.tobiasschuerg.money

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CurrencyTest {
    @Test
    fun `test currency creation`() {
        val eur = Currency("EUR", "Euro")
        assertEquals(CurrencyCode("EUR"), eur.currencyCode)
        assertEquals("Euro", eur.name)
    }

    @Test
    fun `test currency equality`() {
        val eur1 = Currency("EUR", "Euro")
        val eur2 = Currency("EUR", "Euro")
        assertEquals(eur1, eur2)
    }

    @Test
    fun `test different currencies are not equal`() {
        val eur = Currency("EUR", "Euro")
        val usd = Currency("USD", "US Dollar")
        assertNotEquals(eur, usd)
    }

    @Test
    fun `test compareTo sorts by name`() {
        val eur = Currency("EUR", "Euro")
        val usd = Currency("USD", "US Dollar")
        assertTrue(eur < usd)
    }

    @Test
    fun `test NONE sentinel`() {
        assertEquals("NONE", Currency.NONE.currencyCode.code)
        assertEquals("NONE", Currency.NONE.name)
    }

    @Test
    fun `test toString with known currency includes symbol`() {
        val eur = Currency("EUR", "Euro")
        val str = eur.toString()
        assertTrue(str.contains("EUR"))
        assertTrue(str.contains("Euro"))
    }

    @Test
    fun `test getFormatter returns a formatter`() {
        val eur = Currency("EUR", "Euro")
        val formatter = eur.getFormatter()
        // Should not throw and should return a valid formatter
        val formatted = formatter.format(1234.56)
        assertTrue(formatted.isNotEmpty())
    }
}
