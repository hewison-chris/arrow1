package udemy

import org.testng.annotations.Test
import kotlin.test.assertEquals

sealed class LinkedList<out T : Any>

data class Cons<T : Any>(
    val head: T,
    val tail: LinkedList<T>
) : LinkedList<T>() {
    override fun toString(): String {
        return "$head->$tail"
    }
}

object Nil : LinkedList<Nothing>() {
    override fun toString(): String {
        return "."
    }
}

class DemoLinkedList {

    @Test
    fun `01 only Nil`() {
        assertEquals(
            ".", Nil.toString()
        )
    }

    @Test
    fun `02 adding first element`() {
        assertEquals(
            "1->.", Cons(1, Nil).toString()
        )
    }

    @Test
    fun `03 adding second element`() {
        assertEquals(
            "1->2->.", Cons(1, Cons(2, Nil)).toString()
        )
    }

    @Test
    fun `03 adding third element`() {
        assertEquals(
            "1->2->3->.",
            Cons(1, Cons(2, Cons(3, Nil))).toString()
        )
    }

    private tailrec fun <T : Any> LinkedList<T>.forEach(f: (T) -> Any): Unit =
        when (this) {
            is Nil -> Unit
            is Cons -> {
                f(head)
                tail.forEach(f)
            }
        }

    @Test
    fun `04 using forEach`() {
        val intList = mutableListOf<Int>()
        Cons(1, Cons(2, Cons(3, Nil))).forEach { intList.add(it) }
        assertEquals(listOf(1, 2, 3), intList)
    }

    private fun <T : Any, R : Any> LinkedList<T>.map(f: (T) -> R): LinkedList<R> =
        when (this) {
            is Nil -> Nil
            is Cons -> Cons(f(head), tail.map(f))
        }

    @Test
    fun `05 using map`() {
        assertEquals(
            "3.0->2.0->1.0->.",
            Cons(3, Cons(2, Cons(1, Nil))).map(Int::toDouble).toString()
        )
    }

    private tailrec fun <T : Any> LinkedList<T>.filter(f: (T) -> Boolean): LinkedList<T> =
        when (this) {
            is Nil -> Nil
            is Cons -> if (f(head)) Cons(head, tail.filter(f)) else tail.filter(f)
        }

    @Test
    fun `06 using filter`() {
        assertEquals(
            Cons(2, Nil),
            Cons(3, Cons(2, Cons(1, Nil))).filter { it % 2 == 0 }
        )
    }

    private fun <T : Any> LinkedList<T>.add(t: T): LinkedList<T> = Cons(t, this)

    @Test
    fun `07 using add`() {
        assertEquals(
            "three->two->one->.",
            Nil.add("one").add("two").add("three").toString()
        )
    }

    private fun <T : Any> LinkedList<T>.append(l: LinkedList<T>): LinkedList<T> =
        when (this) {
            is Nil -> l
            is Cons -> Cons(head, tail.append(l))
        }

    @Test
    fun `07 using append`() {
        assertEquals(
            "one->two->three->four->.",
            Nil.add("two").add("one").append(Nil.add("four").add("three")).toString()
        )
    }

    private fun <T : Any, R : Any> LinkedList<T>.flatMap(f: (T) -> LinkedList<R>): LinkedList<R> =
        when (this) {
            is Nil -> Nil
            is Cons -> f(head).append(tail.flatMap(f) )
        }

// Imperative way would be:-
//    private fun linkedChars(s: String): LinkedList<Char> {
//        var chars: LinkedList<Char> = Nil
//        s.map { chars = chars.add(it) }
//        return chars
//    }

    private fun linkedChar(s: String): LinkedList<Char> = s.fold<LinkedList<Char>>(Nil) { acc, c -> acc.add(c) }

    @Test
    fun `07 using flatMap`() {
        assertEquals(
            "o->n->e->t->w->o->.",
            Nil.add("owt").add("eno").flatMap(::linkedChar).toString()
        )
    }

}
