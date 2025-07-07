package behavioural

import java.math.BigDecimal

/*
What Is the Chain of Responsibility Pattern?

The Chain of Responsibility Pattern allows multiple handlers to process a request in a sequential chain,
without the sender needing to know which handler will process it.

When to use it:
- You want to decouple request handling logic and allow different components to handle various tasks
- You need to implement a series of checks or validation rules that can be composed dynamically
- You want to avoid tight coupling between the sender and handler, allowing for easy maintenance and extensions

Fintech Example:
- A payment gateway that validates a transaction through a series of checks:
  - Anti-Money Laundering (AML) checks
  - Fraud detection
  - Daily spending limits
  - Account balance verification
  Each check can fail or pass independently, and further checks can be skipped if an earlier one fails.
*/

fun main() {

    val userId = "462b8bf3-9bd4-417c-9711-8f8591bcf986"
    val balances = mapOf(userId to BigDecimal(2_000))

    val paymentHandlerChain = AmlCheck()
        .setNext(FraudCheck())
        .setNext(DailyCheck(BigDecimal(1_000)))
        .setNext(BalanceCheck(balances))

    val paymentRequest = PaymentRequest(userId, BigDecimal(5_000.00), "UK")
    val result = paymentHandlerChain.handle(paymentRequest)

    if (result.approved) {
        println("Payment Approved")
    } else {
        println("Payment Rejected: ${result.failedCheck}")
    }
}

data class PaymentRequest(
    val userId: String,
    val amount: BigDecimal,
    val country: String
)

data class PaymentValidationResult(
    val approved: Boolean,
    val failedCheck: String? = null
) {

    companion object {
        fun success() = PaymentValidationResult(true, null)
        fun failure(reason: String) = PaymentValidationResult(false, reason)
    }
}

abstract class PaymentHandler {
    private var next: PaymentHandler? = null

    fun setNext(handler: PaymentHandler): PaymentHandler {
        next = handler
        return this
    }

    fun handle(request: PaymentRequest): PaymentValidationResult {
        val result = process(request)
        return if (!result.approved) {
            result
        } else {
            next?.handle(request) ?: PaymentValidationResult.success()
        }
    }

    protected abstract fun process(paymentRequest: PaymentRequest): PaymentValidationResult
}

class AmlCheck : PaymentHandler() {
    override fun process(paymentRequest: PaymentRequest): PaymentValidationResult {
        println("Running AML Check...")
        return if (paymentRequest.country == "SanctionedCountry") {
            PaymentValidationResult.failure("AML Check Failed: Sanctioned country")
        } else {
            PaymentValidationResult.success()
        }
    }
}

class FraudCheck : PaymentHandler() {
    override fun process(paymentRequest: PaymentRequest): PaymentValidationResult {
        println("Running Fraud Check...")
        return if (paymentRequest.amount >= BigDecimal(2000)) {
            PaymentValidationResult.failure("Fraud Check Failed: Amount suspicious")
        } else {
            PaymentValidationResult.success()
        }
    }
}

class DailyCheck(private val dailyLimit: BigDecimal) : PaymentHandler() {
    override fun process(paymentRequest: PaymentRequest): PaymentValidationResult {
        println("Running Daily Check...")
        return if (paymentRequest.amount > dailyLimit) {
            PaymentValidationResult.failure("Daily Limit Exceeded")
        } else {
            PaymentValidationResult.success()
        }
    }
}

class BalanceCheck(private val userBalances: Map<String, BigDecimal>) : PaymentHandler() {
    override fun process(paymentRequest: PaymentRequest): PaymentValidationResult {
        println("Running Balance Check...")
        val balance = userBalances[paymentRequest.userId] ?: return PaymentValidationResult.failure("User not found")
        return if (paymentRequest.amount > balance) {
            PaymentValidationResult.failure("Insufficient Balance")
        } else {
            PaymentValidationResult.success()
        }
    }
}