package de.tobiasschuerg.money

import de.tobiasschuerg.money.Currencies.EURO
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class MoneyArithmeticsTest {


    @Test
    fun signumFunctionsForPositive() {

        val money = Money(1.5, EURO)

        Assert.assertTrue(money.isPositive())
        Assert.assertTrue(money.isNonZero())
        Assert.assertFalse(money.isNegative())
        Assert.assertFalse(money.isZero())
    }

    @Test
    fun signumFunctionsForNegative() {

        val money = Money(-1.5, EURO)

        Assert.assertFalse(money.isPositive())
        Assert.assertTrue(money.isNonZero())
        Assert.assertTrue(money.isNegative())
        Assert.assertFalse(money.isZero())
    }

    @Test
    fun signumFunctionsForZero() {

        val zeroMoney = Money.ZERO

        Assert.assertFalse(zeroMoney.isPositive())
        Assert.assertFalse(zeroMoney.isNonZero())
        Assert.assertFalse(zeroMoney.isNegative())
        Assert.assertTrue(zeroMoney.isZero())
    }

    @Test
    fun abs() {

        val money = Money(7.13, EURO)
        Assert.assertEquals(money.abs().amount.toDouble(), 7.13, 0.001)

        val money2 = Money(-5.23, EURO)
        Assert.assertEquals(money2.abs().amount.toDouble(), 5.23, 0.001)
    }

    @Test
    fun equals() {
        val zero = Money.ZERO.copy(currency = EURO)
        Assert.assertEquals(Money(0, EURO), zero)
        Assert.assertEquals(Money(0.0, EURO), zero)
        Assert.assertEquals(Money(0.00000, EURO), zero)
    }

    @Test
    fun plusMinus() {
        val amount1 = 7.23
        val amount2 = 24.9321
        val money1 = Money(amount1, EURO)
        val money2 = Money(amount2, EURO)

        val result1Plus = Money(amount1 + amount2, EURO)
        val result2Plus = money1 + money2
        Assert.assertEquals(result2Plus, result1Plus)

        val result1Minus = Money(amount1 - amount2, EURO)
        val result2Minus = money1 - money2
        Assert.assertEquals(result2Minus, result1Minus)
    }


    @Test
    fun timesTwo() {
        val amount: Double = 7.23
        val money = Money(amount, EURO)

        val result = (money * 2).amount.toDouble()

        Assert.assertEquals(result, amount * 2, 0.001)
    }

    @Test
    fun timesZero() {
        val money = Money(amount = 7.23, currency = EURO)
        val result = money * 0

        Assert.assertTrue(BigDecimal.ZERO.compareTo(result.amount) == 0)
    }

    @Test
    fun dividedByTwo() {
        val amount = 7.23
        val money = Money(amount, EURO)
        val result = money / 2
        val actual = amount / 2

        Assert.assertEquals(result.amount.toDouble(), actual, 0.001)
    }
}