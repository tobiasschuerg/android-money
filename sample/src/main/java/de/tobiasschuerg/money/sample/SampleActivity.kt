package de.tobiasschuerg.money.sample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import de.tobiasschuerg.money.Currencies
import de.tobiasschuerg.money.Currency
import de.tobiasschuerg.money.Money
import de.tobiasschuerg.money.MoneyList
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        // define currencies and their exchange rates
        // (here we take euro as base currency)
        val euro = Currency("EUR", "Euro", 1.0)
        val bitcoin = Currency("XBT", "Bitcoin", 0.000209580) // Oct. 17 2017, 21:00

        // another way of creating a money from predefined
        val usDollar = Currencies.USDOLLAR.withRate(1.17705) // Oct. 17 2017, 21:00


        // create a money object and use it for calculations.
        val savedMoney = Money(1337, euro)
        val birthdayMoney = Money(125, usDollar)
        val availableMoney = savedMoney + birthdayMoney.convertInto(euro)
        log("So in total I've now $availableMoney")

        val bitcoinMoney = savedMoney.convertInto(bitcoin)
        log("I could invest my $availableMoney and get $bitcoinMoney instead")


        // Summing up:
        val moneyList = MoneyList(usDollar)
        moneyList.add(Money(100.01, usDollar))
        moneyList.add(Money(1.27, usDollar))
        moneyList.add(Money(20, usDollar))
        moneyList.add(Money(13.37, usDollar))
        text_view.text = "In total I got: ${moneyList.sum()}"
    }

    private fun log(message: String) {
        Log.i("Money sample", message)
    }
}
