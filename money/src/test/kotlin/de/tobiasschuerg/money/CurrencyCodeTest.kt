package de.tobiasschuerg.money

import org.junit.Assert.assertEquals
import org.junit.Test

class CurrencyCodeTest {
    @Test
    fun `test known currency code returns symbol`() {
        val usd = CurrencyCode("USD")
        assertEquals("$", usd.getSymbol())
    }

    @Test
    fun `test unknown currency code falls back to code string`() {
        val unknown = CurrencyCode("XYZ")
        assertEquals("XYZ", unknown.getSymbol())
    }

    @Test
    fun `test data class equality`() {
        val code1 = CurrencyCode("EUR")
        val code2 = CurrencyCode("EUR")
        assertEquals(code1, code2)
    }

    @Test
    fun `test forDefaultLocale returns a code`() {
        val code = CurrencyCode.forDefaultLocale()
        // Should return a non-empty code based on system locale
        assertEquals(3, code.code.length)
    }
}
