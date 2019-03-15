import arrow.effects.IO
import arrow.effects.extensions.io.fx.fx
import arrow.effects.typeclasses.seconds
import kotlinx.coroutines.runBlocking

suspend fun sayHi(): Unit = println("Hi there!")

suspend fun sayBye(): Unit = println("bye!")

fun greet(): IO<Unit> =
    fx {
        !effect { sayHi() }.also { println("Line 12") }
        !effect { sayBye() }.also { println("Line 13") }
    }

private fun main() {
    runBlocking { greet() }.also { println("Nothing to see here!") }
    runBlocking { greet().unsafeRunTimed(10.seconds) }
}