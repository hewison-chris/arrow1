import arrow.effects.IO

suspend fun sayHello(): Unit =
    println("Hello World")

suspend fun sayGoodBye(): Unit =
    println("Good bye World!")

//fun greet(): IO<Unit> =
//    fx {
//        val pureHello = effect { sayHello() }
//        val pureGoodBye = effect { sayGoodBye() }
//    }

suspend fun main() {
    sayHello()
    sayGoodBye()
}

