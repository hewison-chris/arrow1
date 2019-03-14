package udemy

import org.testng.annotations.Test
import kotlin.test.assertEquals

object FunctionCompositionTests {

    // functional composition
    private fun <T1, T2, R> ((T1) -> T2).andThen(f: (T2) -> R): (T1) -> R = { t1 -> f(this(t1)) }

    private fun <T1, T2, R> ((T2) -> R).compose(f: (T1) -> T2): (T1) -> R = { t1 -> this(f(t1)) }

    @Test
    fun `01 show andThen`() {
        val showLengthAsDouble = String::length.andThen(Int::toDouble).andThen(Double::toString)
        assertEquals(
            "3.0",
            showLengthAsDouble("fun")
        )
    }

    @Test
    fun `02 show compose is same but backwards`() {
        val showAsDoubleTheLength = Double::toString.compose(Int::toDouble).compose(String::length)
        assertEquals(
            "3.0",
            showAsDoubleTheLength("fun")
        )
    }

    @Test
    fun `03 show benefit when using compose to replace multiple maps`() {
        val expected = listOf("1.0","2.0","3.0")
        val res = Double::toString.compose(Int::toDouble).compose(String::length)
        assertEquals(
            expected,
            listOf("a", "ab", "abc").map(res)
        )
        // Instead of
        assertEquals(
            expected,
            listOf("a", "ab", "abc")
                .map(String::length)
                .map(Int::toDouble)
                .map(Double::toString)
        )
    }

}
