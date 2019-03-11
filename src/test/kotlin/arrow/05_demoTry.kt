package arrow

import arrow.core.Try
import arrow.core.orNull
import org.testng.annotations.Test
import kotlin.test.assertEquals

object TryTests {

    private val parseToInt: (s: String) -> Try<Int> = {
        Try {
            it.toInt()
        }
    }

    @Test
    fun `01 show use of Try when success`() =
        assertEquals(
            2,
            parseToInt("2").orNull()
        )

    @Test
    fun `02 show use of Try when fail`() =
        assertEquals(
            null,
            parseToInt("a").orNull()
    )

//    @Test
//    fun `03 show use of Try when tupled`() =
//        assertEquals(
//            null,
//            parseToInt("1"), parseToInt("2") .orNull()
//        )

}
