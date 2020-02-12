package de.tobiasschuerg.money

/**
 * Configuration for proper calculations.
 *
 * TODO: Make this a data class and for initialization before usage?
 *
 * Created by Tobias Schürg on 21.10.2017.
 */
object MoneyConfig {

    /**
     * Scale for comparing.
     * See **BigDecimal.setScale**
     */
    @Suppress("MagicNumber")
    var scale = 10
}
