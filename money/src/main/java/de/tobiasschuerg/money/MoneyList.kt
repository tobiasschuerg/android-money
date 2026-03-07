package de.tobiasschuerg.money

import java.math.BigDecimal

/**
 * List of money which provides additional features such as sum, average, median etc.
 * All entries must be in the same [currency].
 */
@Suppress("TooManyFunctions")
class MoneyList(
    private val currency: Currency,
) : MutableList<Money> {
    private val list: MutableList<Money> = mutableListOf()

    private var sum = Money.ZERO.copy(currency = currency)

    override val size: Int
        get() = list.size

    override fun contains(element: Money) = list.contains(element)

    override fun containsAll(elements: Collection<Money>) = list.containsAll(elements)

    override fun get(index: Int): Money = list[index]

    override fun indexOf(element: Money): Int = list.indexOf(element)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator() = list.iterator()

    override fun lastIndexOf(element: Money): Int = list.lastIndexOf(element)

    override fun listIterator() = list.listIterator()

    override fun listIterator(index: Int) = list.listIterator(index)

    override fun subList(
        fromIndex: Int,
        toIndex: Int,
    ): MoneyList {
        val sublist = MoneyList(currency)
        sublist.addAll(list.subList(fromIndex, toIndex))
        return sublist
    }

    override fun add(element: Money): Boolean {
        list.add(element)
        addToSum(element)
        return true
    }

    override fun add(
        index: Int,
        element: Money,
    ) {
        list.add(index, element)
        addToSum(element)
    }

    private fun addToSum(element: Money) {
        sum += element
    }

    private fun recalculateSum() {
        sum = Money.ZERO.copy(currency = currency)
        list.forEach(this::addToSum)
    }

    private fun subtractFromSum(element: Money) {
        sum -= element
    }

    override fun addAll(
        index: Int,
        elements: Collection<Money>,
    ): Boolean {
        list.addAll(index, elements)
        elements.forEach(this::addToSum)
        return true
    }

    override fun addAll(elements: Collection<Money>): Boolean {
        list.addAll(elements)
        elements.forEach(this::addToSum)
        return true
    }

    override fun clear() {
        list.clear()
        sum = Money.ZERO.copy(currency = currency)
    }

    override fun remove(element: Money): Boolean {
        val removed = list.remove(element)
        if (removed) {
            subtractFromSum(element)
        }
        return removed
    }

    override fun removeAll(elements: Collection<Money>): Boolean {
        val success = list.removeAll(elements.toSet())
        if (success) {
            recalculateSum()
        }
        return success
    }

    override fun removeAt(index: Int): Money {
        val element = list.removeAt(index)
        subtractFromSum(element)
        return element
    }

    override fun retainAll(elements: Collection<Money>): Boolean {
        val removed = list.filterNot { it in elements }
        val success = list.retainAll(elements.toSet())
        removed.forEach(this::subtractFromSum)
        return success
    }

    override fun set(
        index: Int,
        element: Money,
    ): Money {
        val old = list.set(index, element)
        subtractFromSum(old)
        addToSum(element)
        return old
    }

    fun sum(): Money = sum

    fun min(): Money? = list.minByOrNull(Money::amount)

    fun max(): Money? = list.maxByOrNull(Money::amount)

    fun median(): Money? =
        if (list.isNotEmpty()) {
            val index: Int = list.size / 2
            list.sortedBy(Money::amount)[index]
        } else {
            null
        }

    fun average(): Money? =
        if (list.isNotEmpty()) {
            sum / BigDecimal(list.size)
        } else {
            null
        }
}
