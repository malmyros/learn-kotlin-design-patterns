package behavioural

import java.math.BigDecimal

/*
What Is the State Pattern?

The State Pattern allows an object to alter its behavior when its internal state changes.
The object will delegate behavior to the current state object.

When to use it:
- You want to model workflows like approval or lifecycle stages
- You want to avoid scattered conditionals and enforce state transitions

Fintech Example:
- A loan moves from Draft → Submitted → Approved → Funded → Closed
  with rules on what transitions are allowed.
*/

fun main() {

    val loan = Loan("loan-UK-1001", BigDecimal("5000.00"))

    loan.submit()     // Draft → Submitted
    loan.approve()    // Submitted → Approved
    loan.fund()       // Approved → Funded
    loan.close()      // Funded → Closed
    loan.approve()    // Cannot approve closed loan
}

class Loan(val id: String, val amount: BigDecimal) {
    private var state: LoanState = DraftState(this)

    fun setState(newState: LoanState) {
        println("Loan $id transitioned to ${newState::class.simpleName}")
        state = newState
    }

    fun submit() = state.submit()
    fun approve() = state.approve()
    fun fund() = state.fund()
    fun close() = state.close()
}

interface LoanState {
    fun submit()
    fun approve()
    fun fund()
    fun close()
}

class DraftState(private val loan: Loan) : LoanState {
    override fun submit() {
        println("Loan ${loan.id} submitted")
        loan.setState(SubmittedState(loan))
    }

    override fun approve() = println("Cannot approve a draft loan ${loan.id}")
    override fun fund() = println("Cannot fund a draft loan ${loan.id}")
    override fun close() = println("Draft loan ${loan.id} canceled")
}

class SubmittedState(private val loan: Loan) : LoanState {
    override fun submit() = println("Loan ${loan.id} already submitted")

    override fun approve() {
        println("Loan ${loan.id} approved")
        loan.setState(ApprovedState(loan))
    }

    override fun fund() = println("Cannot fund loan ${loan.id} before approval")
    override fun close() = println("Submitted loan ${loan.id} closed")
}

class ApprovedState(private val loan: Loan) : LoanState {
    override fun submit() = println("Loan ${loan.id} already approved")
    override fun approve() = println("Loan ${loan.id} already approved")

    override fun fund() {
        println("Loan ${loan.id} funded for £${loan.amount}")
        loan.setState(FundedState(loan))
    }

    override fun close() = println("Approved loan ${loan.id} cancelled")
}

class FundedState(private val loan: Loan) : LoanState {
    override fun submit() = println("Loan ${loan.id} already funded")
    override fun approve() = println("Cannot reapprove funded loan ${loan.id}")
    override fun fund() = println("Loan ${loan.id} already funded")

    override fun close() {
        println("Loan ${loan.id} fully repaid and closed")
        loan.setState(ClosedState(loan))
    }
}

class ClosedState(private val loan: Loan) : LoanState {
    override fun submit() = println("Loan ${loan.id} already closed")
    override fun approve() = println("Cannot approve closed loan ${loan.id}")
    override fun fund() = println("Cannot fund closed loan ${loan.id}")
    override fun close() = println("Loan ${loan.id} already closed")
}