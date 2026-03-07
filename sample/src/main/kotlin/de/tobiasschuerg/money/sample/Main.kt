package de.tobiasschuerg.money.sample

import de.tobiasschuerg.money.Currencies
import de.tobiasschuerg.money.Currency
import de.tobiasschuerg.money.ExchangeRate
import de.tobiasschuerg.money.Money
import de.tobiasschuerg.money.MoneyList
import java.math.BigDecimal
import java.util.Locale

@Suppress("MagicNumber")
fun main() {
    val euro = Currencies.EURO
    val usDollar = Currencies.USDOLLAR
    val bitcoin = Currencies.BITCOIN

    // --- Create Money ---
    val savedMoney = Money(1337, euro)
    println("I saved $savedMoney")

    // --- Exchange Rates & Conversion ---
    val eurToUsd = ExchangeRate(euro, usDollar, 1.09)
    val eurToBtc = ExchangeRate(euro, bitcoin, 0.000209580)

    val dollars = savedMoney.convertInto(eurToUsd)
    println("That's $dollars")

    val backToEuros = dollars.convertInto(eurToUsd.inverse())
    println("And back: $backToEuros")

    // --- Rate Chaining ---
    val jpy = Currency("JPY", "Japanese Yen")
    val usdToJpy = ExchangeRate(usDollar, jpy, 150.0)
    val eurToJpy = eurToUsd.chain(usdToJpy)
    val yen = savedMoney.convertInto(eurToJpy)
    println("Or in Yen: $yen")

    // --- Arithmetic ---
    val bonus = Money(200, euro)
    val total = savedMoney + bonus
    val half = total / 2
    println("$savedMoney + $bonus = $total, half = $half")

    // --- Unary Minus ---
    val expense = -bonus
    println("Expense: $expense")

    // --- Percentage ---
    val tax = total.percent(19)
    println("19% tax on $total = $tax")

    // --- Rounding ---
    val precise = Money(12.3456, euro)
    println("$precise rounded = ${precise.rounded()}")

    // --- Locale Formatting ---
    println("US format: ${total.format(Locale.US)}")
    println("DE format: ${total.format(Locale.GERMANY)}")

    // --- Money Range ---
    val budget = Money(100, euro)..Money(2000, euro)
    println("Is $total within budget? ${total in budget}")

    // --- MoneyList ---
    val moneyList = MoneyList(usDollar)
    moneyList.add(Money(100, usDollar))
    moneyList.add(Money(2L, usDollar))
    moneyList.add(Money(13.37, usDollar))
    moneyList.add(Money(BigDecimal("3.14"), usDollar))
    println("Sum: ${moneyList.sum()}, Average: ${moneyList.average()}")

    // --- Bitcoin ---
    val btc = savedMoney.convertInto(eurToBtc)
    println("$savedMoney = $btc")
}
