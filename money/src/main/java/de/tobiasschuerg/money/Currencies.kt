package de.tobiasschuerg.money

/**
 * Predefined currencies without exchange rates.
 * Use [Currency.withRate] to set rates for your use case.
 */
object Currencies {
    val EURO = Currency("EUR", "Euro")
    val USDOLLAR = Currency("USD", "United States Dollar")
}
