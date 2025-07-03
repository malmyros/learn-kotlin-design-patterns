package creational

fun main() {

    val car = Car.Builder()
        .brand("Lamborghini")
        .model("Diablo")
        .build()

    println("Car model is ${car.brand} and the model is ${car.model}")

}

class Car private constructor(
    val brand: String,
    val model: String,
) {

    class Builder {
        private var brand = ""
        private var model = ""

        fun brand(brand: String) = apply { this.brand = brand }
        fun model(model: String) = apply { this.model = model }

        fun build() = Car(brand, model)
    }
}