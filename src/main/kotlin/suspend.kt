suspend fun sayHello(): Unit =
    println("Hello World")

suspend fun sayGoodBye(): Unit =
    println("Good bye World!")

suspend fun main() {
    sayHello()
    sayGoodBye()
}

