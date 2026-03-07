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
