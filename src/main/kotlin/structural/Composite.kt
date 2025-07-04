package structural

import java.math.BigDecimal

/*
What Is the Composite Pattern?

The Composite Pattern allows you to compose objects into tree structures
and work with them as if they were individual objects.

It treats individual components and groups of components uniformly.

When to use it:
- You have a hierarchy of objects (e.g. fees, limits, approvals)
- You want to treat a single object and a group of objects the same way
- You need a recursive structure that is easy to extend and inspect

Fintech Example:
- Fee bundles made up of multiple individual fees (e.g. FX Fee + Cross-border Fee)
- Grouped rules or conditions (e.g. transaction validation chains)
*/

fun main() {

    val fxFee = FlatFee("Fx Fee", BigDecimal("1.50"))
    val crossBorderFee = FlatFee("Cross border Fee", BigDecimal("2.00"))
    val atmFee = FlatFee("Atm", BigDecimal("2.50"))

    val internationalFees = FeeBundle("International Fee Usage")
    internationalFees.add(fxFee)
    internationalFees.add(crossBorderFee)

    val totalFees = FeeBundle("Total Transaction Fees")
    totalFees.add(atmFee)
    totalFees.add(internationalFees)

    println(totalFees.describe())
    println("Total fees: £${totalFees.calculate()}")
}

interface FeeComponent {
    fun calculate(): BigDecimal
    fun describe(indent: String = ""): String
}

class FlatFee(private val name: String, private val amount: BigDecimal) : FeeComponent {
    override fun calculate() = amount
    override fun describe(indent: String) = "$indent$name: $amount"
}

class FeeBundle(private val name: String) : FeeComponent {

    private val feeComponents = mutableListOf<FeeComponent>()

    fun add(feeComponent: FeeComponent) {
        feeComponents.add(feeComponent)
    }

    override fun calculate(): BigDecimal {
        return feeComponents.fold(BigDecimal.ZERO, { acc, c -> acc + c.calculate() })
    }

    override fun describe(indent: String): String {
        val details = feeComponents.joinToString("\n") { it.describe("$indent  ") }
        return "$indent$name (Bundle):\n$details"
    }
}