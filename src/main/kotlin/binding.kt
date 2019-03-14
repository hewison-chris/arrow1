import arrow.effects.IO
import arrow.effects.fix
import arrow.effects.handleErrorWith
import arrow.effects.instances.io.monad.binding
import arrow.effects.instances.io.monad.flatten
import arrow.effects.instances.io.monad.monad

fun mainIO(): IO<String> =
    IO.monad().binding {
        first()
    second()
    }.flatten()

fun mainIO2(): IO<Any> =
    binding {
        first()
    second()
    }.attempt().handleErrorWith { IO.just("Failed") }.fix()

fun first(): IO<String> = IO.just("first called")
fun second(): IO<String> = IO.just("second called")

fun main() {
    println(mainIO().unsafeRunSync())
    println(mainIO2().unsafeRunSync())
}
