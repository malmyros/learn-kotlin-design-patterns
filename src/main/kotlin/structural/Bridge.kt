package structural

/*
What Is the Bridge Pattern?

The Bridge Pattern decouples an abstraction from its implementation
so that both can vary independently.

This is useful when you want to avoid a combinatorial explosion
of classes as you add new features or implementations.

When to use it:
- You have an abstraction (e.g. Card Type) and multiple implementations (e.g. Issuers)
- You want to extend either the abstraction or the implementation without modifying the other
- You want to keep your codebase flexible, maintainable, and open to new features
*/

fun main() {
    val visaIssuer = VisaIssuer()
    val mastercardIssuer = MastercardIssuer()

    val debitCard = DebitCard(visaIssuer)
    val creditCard = CreditCard(mastercardIssuer)

    debitCard.issue("54fddb32-4fdf-4559-bd5b-c98a149a1bd1")
    creditCard.issue("487f7f77-77fc-4c41-8513-ae43ff2d981a")
}

abstract class Card(protected val issuer: CardIssuer) {
    abstract fun issue(customerId: String)
}

interface CardIssuer {
    fun issueCard(customerId: String, cardType: String)
}

class VisaIssuer : CardIssuer {
    override fun issueCard(customerId: String, cardType: String) {
        println("Issuing $cardType for $customerId via Visa")
    }
}

class MastercardIssuer : CardIssuer {
    override fun issueCard(customerId: String, cardType: String) {
        println("Issuing $cardType for $customerId via Mastercard")
    }
}

class DebitCard(issuer: CardIssuer) : Card(issuer) {
    override fun issue(customerId: String) {
        issuer.issueCard(customerId, "Debit")
    }
}

class CreditCard(issuer: CardIssuer) : Card(issuer) {
    override fun issue(customerId: String) {
        issuer.issueCard(customerId, "Credit")
    }
}