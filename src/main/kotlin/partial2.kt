import arrow.core.Try
import arrow.effects.IO
import arrow.effects.extensions.io.fx.fx
import arrow.effects.liftIO
import java.security.SecureRandom

private val random = SecureRandom()

private fun putStrLn(line: String): IO<Unit> = IO { println(line) }
private fun getStrLn(): IO<String> = IO { readLine() as String }

private fun String.safeToInt() = Try { toInt() }

private fun testGuess(name: String, guess: String) = IO<Unit> {
    guess.safeToInt().fold(
        { putStrLn("You did not enter a number!") },
        {
            IO<Int> { random.nextInt(5) + 1 }.map { n ->
                if (it == n) {
                    putStrLn("You guessed right, $name!")
                } else {
                    putStrLn("You guessed wrong, $name! The number was $n")
                }
            }
        })
}

private fun gameLoop(name: String): IO<Unit> = fx {
    !effect { putStrLn("Dear $name, please guess a number from 1 to 5:") }.bind()
    val (guess) = !effect { getStrLn() }
    !effect { testGuess(name, guess) }.bind()
    checkContinue(name).map {
        (if (it) gameLoop(name)
        else Unit.liftIO())
    }.flatten()
        .bind()
}

private fun checkContinue(name: String): IO<Boolean> = fx {
    putStrLn("Do you want to continue, $name?").bind()
    (getStrLn()).map { it.toLowerCase() }.map {
        when (it) {
            "y" -> true.liftIO()
            "n" -> false.liftIO()
            else -> checkContinue(name)
        }
    }.flatten()
        .bind()
}

private fun main() {
    mainIO().unsafeRunSync()
}

private fun mainIO(): IO<Unit> = fx {
    !effect { putStrLn("What is your name?") }.bind()
    val (name) = !effect { getStrLn() }
    !effect { putStrLn("Hello, $name, welcome to the game!") }.bind()
    gameLoop(name).bind()
}
