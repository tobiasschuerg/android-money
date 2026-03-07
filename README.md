[![Release](https://jitpack.io/v/tobiasschuerg/android-money.svg)](https://jitpack.io/#tobiasschuerg/android-money)

# kotlin-money

Simple money and currency library for Kotlin/JVM.

## Create Money
```kotlin
val euro = Currency("EUR", "Euro", 1.0)
val bitcoin = Currency("XBT", "Bitcoin", 0.000209580)

val savedMoney = Money(1358.03, euro)
println("I saved $savedMoney")
```
> I saved 1.358,03 EUR

## Convert Money
```kotlin
val bitcoinMoney = savedMoney.convertInto(bitcoin)
println("I could invest my $savedMoney and get $bitcoinMoney instead")
```
> I could invest my 1.358,03 EUR and get 0,28020846 XBT instead

## Money Calculations

### Arithmetics
Use Kotlin operators (`+`, `-`, `*`, `/`):
```kotlin
val savedMoney = Money(1337, euro)
val birthdayMoney = Money(125, usDollar)
val availableMoney = savedMoney + birthdayMoney.convertInto(euro)
println("So in total I've now $availableMoney")
```

### Summing up / Average
```kotlin
val moneyList = MoneyList(usDollar)
moneyList.add(Money(100.01, usDollar))
moneyList.add(Money(1.27, usDollar))
moneyList.add(Money(20, usDollar))
moneyList.add(Money(13.37, usDollar))
println("In total I got: ${moneyList.sum()}")
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
