import arrow.effects.IO
import arrow.effects.extensions.io.applicativeError.handleErrorWith
import arrow.effects.extensions.io.fx.fx
import arrow.effects.fix

private fun mainIO(): IO<String> =
    fx {
        first()
    second()
    }.unsafeRunSync()

private fun mainIO2(): IO<Any> =
    fx {
        first()
    second()
    }.attempt().handleErrorWith { IO.just("Failed") }.fix()

private fun first(): IO<String> = IO.just("first called")
private fun second(): IO<String> = IO.just("second called")

private fun main() {
    println(mainIO().unsafeRunSync())
    println(mainIO2().unsafeRunSync())
}
