package behavioural

import java.math.BigDecimal

/*
What Is the Template Method Pattern?

The Template Method Pattern defines the skeleton of an algorithm in a base class,
allowing subclasses to override specific steps without changing the overall structure.

When to use it:
- You have a standard workflow with customizable steps
- You want to enforce a strict order of operations
- You want to avoid duplicated process logic

Fintech Example:
- A payment processor that always validates → authorises → executes → notifies,
  but each step can be tailored for card vs bank transfer.
*/

fun main() {

    val cardProcessor = CardPaymentProcessor()
    val bankProcessor = BankTransferProcessor()

    println("=== CARD PAYMENT ===")
    cardProcessor.process(
        Payment(
            userId = "user123",
            amount = BigDecimal("100.00"),
            method = "CARD"
        )
    )

    println("\n=== BANK TRANSFER ===")
    bankProcessor.process(
        Payment(
            userId = "user456",
            amount = BigDecimal("500.00"),
            method = "BANK"
        )
    )
}

data class Payment(
    val userId: String,
    val amount: BigDecimal,
    val method: String
)

abstract class PaymentProcessor {

    fun process(payment: Payment) {
        validatePayment(payment)
        authorizePayment(payment)
        executePayment(payment)
        notifyUser(payment)
    }

    protected open fun validatePayment(payment: Payment) {

    }

    protected abstract fun authorizePayment(payment: Payment)

    protected abstract fun executePayment(payment: Payment)

    protected open fun notifyUser(payment: Payment) {
        println("Sending confirmation to ${payment.userId}")
    }
}

class CardPaymentProcessor : PaymentProcessor() {
    override fun authorizePayment(payment: Payment) {
        println("Performing 3DS card authorisation")
    }

    override fun executePayment(payment: Payment) {
        println("Charging card for £${payment.amount}")
    }
}

class BankTransferProcessor : PaymentProcessor() {

    override fun authorizePayment(payment: Payment) {
        println("Checking account limits and authorising via Open Banking")
    }

    override fun executePayment(payment: Payment) {
        println("Initiating bank transfer for £${payment.amount}")
    }

    override fun notifyUser(payment: Payment) {
        println("Sending bank transfer receipt to ${payment.userId}")
    }

}