package creational

/*
What Is the Builder Pattern?

The Builder Pattern separates object construction from representation,
allowing step-by-step configuration of complex objects.

When to use it:
- When an object has many optional or context-dependent fields
- When you want to enforce construction constraints before finalizing
- When constructors become too messy or overloaded

Fintech Example:
- Building a CardProduct (e.g., Visa Debit GBP) with limits and type configuration
  in a readable, fluent manner using a dedicated builder.
*/

fun main() {

    val cardProduct = CardProduct.Builder()
        .brand("Visa")
        .type("Debit")
        .currency("GBP")
        .dailyLimit(500)
        .build()

    println("Issued ${cardProduct.type} card in ${cardProduct.currency} with limit £${cardProduct.dailyLimit} for brand ${cardProduct.brand}")
}

class CardProduct private constructor(
    val brand: String,
    val type: String,
    val currency: String,
    val dailyLimit: Int,
) {

    class Builder {
        private var brand: String = "Unknown"
        private var type: String = "Debit"
        private var currency: String = "GBP"
        private var dailyLimit: Int = 0

        fun brand(brand: String) = apply { this.brand = brand }
        fun type(type: String) = apply { this.type = type }
        fun currency(currency: String) = apply { this.currency = currency }
        fun dailyLimit(dailyLimit: Int) = apply { this.dailyLimit = dailyLimit }

        fun build(): CardProduct {

            require(dailyLimit >= 0) { "Daily limit cannot be negative" }
            return CardProduct(brand, type, currency, dailyLimit)
        }
    }
}