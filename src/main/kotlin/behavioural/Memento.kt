package behavioural

/*
What Is the Memento Pattern?

The Memento Pattern captures and stores an object's internal state
so it can be restored or inspected later — without violating encapsulation.

When to use it:
- You want to keep a history of an object’s metadata
- You need to inspect previous versions of a profile (e.g., for audits)
- You want to restore form or workflow state without affecting core systems

Fintech Example:
- Save and restore a user's risk profile across time
  as new events or KYC data modify it.
*/

fun main() {

    val userId = "9d5f2ffd-7519-4221-8bb6-9e9f408020d2"
    val customer = Customer(userId, RiskProfile(300, "Medium", listOf("high_velocity", "new_country")))
    val history = RiskProfileHistory()

    history.save(customer)

    customer.updateProfile(RiskProfile(500, "High", listOf("large_txn", "new_ip")))
    history.save(customer)

    customer.updateProfile(RiskProfile(120, "Low", emptyList()))
    println("🔎 Current Profile: ${customer.profile}")

    history.restore(customer)
    println("↩️ Rolled Back to: ${customer.profile}")

    history.restore(customer)
    println("↩️ Rolled Back to: ${customer.profile}")
}

data class RiskProfile(
    val score: Int,
    val riskLevel: String,
    val flags: List<String>
)

class Customer(val userId: String, var profile: RiskProfile) {
    fun updateProfile(profile: RiskProfile) {
        println("Updating risk profile to $profile for user $userId")
        this.profile = profile
    }

    fun createSnapshot(): RiskSnapshot = RiskSnapshot(profile)

    fun restore(snapshot: RiskSnapshot) {
        println("Restoring snapshot ${snapshot.profile}")
        this.profile = snapshot.profile
    }
}

data class RiskSnapshot(val profile: RiskProfile)

class RiskProfileHistory {
    private val snapshots = ArrayDeque<RiskSnapshot>()

    fun save(customer: Customer) {
        snapshots.addLast(customer.createSnapshot())
    }

    fun restore(customer: Customer) {
        if (snapshots.isNotEmpty()) {
            val snapshot = snapshots.removeLast()
            customer.restore(snapshot)
        }
    }
}