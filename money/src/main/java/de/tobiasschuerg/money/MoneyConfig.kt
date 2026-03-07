package de.tobiasschuerg.money

/**
 * Configuration for money calculations.
 */
object MoneyConfig {
    /**
     * Scale used for comparing amounts in [Money.equals] and [Money.isZero].
     * @see [java.math.BigDecimal.setScale]
     */
    @Suppress("MagicNumber")
    const val SCALE = 10
}
