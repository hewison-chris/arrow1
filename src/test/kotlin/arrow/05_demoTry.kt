package arrow

import arrow.core.*
import arrow.core.extensions.`try`.applicative.applicative
import org.testng.annotations.Test
import kotlin.test.assertEquals

object TryTests {

    private val parseToInt: (s: String) -> Try<Int> = {
        Try {
            it.toInt()
        }
    }

    @Test
    fun `01 show use of Try when success`() {
        val parseToIntResult: Try<Int> = parseToInt("2")
        assertEquals(
            2,
            parseToIntResult.orNull()
        )
    }

    @Test
    fun `02 show use of Try when fail`() =
        assertEquals(
            null,
            parseToInt("a").orNull()
    )

    @Test
    fun `03 show use of Try when handling failure`() {
        val handleError: Try<Int> = parseToInt("a").handleError { 0 }
        assertEquals(
            0,
            handleError.orNull()
        )
    }

    @Test   // Applicative
    fun `04 show use of Try when tupled`() {
        val res: Try<Tuple2<Int, Int>> = Try.applicative().tupled(parseToInt("1"), parseToInt("2")).fix()
        assertEquals(
            Tuple2(1, 2), res.orNull()
        )
    }

}
