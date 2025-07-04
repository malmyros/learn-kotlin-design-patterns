package creational

import java.math.BigDecimal

/*
What Is the Abstract Factory Pattern?

The Abstract Factory Pattern provides an interface for creating families of related objects
without specifying their concrete classes.

When to use it:
- You want to enforce consistency across a family of related components (e.g., cards, fees, limits)
- You need to support multiple product variants in a clean, modular way
- You want to switch configurations at runtime based on region, issuer, or product type

Fintech Example:
- A card issuing system that generates different card products (e.g., Standard, Premium)
  with their own design, spending limits, and fee policies
*/

fun main() {
    val standardFactory = StandardCardFactory()
    val premiumFactory = PremiumCardFactory()

    issueCard(standardFactory)
    issueCard(premiumFactory)
}

interface CardDesign {
    fun renderDesign(): String
}

interface SpendingLimit {
    fun dailyLimit(): Int
}

interface FeePolicy {
    fun monthlyFee(): BigDecimal
}

interface CardProductFactory {
    fun createCardDesign(): CardDesign
    fun createSpendingLimit(): SpendingLimit
    fun createFeePolicy(): FeePolicy
}

class StandardCardDesign : CardDesign {
    override fun renderDesign() = "Blue Visa Debit"
}

class PremiumCardDesign : CardDesign {
    override fun renderDesign() = "Black Metal Mastercard"
}

class StandardLimit : SpendingLimit {
    override fun dailyLimit() = 500
}

class PremiumLimit : SpendingLimit {
    override fun dailyLimit() = 5000
}

class StandardFee : FeePolicy {
    override fun monthlyFee() = BigDecimal("0.00")
}

class PremiumFee : FeePolicy {
    override fun monthlyFee() = BigDecimal("9.99")
}

class StandardCardFactory : CardProductFactory {
    override fun createCardDesign() = StandardCardDesign()
    override fun createSpendingLimit() = StandardLimit()
    override fun createFeePolicy() = StandardFee()
}

class PremiumCardFactory : CardProductFactory {
    override fun createCardDesign() = PremiumCardDesign()
    override fun createSpendingLimit() = PremiumLimit()
    override fun createFeePolicy() = PremiumFee()

}

fun issueCard(cardProductFactory: CardProductFactory) {
    val cardDesign = cardProductFactory.createCardDesign()
    val spendingLimit = cardProductFactory.createSpendingLimit()
    val feePolicy = cardProductFactory.createFeePolicy()

    println("Issuing card:")
    println(" - Design: ${cardDesign.renderDesign()}")
    println(" - Daily Limit: £${spendingLimit.dailyLimit()}")
    println(" - Monthly Fee: £${feePolicy.monthlyFee()}")
}