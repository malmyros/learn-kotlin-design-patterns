package structural

import java.time.Duration
import java.time.Instant

/*
What Is the Proxy Pattern?

The Proxy Pattern provides a placeholder or surrogate for another object
to control access to it.

It implements the same interface as the real object but can add behavior
such as lazy loading, caching, access control, or logging.

When to use it:
- You need to control or restrict access to an object
- You want to cache or delay expensive operations
- You want to add cross-cutting concerns without modifying the real object

Fintech Example:
- A proxy that wraps a remote KYC provider and adds caching, logging, or rate-limiting
- A proxy around payment APIs to enforce access controls or simulate behavior in tests
*/

fun main() {

    val remoteKyc = RemoteKycService()

    val cacheTtl = Duration.ofSeconds(3)
    val proxyKyc = ProxyKycService(kycService = remoteKyc, cacheTtl = cacheTtl)

    println(proxyKyc.checkCustomerStatus("pass-123")) // API call
    println(proxyKyc.checkCustomerStatus("pass-123")) // Cached
    println(proxyKyc.checkCustomerStatus("fail-999")) // API call
}


interface KycService {
    fun checkCustomerStatus(customerId: String): Boolean
}

class RemoteKycService : KycService {
    override fun checkCustomerStatus(customerId: String): Boolean {
        println("Calling external KYC provider for $customerId")

        try {
            Thread.sleep(Duration.ofMillis(100))
        } catch (e: InterruptedException) {
            println("Sleep interrupted during KYC check: ${e.message}")
            Thread.currentThread().interrupt()
            return false
        }

        return customerId.startsWith("pass")
    }
}

data class CachedKYCResult(val result: Boolean, val expiresAt: Instant)

class ProxyKycService(
    private val kycService: KycService,
    private val cacheTtl: Duration
) : KycService {

    private val cache = mutableMapOf<String, CachedKYCResult>()

    override fun checkCustomerStatus(customerId: String): Boolean {
        println("Checking KYC status for $customerId through proxy")

        val now = Instant.now()

        return cache.compute(customerId) { _, existing ->
            // If not expired, reuse existing
            if (existing != null && existing.expiresAt.isAfter(now)) {
                println("Returning cached result for $customerId: ${existing.result}")
                return@compute existing
            }

            // Otherwise fetch new result and cache it
            val result = kycService.checkCustomerStatus(customerId)
            val newCache = CachedKYCResult(result, now.plus(cacheTtl))
            println("Caching result for $customerId: $result (expires at ${newCache.expiresAt})")
            newCache
        }!!.result
    }
}
