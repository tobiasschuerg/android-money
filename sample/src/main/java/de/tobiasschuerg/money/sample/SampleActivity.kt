package de.tobiasschuerg.money.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import de.tobiasschuerg.money.Currencies
import de.tobiasschuerg.money.Currency
import de.tobiasschuerg.money.Money

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        // define currencies and their exchange rates
        // (here we take euro as base currency)
        val euro = Currency("EUR", "Euro", 1.0)
        val bitcoin = Currency("XBT", "Bitcoin", 0.000209580) // Oct. 17 2017, 21:00
        val usDollar = Currencies.USDOLLAR.withRate(1.17705) // Oct. 17 2017, 21:00


        // create a money object and use it for calculations.
        val savedMoney = Money(1337, euro)
        log("I saved $savedMoney")

        val birthdayMoney = Money(24.75, usDollar)
        log("My aunt from New York send me $birthdayMoney for my birthday")

        val availableMoney = savedMoney + birthdayMoney.convertInto(euro)
        log("So in total I've now $availableMoney")

        val bitcoinMoney = savedMoney.convertInto(bitcoin)
        log("I could invest my $availableMoney and get $bitcoinMoney instead")

    }

    private fun log(message: String) {
        Log.i("Money sample", message)
    }
}
