package structural

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/*
What Is the Adapter Pattern?

The Adapter Pattern allows incompatible interfaces
to work together by wrapping an existing class with a new interface.

When to use it:
- You have an interface your system expects
- You need to integrate a third-party or legacy class that doesn't implement that interface
- You don’t want or can't modify the third-party code
 */

fun main() {
    val legacyService = LegacyPaymentService()
    val adapter: PaymentProcessor = LegacyPaymentAdapter(legacyService)
    val result = adapter.pay(BigDecimal("100.00"), "USD")
    println("Payment result: $result")
}

interface PaymentProcessor {
    fun pay(amount: BigDecimal, currencyCode: String): Boolean
}

class LegacyPaymentService {

    fun makePayment(sum: Double, currencyCode: String): Int {
        val locale = Locale.US
        val formatter = NumberFormat.getCurrencyInstance(locale)
        formatter.currency = Currency.getInstance(currencyCode)
        val formattedAmount = formatter.format(sum)

        println("Processing payment of $formattedAmount")
        return 1
    }
}

class LegacyPaymentAdapter(
    private val legacyService: LegacyPaymentService,
) : PaymentProcessor {

    override fun pay(amount: BigDecimal, currencyCode: String): Boolean {
        validateAmountScale(amount, currencyCode)

        val scale = Currency.getInstance(currencyCode).defaultFractionDigits
        val status = legacyService.makePayment(
            sum = amount.setScale(scale, RoundingMode.HALF_UP).toDouble(),
            currencyCode = currencyCode,
        )

        return status == 1
    }
}

fun validateAmountScale(amount: BigDecimal, currencyCode: String) {
    val expectedScale = Currency.getInstance(currencyCode).defaultFractionDigits
    require(amount.scale() <= expectedScale) {
        when (expectedScale) {
            0 -> "Amount for $currencyCode must be a whole number with no decimal places"
            1 -> "Amount for $currencyCode must have at most 1 decimal place"
            else -> "Amount for $currencyCode must have at most $expectedScale decimal places"
        }
    }
}