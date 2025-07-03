package creational

fun main() {
    MyService.instance.greet();
}

class MyService {

    companion object {
        val instance = MyService()
    }

    fun greet() {
        println("Hello World!")
    }
}