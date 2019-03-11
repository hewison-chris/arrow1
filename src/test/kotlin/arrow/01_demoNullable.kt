package arrow

import org.testng.annotations.Test
import kotlin.test.assertEquals

object NullableTests {

    private fun parseInt(s: String): Int? = try {
        s.toInt()
    } catch (e: Exception) {
        null
    }

    @Test
    fun `01 actual value using orNull`() =
        assertEquals(
            1,
            parseInt("1")
        )

    @Test
    fun `02 null when None using orNull`() =
        assertEquals(
            null,
            parseInt("a")
        )

    @Test
    fun `03 actual value using getOrElse`() =
        assertEquals(
            2,
            parseInt("2") ?: -1
        )

    @Test
    fun `04 default when empty using getOrElse`() =
        assertEquals(
            -1,
            parseInt("") ?: -1
        )

    @Test
    fun `05 chaining`() =
        assertEquals(
            6,
            parseInt("1")
                ?.let { it + 2 }
                ?.let { it + 3 }
        )

    @Test
    fun `06 chain values`() =
        assertEquals(
            100,
            parseInt("1")
                ?.let { parseInt(it.toString() + "0") }
                ?.let { parseInt(it.toString() + "0") }
        )

    @Test
    fun `07 chain with an empty value in the chain`() =
        assertEquals(
            null,
            parseInt("1")
                ?.let { parseInt(it.toString() + "a") }
                ?.let { parseInt(it.toString() + "0") }
        )

    @Test
    fun `08 chain starting with empty value`() =
        assertEquals(
            null,
            parseInt("")
                ?.let { parseInt(it.toString() + "a") }
                ?.let { parseInt(it.toString() + "0") }
        )

    @Test
    fun `09 get first from map if found`() {
        val foxMap = mapOf(1 to "The", 2 to "Quick", 3 to "Brown", 4 to "Fox")
        assertEquals(
            "FOX",
            foxMap.entries.firstOrNull { it.key == 4 }?.value.let { it?.toUpperCase() }
        )
        assertEquals(
            null,
            foxMap.entries.firstOrNull { it.key == 5 }?.value.let { it?.toUpperCase() }
        )
    }

}
