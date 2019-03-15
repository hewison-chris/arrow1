import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import java.security.SecureRandom

private val random = SecureRandom()

private fun main() {
    println("What is your name?")
    val name: String = readLine() ?: "anonymous"
    println("Hello, $name, welcome to the game!")
    var exec = true
    while (exec) {
        println("Dear $name, please guess a number from 1 to 5:")
        tryGuess(random.nextInt(5) + 1, name)
        println("Do you want to continue, $name?")
        when (readLine()) {
            "y" -> exec = true
            "n" -> exec = false
        }
    }
}

fun parseInt(input: String?) = Try { input?.toInt() }

fun tryGuess(number: Int, name: String?) {
    val guess: Try<Int?> = parseInt(readLine())
    when (guess) {
        is Failure -> println("You did not enter a number!")
        is Success<Int?> -> {
            if (guess.value == number) println("You guessed right, $name!")
            else println("You guessed wrong, $name! The number was $number")
        }
    }
}

