package de.tobiasschuerg.money

fun Money.toFloat(): Float = amount.toFloat()

fun Money.toDouble(): Double = amount.toDouble()

/**
 * Sum all amounts in the collection.
 * All entries must be in the same currency.
 */
fun Collection<Money>.sum(): Money =
    fold(Money.ZERO) { acc, money ->
        if (money.isZero()) acc else acc + money
    }
