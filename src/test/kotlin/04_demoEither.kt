import arrow.core.Either
import arrow.core.flatMap
import arrow.core.getOrHandle
import arrow.core.orNull
import org.testng.annotations.Test
import kotlin.test.assertEquals

object EitherTests {

    fun divTwoNumbers(x: Int, y: Int): Either<Int, Double> {
        val result: Double = x.toDouble().div(y)
        return if (result.rem(1.0) == 0.0) {
            Either.left(result.toInt())
        } else {
            Either.right(result)
        }
    }

    @Test
    fun `01 show use of Either with Int or Double when right`() =
        assertEquals(
            2.5,
            divTwoNumbers(5, 2).map { it }.orNull()
        )

    @Test
    fun `02 show use of Either with Int or Double when left`() =
        assertEquals(
            2.0,
            divTwoNumbers(4, 2).map { it }.getOrHandle { it.toDouble() }
        )

    // Just for fun using lambda function signature
    fun divide10By(): (Int) -> Either<String, Int> =
        { if (it == 0) Either.left("Can not divide by zero") else Either.right(10.div(it)) }

    @Test
    fun `03 show use of Either when used for failure checking when right`() {
        assertEquals(
            5,
            divide10By().invoke(2).orNull()     //Note invoke is needed for call to lambda function
        )
    }

    @Test
    fun `04 show use of Either when used for failure checking when left`() {
        assertEquals(
            "Failed: Can not divide by zero",
            divide10By().invoke(0).getOrHandle { "Failed: $it" }
        )
    }

    // Either with ADT Style

    sealed class Error {
        object NotANumber : Error()
        object NoZeroReciprocal : Error()
    }

    fun parse(s: String): Either<Error, Int> =
        if (s.matches(Regex("-?[0-9]+"))) Either.Right(s.toInt())
        else Either.Left(Error.NotANumber)

    fun reciprocal(i: Int): Either<Error, Double> =
        if (i == 0) Either.Left(Error.NoZeroReciprocal)
        else Either.Right(1.0 / i)

    fun stringify(d: Double): String = d.toString()

    fun magic(s: String): Either<Error, String> =
        parse(s).flatMap { reciprocal(it) }.map { stringify(it) }

    fun doMagic(s: String): String = magic(s).let {
        when (it) {
            is Either.Left -> when (it.a) {
                is Error.NotANumber -> "Not a number!"
                is Error.NoZeroReciprocal -> "Can't take reciprocal of 0!"
            }
            is Either.Right -> "Got reciprocal: ${it.b}"
        }
    }

    @Test
    fun `05 no error`() {
        assertEquals(
            "Got reciprocal: 0.5",
            doMagic("2")
        )
    }

    @Test
    fun `06 error with reciprocal 0`() {
        assertEquals(
            "Can't take reciprocal of 0!",
            doMagic("0")
        )
    }

    @Test
    fun `07 error parsing non int`() {
        assertEquals(
            "Not a number!",
            doMagic("a")
        )
    }

    @Test
    fun `08 fold to use right`() {
        assertEquals(
            11,
            Either.Right(10).fold({ 1 }, { it + 1 })
        )
    }

    @Test
    fun `09 fold to use default`() {
        assertEquals(
            1,
            Either.Left(10).fold({ 1 }, { it })
        )
    }
}
