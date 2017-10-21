package de.tobiasschuerg.money

/**
 * List of money which provides additional features such as sum, average, median etc...
 *
 * Created by Tobias Schürg on 18.10.2017.
 */
class MoneyList(private val currency: Currency, autoTransform: Boolean = false) : MutableList<Money> {

    private val list = mutableListOf<Money>()

    private var sum = Money.ZERO

    override val size = list.size

    override fun contains(element: Money) = list.contains(element)

    override fun containsAll(elements: Collection<Money>) = list.containsAll(elements)

    override fun get(index: Int): Money = list.get(index)

    override fun indexOf(element: Money): Int = list.indexOf(element)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator() = list.iterator()

    override fun lastIndexOf(element: Money): Int = list.lastIndexOf(element)

    override fun listIterator() = list.listIterator()

    override fun listIterator(index: Int) = list.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<Money> = subList(fromIndex, toIndex)

    override fun add(element: Money): Boolean {
        list.add(element)
        sum += element
        return true
    }

    override fun add(index: Int, element: Money) {
        list.add(index, element)
        sum += element
    }

    override fun addAll(index: Int, elements: Collection<Money>): Boolean {
        list.addAll(index, elements)
        elements.forEach { sum += it }
        return true
    }

    override fun addAll(elements: Collection<Money>): Boolean {
        list.addAll(elements)
        elements.forEach { sum += it }
        return true
    }

    override fun clear() {
        list.clear()
        sum = Money.ZERO
    }

    override fun remove(element: Money): Boolean {
        list.remove(element)
        sum -= element
        return true
    }

    override fun removeAll(elements: Collection<Money>): Boolean {
        val success = list.removeAll(elements)
        elements.forEach { sum -= it }
        return success
    }

    override fun removeAt(index: Int): Money {
        val element = list.removeAt(index)
        sum -= element
        return element
    }

    override fun retainAll(elements: Collection<Money>): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun set(index: Int, element: Money): Money {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}