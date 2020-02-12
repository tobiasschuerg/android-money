package de.tobiasschuerg.money

/**
 * Predefined currencies.
 *
 * TODO: create a script which auto-creates this file with current exchange rates.
 *
 * Created by Tobias Schürg on 18.10.2017.
 */

object Currencies {

    val EURO = Currency("EUR", "Euro", 1.0) // base rate; of course we're europe
    val USDOLLAR = Currency("USD", "United States Dollar", 1.09) // as of 12.02.2019

    // ...
}
