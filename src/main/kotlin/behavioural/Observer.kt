package behavioural

import java.math.BigDecimal

/*
What Is the Observer Pattern?

The Observer Pattern lets you define a one-to-many relationship between objects so
that when one object changes state, all its dependents are notified automatically.

When to use it:
- You want to decouple event publishers from subscribers
- You need to notify multiple systems about changes
- You want to implement real-time updates or triggers

Fintech Example:
- A transaction processor notifies audit, compliance, and notification systems
  when a financial transaction is completed.
*/

fun main() {

    val processor = TransactionProcessor()
    processor.addObserver(AuditService())
    processor.addObserver(TransactionComplianceService())
    processor.addObserver(NotificationService())

    val txn = Transaction(
        id = "txn-20250707-UK123",
        userId = "user123",
        amount = BigDecimal("900.00"),
        currency = "GBP",
        country = "UK"
    )
    processor.process(txn)
}

data class Transaction(
    val id: String,
    val userId: String,
    val amount: BigDecimal,
    val currency: String,
    val country: String,
)

interface TransactionObserver {
    fun onTransactionCompleted(transaction: Transaction)
}

class TransactionProcessor {
    private val observers = mutableListOf<TransactionObserver>()

    fun addObserver(observer: TransactionObserver) {
        observers.add(observer)
    }

    fun process(transaction: Transaction) {
        println("Processing transaction ${transaction.id} for ${transaction.amount}")
        notifyObservers(transaction)
    }

    private fun notifyObservers(transaction: Transaction) {
        observers.forEach { it.onTransactionCompleted(transaction) }
    }
}

class AuditService : TransactionObserver {
    override fun onTransactionCompleted(transaction: Transaction) {
        println("Audit log saved for transaction ${transaction.id}")
    }
}

class TransactionComplianceService : TransactionObserver {
    override fun onTransactionCompleted(transaction: Transaction) {
        println("Compliance check triggered for ${transaction.id} in ${transaction.country}")
    }
}

class NotificationService : TransactionObserver {
    override fun onTransactionCompleted(transaction: Transaction) {
        println("Transaction notification sent to user ${transaction.userId}")
    }
}

