import java.security.SecureRandom

private val random = SecureRandom()

private fun main() {
    println("What is your name?")
    val name: String = readLine() ?: "anonymous"
    println("Hello, $name, welcome to the game!")
    var exec = true
    while (exec) {
        println("Dear $name, please guess a number from 1 to 5:")
        val number = random.nextInt(5) + 1
        userGuess(number, name)
        println("Do you want to continue, $name?")
        when (readLine()) {
            "y" -> exec = true
            "n" -> exec = false
        }
    }
}

//This function is partial as it could throw an exception if guess is not an integer
private fun userGuess(number: Int, name: String) {
    val guess = readLine()?.toInt()
    if (guess == number) {
        println("You guessed right, $name!")
    } else {
        println("You guessed wrong, $name! The number was $number")
    }
}
