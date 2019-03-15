import arrow.core.Option
import arrow.core.Try
import arrow.core.getOrElse

private fun main() {
    val zero: Option<Int> = Option.just(0)
    val one: Option<Int> = Option.just(1)
    val none:Option<Int> = Option.empty()

    listOf(zero, one, none).map {
        println(it)
        println(it.orNull())
        println(it.getOrElse { "42" })
        println()
    }
    println(pureDivide(1,2))
    println(pureDivide(4,2))
    println(pureDivide(4,0))

    println(tryDivide(1,2))
    println(tryDivide(4,2))
    println(tryDivide(4,0))
    
}

// Will throw if q is 0
fun partialDivide(p: Int, q: Int) = p / q

// Will return empty if q is 0
fun pureDivide(p: Int, q: Int): Option<Int> =
    if (q != 0) Option.just(p / q) else Option.empty()

// Will return throwable in Failure inside of Try if q is 0
fun tryDivide(p: Int, q: Int): Try<Int> =
    Try.invoke { p / q }
