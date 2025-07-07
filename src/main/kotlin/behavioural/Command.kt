package behavioural

import java.math.BigDecimal

/*
What Is the Command Pattern?

The Command Pattern encapsulates a request as an object, allowing you to parameterize clients with different operations,
queue them, log them, or support undo functionality.

When to use it:
- You want to decouple the sender and receiver of actions
- You need to queue, log, or retry operations (e.g., transactions)
- You want to support undo, audit logs, or delayed execution

Fintech Example:
- A transaction engine where operations like debit, credit, or transfer are represented as command objects
  that can be executed, logged, and potentially undone or replayed for reconciliation.
*/

fun main() {

    val userId = "512cd9bd-3601-43ee-894c-12b5d2199e54"
    val account = BankAccount(userId, BigDecimal(2_000.00))
    println("Initial balance: ${account.balance}")

    val commandQueue: List<BankCommand> = listOf(
        CreditCommand(account, BigDecimal(200.00)),
        DebitCommand(account, BigDecimal(1_500.00)),
        DebitCommand(account, BigDecimal(300.00))
    )

    commandQueue.forEach { command ->
        val result = command.execute()
        println(result.message)
    }

    println("Final balance: ${account.balance}")

}

interface BankCommand {
    fun execute(): CommandResult
}

data class CommandResult(
    val success: Boolean,
    val message: String,
)

class BankAccount(
    val userId: String,
    var balance: BigDecimal,
)

class CreditCommand(
    private val account: BankAccount,
    private val amount: BigDecimal,
) : BankCommand {
    override fun execute(): CommandResult {
        account.balance += amount
        return CommandResult(true, "Credited £${amount} to account ${account.userId}")
    }
}

class DebitCommand(
    private val account: BankAccount,
    private val amount: BigDecimal,
) : BankCommand {
    override fun execute(): CommandResult {
        return if (account.balance >= amount) {
            account.balance -= amount
            CommandResult(true, "Debited £$amount to account ${account.userId}")
        } else {
            CommandResult(false, "Failed to debit £$amount Insufficient funds")
        }
    }
}