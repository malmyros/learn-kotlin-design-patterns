package creational

fun main() {

    // Prints Before access
    println("Before access")

    // Prints Computing a value
    // Prints Hello, World!
    println(expensiveValue)

    // Prints Hello, World!
    println(expensiveValue)
}

val expensiveValue: String by lazy {
    println("Computing a value")
    "Hello, World!"
}

val nonThreadSafeValue: String by lazy(LazyThreadSafetyMode.NONE) {
    println("Init once, not thread-safe")
    "Some value"
}

val partialThreadSafeValue: String by lazy(LazyThreadSafetyMode.PUBLICATION) {
    println("Init once, not thread-safe")
    "Some value"
}

val threadSafeValue: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    println("Init once, not thread-safe")
    "Some value"
}