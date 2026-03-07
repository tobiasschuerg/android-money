[![Release](https://jitpack.io/v/tobiasschuerg/android-money.svg)](https://jitpack.io/#tobiasschuerg/android-money)

# kotlin-money

Simple money and currency library for Kotlin/JVM.

## Create Money
```kotlin
val euro = Currencies.EURO
val usDollar = Currencies.USDOLLAR

val savedMoney = Money(1358.03, euro)
println("I saved $savedMoney")
```
> I saved 1.358,03 EUR

## Convert Money
Define exchange rates explicitly and convert:
```kotlin
val eurToUsd = ExchangeRate(euro, usDollar, 1.09)

val dollars = savedMoney.convertInto(eurToUsd)
println("That's $dollars")

// Convert back using the inverse rate
val backToEuros = dollars.convertInto(eurToUsd.inverse())
```

### Chain Exchange Rates
Compose multiple rates into a single transitive rate:
```kotlin
val jpy = Currency("JPY", "Japanese Yen")
val eurToUsd = ExchangeRate(euro, usDollar, 1.10)
val usdToJpy = ExchangeRate(usDollar, jpy, 150.0)

val eurToJpy = eurToUsd.chain(usdToJpy) // EUR -> JPY at 165.0
val yen = Money(100, euro).convertInto(eurToJpy)
```

## Money Calculations

### Arithmetics
Use Kotlin operators (`+`, `-`, `*`, `/`):
```kotlin
val savings = Money(1337, euro)
val bonus = Money(200, euro)
val total = savings + bonus
val half = total / 2
```

### Summing up / Average
```kotlin
val moneyList = MoneyList(usDollar)
moneyList.add(Money(100.01, usDollar))
moneyList.add(Money(1.27, usDollar))
moneyList.add(Money(20, usDollar))
moneyList.add(Money(13.37, usDollar))
println("Sum: ${moneyList.sum()}")
println("Average: ${moneyList.average()}")
```

Or use the extension function on any collection:
```kotlin
val total = listOf(money1, money2, money3).sum()
```

### Percentage
```kotlin
val price = Money(200, euro)
val tax = price.percent(19)       // 38 EUR
val tip = price.percent(7.5)      // 15 EUR
```

### Rounding
```kotlin
val precise = Money(12.3456, euro)
val rounded = precise.rounded()               // 12.35 EUR (2 decimals, HALF_UP)
val floored = precise.rounded(2, RoundingMode.FLOOR)  // 12.34 EUR
val integer = precise.rounded(0)              // 12 EUR
```

### Unary Minus
```kotlin
val income = Money(500, euro)
val expense = -income  // -500 EUR
```

### Money Range
Check if an amount falls within a budget:
```kotlin
val budget = Money(100, euro)..Money(500, euro)
val price = Money(250, euro)
if (price in budget) println("Within budget!")
```

### Locale Formatting
```kotlin
val price = Money(1234.56, usDollar)
println(price.format(Locale.US))       // $1,234.56
println(price.format(Locale.GERMANY))  // 1.234,56 $
```

## Add Library
Step 1. Add the JitPack repository to your build file:
```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}
```
Step 2. Add the dependency:
```kotlin
dependencies {
    implementation("com.github.tobiasschuerg:android-money:1.0.0")
}
```

## Breaking Changes in 1.0.0

This release converts the library from an Android library to a plain **Kotlin/JVM** library and introduces several breaking API changes.

### Library is now Kotlin/JVM
The library no longer depends on the Android SDK. It can be used in any Kotlin/JVM project (Android, backend, desktop).

### Currency no longer holds exchange rates
Previously, `Currency` bundled an exchange rate, making it ambiguous and hard to reuse:
```kotlin
// Before (0.x)
val usd = Currency("USD", "US Dollar", 1.09)
val dollars = euros.convertInto(usd)
```

Now, `Currency` is a pure identity type. Exchange rates are modeled explicitly:
```kotlin
// After (1.0.0)
val usd = Currency("USD", "US Dollar")
val eurToUsd = ExchangeRate(Currencies.EURO, usd, 1.09)
val dollars = euros.convertInto(eurToUsd)
```

### compareTo requires same currency
`Money.compareTo()` previously compared across currencies using implicit rates. It now requires both values to be in the same currency and throws `IllegalArgumentException` otherwise.

### MoneyList no longer supports autoConvert
The `autoConvert` parameter has been removed. Convert money to the target currency before adding it to the list.

### Removed APIs
- `Currency.rate`, `Currency.conversionTo()`, `Currency.withRate()`
- `Collection<Money>.sum(currency)` (cross-currency sum)
