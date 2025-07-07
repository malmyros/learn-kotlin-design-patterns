package behavioural

import java.math.BigDecimal

/*
What Is the Iterator Pattern?

The Iterator Pattern provides a way to access the elements of an aggregate object
sequentially without exposing its internal structure.

When to use it:
- You want to hide the details of how a collection is stored
- You want to traverse collections in a consistent, flexible way
- You need multiple iteration strategies (e.g., sorted, filtered, reversed)

Fintech Example:
- A portfolio manager that iterates over a user’s investment assets
  to calculate total value, apply filters, or generate reports.
*/

fun main() {

    val investmentPortfolio = InvestmentPortfolio()
    investmentPortfolio.add(InvestmentAsset("AAPL", "Stock", BigDecimal("10.0"), BigDecimal(200.00)))
    investmentPortfolio.add(InvestmentAsset("VUSA", "ETF", BigDecimal("5"), BigDecimal(80.00)))
    investmentPortfolio.add(InvestmentAsset("BTC", "Crypto", BigDecimal("0.25"), BigDecimal(30_000.00)))

    val iterator = investmentPortfolio.iterator()

    var totalValue = BigDecimal.ZERO
    while (iterator.hasNext()) {
        val asset = iterator.next()
        val value = asset.unitPrice.multiply(asset.quantity)
        println("Asset: ${asset.symbol}, Value: £$value")
        totalValue = totalValue.add(value)
    }

    println("Total Portfolio Value: £$totalValue")
}

data class InvestmentAsset(
    val symbol: String,
    val type: String,
    val quantity: BigDecimal,
    val unitPrice: BigDecimal,
)

interface AssetIterator {
    fun hasNext(): Boolean
    fun next(): InvestmentAsset
}

class InvestmentPortfolio {
    private val assets = mutableListOf<InvestmentAsset>()

    fun add(asset: InvestmentAsset) {
        assets.add(asset)
    }

    fun iterator(): AssetIterator = PortfolioIterator(assets)
}

class PortfolioIterator(private val assets: MutableList<InvestmentAsset>) : AssetIterator {

    private var index = 0

    override fun hasNext() = index < assets.size

    override fun next(): InvestmentAsset = assets[index++]
}