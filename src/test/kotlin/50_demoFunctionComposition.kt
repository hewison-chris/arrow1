import org.testng.annotations.Test
import kotlin.test.assertEquals

object FunctionCompositionTests {

    // partial application
    private fun <T1, T2, R> ((T1, T2) -> R).partial1(t1: T1): (T2) -> R = { t2 -> this.invoke(t1, t2) }

    private fun concat(a:String, b: String): String = a + b

    private val prependedWithA = ::concat.partial1("A")

    // create curried function for two inputs
    private fun <T1, T2, R> ((T1, T2) -> R).curried(): (T1) -> (T2) -> R = { t1 -> { t2 -> this(t1, t2) } }

    private val concatLambda: (String, String) -> String = { a,b -> a + b } // Same as ::concat

    private val prependedWithB = concatLambda.partial1("B")

    @Test
    fun `01 show partial application`() =
        assertEquals(
            "Ab",
            prependedWithA("b")
        )

    @Test
    fun `02 show concat at same time using val function`() =
        assertEquals(
            "Bb",
            prependedWithB("b")
        )

    @Test
    fun `03 show concat at same time using val function`() =
        assertEquals(
            "ab",
            concat("a", "b")
        )

    @Test
    fun `04 show concat at same time using normal fun`() =
        assertEquals(
            "ab",
            concat("a", "b")
        )

    @Test
    fun `05 show concat at same time using lambda fun`() =
        assertEquals(
            "ab",
            concatLambda.invoke("a", "b")
        )

    @Test
    fun `06 show concat using curry`() =
        assertEquals(
            "ab",
            ::concat.curried()("a")("b")
        )

}
