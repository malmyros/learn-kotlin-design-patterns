package creational

fun main() {
    val originalCar = PrototypeCar("Lamborghini", "Countach")
    println("Car model is ${originalCar.brand} and the model is ${originalCar.model}")

    val clonedCar = originalCar.clone()
    println("Car model is ${clonedCar.brand} and the model is ${clonedCar.model}")
}

interface Prototype<T> {
    fun clone(): T
}

data class PrototypeCar(
    val brand: String = "",
    val model: String = "",
) : Prototype<PrototypeCar> {
    override fun clone(): PrototypeCar {
        return this.copy()
    }
}