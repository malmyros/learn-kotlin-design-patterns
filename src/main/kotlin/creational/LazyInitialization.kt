package creational

import java.time.Duration

/*
What Is the Lazy Initialization Pattern?

Lazy Initialization delays the creation of an object or the evaluation of a value
until it is first needed. Kotlin provides built-in support via the `lazy {}` delegate.

When to use it:
- You want to defer the cost of initializing a heavy resource
- The object may never be used during the lifecycle
- You want to initialize data safely across multiple threads

In fintech:
- Useful for loading external configurations (e.g., exchange rates, fee rules, BIN metadata)
- Improves startup performance and resource usage
- Avoids unnecessary API calls or memory overhead
*/

fun main() {

    val exchangeRateProvider = ExchangeRateProvider()

    println("Before accessing exchange rate")

    // First access — triggers the computation
    println("Exchange rate: ${exchangeRateProvider.getRate("USD")}")

    // Second access — uses cached value
    println("Exchange rate again: ${exchangeRateProvider.getRate("USD")}")
}

class ExchangeRateProvider {

    private val exchangeRates: Map<String, Double> by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        println("Loading exchange rates from a remote API...")
        simulateRemoteRateLoad()
    }

    fun getRate(currencyCode: String): Double? {
        return exchangeRates.getOrDefault(currencyCode, DEFAULT_RATE)
    }

    private fun simulateRemoteRateLoad(): Map<String, Double> {

        try {
            Thread.sleep(Duration.ofMillis(500))
        } catch (e: InterruptedException) {
            println("Rate loading interrupted: ${e.message}")
            Thread.currentThread().interrupt()
            throw IllegalStateException("Exchange rate loading interrupted", e)
        }

        return mapOf(
            "USD" to 1.0,
            "GBP" to 0.78,
            "EUR" to 0.91,
            "JPY" to 110.25
        )
    }

    companion object {
        private const val DEFAULT_RATE = 1.0
    }
}

