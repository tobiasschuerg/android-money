[![Release](https://jitpack.io/v/tobiasschuerg/android-money.svg)](https://jitpack.io/#tobiasschuerg/android-money)

# android-money

Simple money and currency library for android (written in kotlin).

## Create Money
Creating money is as easy as:
```kotlin
   // define your currencies
   val euro = Currency("EUR", "Euro", 1.0)
   val bitcoin = Currency("XBT", "Bitcoin", 0.000209580) // Oct. 17 2017, 21:00
   // etc ...
   
   val savedMoney = Money(1358.03, euro)
   log("I saved $savedMoney")
```
> I saved 1.358,03 €



## Convert Money
Also converting between currencies is straight forward:
```kotlin
  val bitcoinMoney = savedMoney.convertInto(bitcoin)
  log("I could invest my $availableMoney and get $bitcoinMoney instead")
```
> I could invest my 1.358,03 € and get 0,28020846 XBT instead



## Money Calculations

### Simple Arithmetics
If using kotlin just use the default operators (+,-,*, /) or in java (`.plus()`, `.minus()`, ...):
```kotlin
        val savedMoney = Money(1337, euro)
        val birthdayMoney = Money(125, usDollar)
        val availableMoney = savedMoney + birthdayMoney.convertInto(euro)
        log("So in total I've now $availableMoney")
```
> So in total I've now 1.358,03 €

### Summing up / Average
For summing up the `MoneyList`can be used:
```kotlin
  val moneyList = MoneyList(usDollar)
  moneyList.add(Money(100.01, usDollar))
  moneyList.add(Money(1.27, usDollar))
  moneyList.add(Money(20, usDollar))
  moneyList.add(Money(13.37, usDollar))
  log("In total I got: ${moneyList.sum()}")
```
>  In total I got: $134.65

# Add Library
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
	dependencies {
      implementation 'com.github.tobiasschuerg:android-money:v0.3'
	}
```
