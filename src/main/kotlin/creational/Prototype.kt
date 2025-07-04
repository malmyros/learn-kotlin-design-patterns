package creational

/*
What Is the Prototype Pattern?

The Prototype Pattern creates new objects by cloning existing ones,
rather than instantiating new instances from scratch.

When to use it:
- You have complex or costly-to-create objects
- You want to duplicate templates with small variations
- You need to isolate copies for safe mutation without affecting the original

In Fintech:
- Clone pre-configured risk profiles with default thresholds and limits
- Create regional variants of fraud rules or compliance checks
- Generate synthetic customer risk data for sandbox or simulation environments
 */
fun main() {

    val defaultProfile = RiskProfile(
        name = "Standard Risk Profile",
        maxDailyTransaction = 1000,
        maxMonthlyTransaction = 10000,
        kycLevel = "Basic"
    )

    println("Original: $defaultProfile")

    // Clone and customize
    val vipProfile = defaultProfile.clone().copy(
        name = "VIP Risk Profile",
        maxDailyTransaction = 10000,
        kycLevel = "Enhanced"
    )

    println("Cloned VIP: $vipProfile")

}

interface Prototype<T> {
    fun clone(): T
}

data class RiskProfile(
    val name: String,
    val maxDailyTransaction: Int,
    val maxMonthlyTransaction: Int,
    val kycLevel: String,
) : Prototype<RiskProfile> {
    override fun clone() = this.copy()
}
