package creational

import java.math.BigDecimal

/*
What Is the Idiomatic Builder Pattern?

Instead of a traditional builder class, Kotlin leverages features like:
- Named parameters
- Default values
- Data classes

This enables concise, readable object creation without extra boilerplate.

When to use it in Fintech:
- Building simple, immutable domain models like `Transaction`, `CustomerProfile`, or `Card`
- When traditional builders feel too heavy
- When you want clarity without giving up flexibility
*/

fun main() {
    val transaction = Transaction(
        amount = 250.toBigDecimal(),
        currencyCode = "GBP",
        payer = "cust-123",
        payee = "merchant-456",
        description = "Coffee purchase"
    )

    println("Transaction from ${transaction.payer} to ${transaction.payee} for ${transaction.amount} ${transaction.currencyCode}")
}

/**
 * Represents a financial transaction between a payer and a payee.
 *
 * This model is typically used for internal transaction processing within
 * a payment platform, card engine, or core banking system.
 *
 * @property amount The monetary value of the transaction. Must be non-negative and scaled appropriately for the currency.
 * @property currencyCode The ISO 4217 currency code (e.g., "GBP", "USD", "JPY").
 * @property payer The identifier of the party initiating the transaction (e.g., customer ID).
 * @property payee The identifier of the recipient (e.g., merchant ID or account number).
 * @property description An optional description of the transaction (e.g., "Card top-up", "Coffee at Pret").
 */
data class Transaction(
    val amount: BigDecimal,
    val currencyCode: String,
    val payer: String,
    val payee: String,
    val description: String = "",
)
