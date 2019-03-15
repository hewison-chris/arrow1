package arrow

import arrow.core.Id
import arrow.core.extensions.id.fx.fx
import arrow.core.value
import org.testng.annotations.Test
import kotlin.test.assertEquals

object IdTests {

    @Test
    fun `01 additional using map`() {
        val id1: Id<Int> = Id.just(1)
        assertEquals(
            3,
            id1.map { it + 2 }.value()
        )
    }

    @Test
    fun `02 additional of two Ids using flatMap`() {
        val id1: Id<Int> = Id.just(1)
        val id2: Id<Int> = Id.just(2)
        assertEquals(
            3,
            id1.flatMap { one ->
                id2.map { two -> one + two }
            }.value()
        )
    }

    @Test
    fun `03 additional using Monad binding`() {
        val id1: Id<Int> = Id.just(1)
        val id2: Id<Int> = Id.just(2)
        assertEquals(
            3,
            fx {
                val one = id1.bind()
                val two = id2.bind()
                one + two
            }.value()
        )
    }

    @Test
    fun `04 additional using Monad binding with `() {
        val id1: Id<Int> = Id.just(1)
        val id2: Id<Int> = Id.just(2)
        assertEquals(
            3,
            fx {
                val one = id1.bind()
                val two = id2.bind()
                one + two
            }.value()
        )
    }

}
