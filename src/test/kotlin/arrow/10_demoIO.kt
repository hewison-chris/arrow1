package arrow

import arrow.core.Either
import arrow.core.Try
import arrow.core.getOrElse
import arrow.effects.IO
import arrow.effects.extensions.io.fx.fx
import arrow.effects.typeclasses.seconds
import org.testng.annotations.Test
import kotlin.test.assertEquals

object IOTests {

    private fun divide(d: Int, r: Int): Try<Int> = Try {
        println("Execute divide function")
        d / r
    }

    @Test
    fun `01 IO side effects with output`() {
        val defer: IO<Try<Int>> = IO { divide(2, 1).also { println("2) inside defer: $it") } }
        println("1) First output")
        assertEquals(
            2,
            defer.also { println("Just before unsafeRunSync()") }
                .unsafeRunSync().getOrElse { 0 })
    }

    @Test
    fun `02 IO side effects with failure`() {
        val defer: IO<Try<Int>> = IO { divide(2, 0).also { println("2) inside defer: $it") } }
        println("1) First output")
        assertEquals(
            0,
            defer.also { println("Just before unsafeRunSync()") }
                .unsafeRunSync().getOrElse { 0 })
    }

    @Test
    fun `03 IO attempt`() {
        val deferAttempt: IO<Either<Throwable, Try<Int>>> =
            IO {
                divide(
                    2,
                    1
                ).also { println("2) inside deferAttempt: $it") }
            }.attempt()
        println("1) First output")
        assertEquals(
            2,
            deferAttempt.also { println("Just before unsafeRunSync()") }
                .unsafeRunSync().also { println("Just after unsafeRunSync()") }
                .fold(
                    { 0.also { println(" Was Left") } },
                    { it.getOrElse { -1 }.also { println(" Was Right") } }
                )
        )
    }

    private fun explodeAt0(n: Int): IO<Unit> =
        when (n) {
            0 -> IO { throw RuntimeException("Boom!") }
            else -> explodeAt0(n - 1).also { println(n) }
        }

    @Test
    fun `04 runAsync countdown`() {
        val defer = explodeAt0(10)
        defer.runAsync { cb: Either<Throwable, Unit> ->
            cb.fold({ IO { println(it.localizedMessage) } }, { IO { println("Never ever") } })
        }.unsafeRunSync()
    }

    @Test
    fun `05 Io binding`() {
        fx {
            IO.just("Hello")
            IO.just("World")
        }.attempt().unsafeRunTimed(1.seconds)
            .let { println(it) }
    }
}
