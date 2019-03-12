import arrow.core.Either
import arrow.effects.IO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

////Coroutine dispatcher
import kotlinx.coroutines.javafx.JavaFx as UI

suspend fun <T : Any> IO<T>.await(): Either<Throwable, T> =
    suspendCoroutine { continuation ->
        unsafeRunAsync(continuation::resume)
    }

fun main() {
    runBlocking {
        IO { "Hello Coroutine" }
            .await()
            // Wait for result on Coroutine dispatcher
            .fold(
                { println("Error $it") },
                { println("Success $it") }
            )
    }

    GlobalScope.launch(Dispatchers.UI) {
        IO { "Hello Coroutine" }
            .await()
            // Wait for result on Coroutine dispatcher
            .fold(
                { println("Error $it") },
                { println("Success $it") }
            )
    }
}