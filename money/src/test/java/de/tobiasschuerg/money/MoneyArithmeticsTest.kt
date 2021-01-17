package de.tobiasschuerg.money

import de.tobiasschuerg.money.Currencies.EURO
import de.tobiasschuerg.money.Currencies.USDOLLAR
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.math.BigDecimal

class MoneyArithmeticsTest {

    @Test
    fun `test signum functions on positive money`() {

        val money = Money(5, EURO)

        assertTrue(money.isPositive())
        assertTrue(money.isNonZero())
        assertFalse(money.isNegative())
        assertFalse(money.isZero())
    }

    @Test
    fun `test signum functions on negative money`() {

        val money = Money(-1.2, EURO)

        assertFalse(money.isPositive())
        assertTrue(money.isNonZero())
        assertTrue(money.isNegative())
        assertFalse(money.isZero())
    }

    @Test
    fun `test signum functions on zero money`() {
        assertFalse(Money.ZERO.isPositive())
        assertFalse(Money.ZERO.isNonZero())
        assertFalse(Money.ZERO.isNegative())
        assertTrue(Money.ZERO.isZero())
    }

    @Test
    fun `test that abs() function works`() {
        val money = Money(7.13, EURO)
        assertEquals(money.abs().amount.toDouble(), 7.13, 0.001)

        val money2 = Money(-5.23, EURO)
        assertEquals(money2.abs().amount.toDouble(), 5.23, 0.001)
    }

    @Test
    fun `test that equals works with decimals`() {
        val zero = Money.ZERO.copy(currency = EURO)
        assertEquals(Money(0, EURO), zero)
        assertEquals(Money(0.0, EURO), zero)
        assertEquals(Money(0.00000, EURO), zero)
    }

    @Test
    fun `test addition and subtraction`() {
        val amount1 = 7.23
        val amount2 = 24.9321
        val money1 = Money(amount1, EURO)
        val money2 = Money(amount2, EURO)

        val result1Plus = Money(amount1 + amount2, EURO)
        val result2Plus = money1 + money2
        assertEquals(result2Plus, result1Plus)

        val result1Minus = Money(amount1 - amount2, EURO)
        val result2Minus = money1 - money2
        assertEquals(result2Minus, result1Minus)
    }

    @Test
    fun `test multiplying by two`() {
        val amount = 7.23
        val money = Money(amount, EURO)

        val result = (money * 2).amount.toDouble()

        assertEquals(result, amount * 2, 0.001)
    }

    @Test
    fun `test multiplication by zero`() {
        val money = Money(amount = 7.23, currency = EURO)
        val result = money * 0

        assertTrue(BigDecimal.ZERO.compareTo(result.amount) == 0)
    }

    @Test
    fun `test division`() {
        val amount = 7.23
        val money = Money(amount, EURO)
        val moneyResult = money / 2
        val actualResult = amount / 2

        assertEquals(moneyResult.amount.toDouble(), actualResult, 0.001)
    }

    @Test
    fun `test that money conversion works`() {
        val initialAmount = 7.23
        val euros = Money(initialAmount, EURO)
        val dollarConverted = euros.convertInto(USDOLLAR)
        val dollarCalculated = Money(initialAmount * USDOLLAR.rate.toFloat(), USDOLLAR)

        assertEquals(dollarConverted.toDouble(), dollarCalculated.toDouble(), 0.0001)
        assertEquals(dollarConverted.toFloat(), dollarCalculated.toFloat())
    }
}
