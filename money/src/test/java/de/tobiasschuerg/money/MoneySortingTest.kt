package de.tobiasschuerg.money

import de.tobiasschuerg.money.Currencies.EURO
import de.tobiasschuerg.money.Currencies.USDOLLAR
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class MoneySortingTest {

    @Test
    fun `test money sorting`() {

        val money1 = Money(2, EURO)
        val money2 = Money(17.11, EURO)
        val money3 = Money(4.25, EURO)

        val moneyListUnsorted = listOf(money1, money2, money3)

        val sortedlist = moneyListUnsorted.sorted()
        assertNotEquals(moneyListUnsorted, sortedlist)

        val moneyListSortedManually = listOf(money1, money3, money2)
        assertEquals(moneyListSortedManually, sortedlist)
    }

    @Test
    fun `test money sorting with different currencies`() {

        val money1 = Money(2, EURO)
        val money2 = Money(2, USDOLLAR)
        val money3 = Money(3, EURO)

        val moneyListUnsorted = listOf(money1, money2, money3)

        val sortedlist = moneyListUnsorted.sorted()
        assertNotEquals(moneyListUnsorted, sortedlist)

        val moneyListSortedManually = listOf(money2, money1, money3)
        assertEquals(moneyListSortedManually, sortedlist)
    }
}
