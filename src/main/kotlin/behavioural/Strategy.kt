package behavioural

import java.math.BigDecimal

/*
What Is the Strategy Pattern?

The Strategy Pattern defines a family of algorithms,
encapsulates each one, and makes them interchangeable at runtime.

When to use it:
- You have multiple algorithms that vary based on region, product, or policy
- You want to avoid large conditionals
- You want to inject logic dynamically (e.g., via config)

Fintech Example:
- Fee calculation logic differs by region:
  UK, EU, and US have different fee models.
*/

fun main() {

    val transactions = listOf(
        Transaction("txn-001", "user-001", BigDecimal("500"), "GBP", "UK"),
        Transaction("txn-001", "user-001", BigDecimal("1500"), "EUR", "EU"),
        Transaction("txn-001", "user-001", BigDecimal("300"), "USD", "US"),
    )

    val feeCalculator = FeeCalculator()
    transactions.forEach { transaction ->
        val strategy = when (transaction.country) {
            "UK" -> UKFeeStrategy()
            "EU" -> EUFeeStrategy()
            "US" -> USFeeStrategy()
            else -> throw IllegalArgumentException("Unsupported region")
        }

        feeCalculator.setStrategy(strategy)

        val fee = feeCalculator.calculateFee(transaction)
        println("Transaction in ${transaction.country}: amount = ${transaction.amount}, fee = $fee")

    }
}

interface FeeStrategy {
    fun calculateFee(transaction: Transaction): BigDecimal
}

class UKFeeStrategy : FeeStrategy {
    override fun calculateFee(transaction: Transaction): BigDecimal {
        return transaction.amount.multiply(BigDecimal("0.015"))
    }
}

class EUFeeStrategy : FeeStrategy {
    override fun calculateFee(transaction: Transaction): BigDecimal {
        return if (transaction.amount <= BigDecimal("1000")) {
            transaction.amount.multiply(BigDecimal("0.01"))
        } else {
            transaction.amount.multiply(BigDecimal("0.02"))
        }
    }
}

class USFeeStrategy : FeeStrategy {
    override fun calculateFee(transaction: Transaction): BigDecimal {
        return BigDecimal("2.50").add(transaction.amount.multiply(BigDecimal("0.0125")))
    }
}

class FeeCalculator {
    private lateinit var strategy: FeeStrategy

    fun setStrategy(strategy: FeeStrategy) {
        this.strategy = strategy
    }

    fun calculateFee(transaction: Transaction): BigDecimal {
        return strategy.calculateFee(transaction)
    }
}