package creational

import java.time.Instant

/*
What Is the Singleton Pattern?

The Singleton Pattern ensures a class has only one instance and provides
a global access point to that instance.

When to use it:
- You need one shared instance across the system (e.g., a logger, cache, or rate limiter)
- Instantiating the object multiple times would cause inconsistency or resource waste
- The object is stateless or immutable, or has internal locking for thread safety

Fintech Example:
- A secure AuditLogger used for regulatory logging (e.g., failed KYC attempts, rejected payments),
  where only one logger should write to the central audit stream.
*/

fun main() {
    val logger1 = AuditLogger.getInstance()
    val logger2 = AuditLogger.getInstance()

    logger1.log("Card declined for user 1234")
    logger2.log("Suspicious login from unknown IP")

    /*
    In financial systems, we often need a single shared instance of a critical service,
    such as an AuditLogger or RateLimiter. The Singleton pattern guarantees that
    all references (logger1, logger2) point to the same object in memory.
    This avoids duplicate logging, race conditions, or inconsistencies.

    === checks referential equality, confirming both variables point to the exact same instance.
    */
    println("Same instance: ${logger1 === logger2}") // true
}

class AuditLogger private constructor() {

    init {
        println("Initializing secure audit logger...")
    }

    fun log(message: String) {
        println("[AUDIT] ${Instant.now()} - $message")
    }

    companion object {

        @Volatile
        private var instance: AuditLogger? = null

        fun getInstance(): AuditLogger {
            return instance ?: synchronized(this) {
                instance ?: AuditLogger().also { instance = it }
            }
        }
    }
}