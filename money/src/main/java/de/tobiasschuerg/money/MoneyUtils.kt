package de.tobiasschuerg.money

fun Money.toFloat(): Float = amount.toFloat()

fun Money.toDouble(): Double = amount.toDouble()

/**
 * Sum all amounts on the collection.
 * Will throw if currencies does not match.
 * @see [sum(currency)]
 */
fun Collection<Money>.sum(): Money {
    return fold(Money.ZERO) { acc, money ->
        if (money.isZero()) acc else acc + money
    }
}

/**
 * Sum all amounts on the collection and auto apply currency conversion.
 */
fun Collection<Money>.sum(currency: Currency): Money {
    return fold(Money.ZERO) { acc, money ->
        if (money.isZero()) acc else acc + money.convertInto(currency)
    }
}
