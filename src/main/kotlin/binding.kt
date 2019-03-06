import arrow.effects.IO
import arrow.effects.instances.io.monad.binding
import arrow.effects.instances.io.monad.flatten

fun mainIO(): IO<String> =
    binding {
        first()
    second()
    }.flatten()

fun first(): IO<String> = IO.just("first called")
fun second(): IO<String> = IO.just("second called")

fun main() {
    println(mainIO().unsafeRunSync())
}
