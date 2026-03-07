package de.tobiasschuerg.money.sample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.tobiasschuerg.money.Currencies
import de.tobiasschuerg.money.ExchangeRate
import de.tobiasschuerg.money.Money
import de.tobiasschuerg.money.MoneyList
import java.math.BigDecimal

@Suppress("MagicNumber")
class SampleActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)

        val euro = Currencies.EURO
        val usDollar = Currencies.USDOLLAR
        val bitcoin = Currencies.BITCOIN

        // define exchange rates
        val eurToUsd = ExchangeRate(euro, usDollar, 1.17705)
        val eurToBtc = ExchangeRate(euro, bitcoin, 0.000209580)

        // create money and convert between currencies
        val savedMoney = Money(1337, euro)
        val birthdayMoney = Money(125, usDollar)
        val birthdayInEuro = birthdayMoney.convertInto(eurToUsd.inverse())
        val availableMoney = savedMoney + birthdayInEuro
        log("So in total I've now $availableMoney")

        val bitcoinMoney = savedMoney.convertInto(eurToBtc)
        log("I could invest my $availableMoney and get $bitcoinMoney instead")

        // Summing up (same currency):
        val moneyList = MoneyList(usDollar)
        moneyList.add(Money(100, usDollar))
        moneyList.add(Money(2L, usDollar))
        moneyList.add(Money(13.37, usDollar))
        moneyList.add(Money(BigDecimal(3.14159265359), usDollar))

        findViewById<TextView>(R.id.text_view).text = "In total I got: ${moneyList.sum()}"
    }

    private fun log(message: String) {
        Log.i("Money sample", message)
    }
}
