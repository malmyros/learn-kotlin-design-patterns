package behavioural

import java.math.BigDecimal

/*
What Is the Visitor Pattern?

The Visitor Pattern lets you add new operations to a fixed object structure
without modifying the objects themselves. The operations are encapsulated
in visitor classes.

When to use it:
- You want to add operations to domain objects without cluttering them
- You want to separate responsibilities (e.g., reporting, auditing, exporting)
- You have a stable set of elements (e.g., financial instruments)

Fintech Example:
- A portfolio contains stocks, bonds, and cash. You want to run multiple
  operations like valuation, auditing, or exporting without changing the asset classes.
*/

fun main() {

    val portfolio = listOf<FinancialInstrument>(
        Stock("AAPL", 10, BigDecimal("190.00")),
        Bond("Gov-10Y", BigDecimal("1000.00"), BigDecimal("0.03")),
        Cash("GBP", BigDecimal("500.00"))
    )

    val valueVisitor = ValuationVisitor()
    val auditVisitor = AuditVisitor()

    portfolio.forEach { it.accept(valueVisitor) }
    portfolio.forEach { it.accept(auditVisitor) }

    println("Total portfolio value: £${valueVisitor.total}")
}

interface FinancialInstrument {
    fun accept(visitor: InstrumentVisitor)
}

class Stock(val symbol: String, val quantity: Int, val unitPrice: BigDecimal) : FinancialInstrument {
    override fun accept(visitor: InstrumentVisitor) {
        visitor.visit(this)
    }
}

class Bond(val name: String, val faceValue: BigDecimal, val interestRate: BigDecimal) : FinancialInstrument {
    override fun accept(visitor: InstrumentVisitor) {
        visitor.visit(this)
    }
}

class Cash(val currency: String, val amount: BigDecimal) : FinancialInstrument {
    override fun accept(visitor: InstrumentVisitor) {
        visitor.visit(this)
    }
}


interface InstrumentVisitor {
    fun visit(stock: Stock)
    fun visit(bond: Bond)
    fun visit(cash: Cash)
}

class ValuationVisitor : InstrumentVisitor {
    var total = BigDecimal.ZERO

    override fun visit(stock: Stock) {
        val value = stock.unitPrice.multiply(BigDecimal(stock.quantity))
        total += value
        println("Valued ${stock.quantity} x ${stock.symbol} = £$value")
    }

    override fun visit(bond: Bond) {
        total += bond.faceValue
        println("Valued bond ${bond.name} = £${bond.faceValue}")
    }

    override fun visit(cash: Cash) {
        total += cash.amount
        println("Cash in ${cash.currency} = £${cash.amount}")
    }
}

class AuditVisitor : InstrumentVisitor {

    override fun visit(stock: Stock) {
        println("Auditing stock: ${stock.symbol} quantity=${stock.quantity}")
    }

    override fun visit(bond: Bond) {
        println("Auditing bond: ${bond.name} rate=${bond.interestRate}")
    }

    override fun visit(cash: Cash) {
        println("Auditing cash balance: ${cash.currency}")
    }
}