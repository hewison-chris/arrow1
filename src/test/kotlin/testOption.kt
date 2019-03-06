import arrow.core.*
import arrow.effects.instances.io.monad.binding
import arrow.instances.option.applicative.applicative
import org.testng.annotations.Test
import kotlin.test.assertEquals

object OptionsTests {

    fun parseInt(s: String): Option<Int> = try {
        Some(s.toInt())
    } catch (e: Exception) {
        None
    }

    @Test
    fun `01 actual value using orNull`() =
        assertEquals(
            1,
            parseInt("1").orNull()
        )

    @Test
    fun `02 null when None using orNull`() =
        assertEquals(
            null,
            parseInt("a").orNull()
        )

    @Test
    fun `03 actual value using getOrElse`() =
        assertEquals(
            2,
            parseInt("2").getOrElse { -1 }
        )

    @Test
    fun `04 default when empty using getOrElse`() =
        assertEquals(
            -1,
            parseInt("").getOrElse { -1 }
        )

    @Test
    fun `05 FUNCTOR (Transforming the inner contents)`() =
        assertEquals(
            6,
            parseInt("1")
                .map { it + 2 }
                .map { it + 3 }
                .orNull()
        )

    @Test
    fun `06 flatmap values`() =
        assertEquals(
            100,
            parseInt("1")
                .flatMap { parseInt(it.toString() + "0") }
                .flatMap { parseInt(it.toString() + "0") }
                .orNull()
        )

    @Test
    fun `07 flatmap with an empty value in the chain`() =
        assertEquals(
            null,
            parseInt("1")
                .flatMap { parseInt(it.toString() + "a") }
                .flatMap { parseInt(it.toString() + "0") }
                .orNull()
        )

    @Test
    fun `08 flatmap starting with empty value`() =
        assertEquals(
            null,
            parseInt("")
                .flatMap { parseInt(it.toString() + "0") }
                .flatMap { parseInt(it.toString() + "0") }
                .orNull()
        )

    @Test
    fun `09 get first from map if found`() {
        val foxMap = mapOf(1 to "The", 2 to "Quick", 3 to "Brown", 4 to "Fox")
        assertEquals(
            "FOX",
            foxMap.entries.firstOrNone { it.key == 4 }.map { it.value.toUpperCase() }.orNull()
        )
        assertEquals(
            null,
            foxMap.entries.firstOrNone { it.key == 5 }.map { it.value.toUpperCase() }.orNull()
        )
    }

    @Test
    fun `10 option from nullable when value`() =
        assertEquals(
            Some(2),
            Option.fromNullable(2)
        )

    @Test
    fun `11 option from nullable when null`() =
        assertEquals(
            None,
            Option.fromNullable(null)
        )

    @Test
    fun `12 APPLICATIVE (Computing over independent values)`() =
        assertEquals(
            Some(Tuple3(1, "Hello", 20.0)),
            Option.applicative().tupled(Some(1), Some("Hello"), Some(20.0))
        )

    @Test
    fun `13 MONAD (Computing over dependent values ignoring absence)`() =
        assertEquals(
            "a2a:2",
            binding {
                val (a) = Some("a")
                val (b) = Some(2)
                val (c) = Some("$a:$b")
                a + b + c
            }.unsafeRunSync()
        )

//    @Test
//    fun `14 MONAD (with none in chain)`() =
//        assertEquals(
//            6,
//            binding {
//                val (x) = none<Int>()
//                val (y) = Some(1 + x)
//                val (z) = Some(1 + y)
//                y + z
//            }.unsafeRunSync()
//        )

    @Test
    fun `14 Applicative example`() {

        fun profileService(): Option<String> = Some("Alfredo Lambda")
        fun phoneService(): Option<Int> = Some(55555555)
        fun addressService(): Option<List<String>> = Some(listOf("1 Main Street", "11130", "NYC"))
        data class Profile(val name: String, val phone: Int, val address: List<String>)

        fun applicative() {
            val r: Option<Tuple3<String, Int, List<String>>> =
                Option.applicative().tupled(profileService(), phoneService(), addressService()).fix()
            println(r.map { Profile(it.a, it.b, it.c) })
        }
        applicative()
    }
}
