package creational

fun main() {
    val car = car {
        brand("Lamborghini")
        model("Aventador")
    }

    println("Car model is ${car.brand} and the model is ${car.model}")
}

class DSLCar(
    var brand: String = "",
    var model: String = "",
)

class DSLCarBuilder {
    private val car = DSLCar()

    fun brand(brand: String) = apply { car.brand = brand }
    fun model(model: String) = apply { car.model = model }

    fun build(): DSLCar = car
}

fun car(block: DSLCarBuilder.() -> Unit): DSLCar {
    return DSLCarBuilder().apply(block).build()
}