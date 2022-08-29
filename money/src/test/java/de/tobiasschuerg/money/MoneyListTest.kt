package de.tobiasschuerg.money

import de.tobiasschuerg.money.Currencies.EURO
import org.junit.Assert
import org.junit.Test

/**
 * Created by Tobias Schürg on 05.11.2017.
 */
class MoneyListTest {

    private val currency = EURO
    private val list = MoneyList(EURO)

    init {
        list.add(Money(3, currency))
        list.add(Money(2, currency))
        list.add(Money(6, currency))
        list.add(Money(1, currency)) // min
        list.add(Money(4, currency))
        list.add(Money(9, currency)) // max
        list.add(Money(7, currency))
        list.add(Money(5, currency))
        list.add(Money(8, currency))
    }

    @Test
    fun test() {
        Assert.assertEquals(9, list.size)
        list.add(Money(13, currency))
        Assert.assertEquals(10, list.size)

        val sum: Money = list.sum()
        Assert.assertEquals(58, sum.amount.intValueExact())
    }

    @Test
    fun minMax() {
        Assert.assertEquals(Money(1, currency), list.min())
        Assert.assertEquals(Money(9, currency), list.max())
        Assert.assertEquals(Money(5, currency), list.average())
        Assert.assertEquals(Money(5, currency), list.median())
    }

    @Test
    fun minMaxEmptyList() {
        val emptyList = MoneyList(currency)
        Assert.assertNull(emptyList.min())
        Assert.assertNull(emptyList.max())
        Assert.assertNull(emptyList.average())
        Assert.assertNull(emptyList.median())
    }

    @Test
    fun operations() {
        val sublist = list.subList(0, 5)
        Assert.assertEquals(5, sublist.size)

        Assert.assertEquals(1, sublist.min()?.amount?.intValueExact())
        Assert.assertEquals(6, sublist.max()?.amount?.intValueExact())
    }

    @Test
    fun `test that sum of same currencies is calculated correctly`() {
        val result = list.sum()
        Assert.assertEquals(45.00, result.amount.toDouble(), 0.01)
    }

    @Test
    fun `test that sum of different currencies is calculated correctly`() {
        val list2: List<Money> = list.plus(Money(5.37, Currencies.USDOLLAR))
        val result = list2.sum(EURO)
        Assert.assertEquals(49.93, result.amount.toDouble(), 0.01)
    }
}
