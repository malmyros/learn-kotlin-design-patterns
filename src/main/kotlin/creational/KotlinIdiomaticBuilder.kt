package creational

fun main() {

    val car = IdiomaticCar(brand = "Lamborghini", model = "Murcielago")
    println("Car model is ${car.brand} and the model is ${car.model}")
}

data class IdiomaticCar(
    val brand: String,
    val model: String,
) {

}