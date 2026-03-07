package de.tobiasschuerg.money

/**
 * Configuration for proper calculations.
 *
 * Maybe make this a data class and force initialization before usage?
 *
 * Created by Tobias Schürg on 21.10.2017.
 */
object MoneyConfig {
    /**
     * Scale for comparing.
     * @see [java.math.BigDecimal.setScale]
     */
    @Suppress("MagicNumber")
    var scale = 10
}
