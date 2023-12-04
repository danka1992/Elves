import java.io.File
import kotlin.math.pow

class Scratchcards {

    fun totalPointsOnCards(){
        val fileName = "src/main/resources/Scratchcards.txt"
        val file = File(fileName).readLines()
        var result = 0
        file.forEach { line ->
            val numbers = line.replace(" |  "," | ").replace(":  ",": ").split(": ", " | ")
            val winning = numbers[1].replace("  "," ").split(" ").map { it.toInt() }
            val playing = numbers[2].replace("  "," ").split(" ").map { it.toInt() }
            var exponent = 0
            val base = 2
            winning.forEach { winningNumber ->
                if(playing.any { it == winningNumber }) exponent++
            }

            val resultForOneLine = base.toDouble().pow(exponent - 1).toInt()
            println("Total points on one line: $resultForOneLine")
            result += resultForOneLine
            println("Result: $result")

        }
        println("Total points: $result")
    }

    fun totalPointsOnCardsWithCopies(){
        val fileName = "src/main/resources/Scratchcards.txt"
        val file = File(fileName).readLines()
        val numberOfCopies = IntArray(219) { 1 }
        file.forEachIndexed { index, line ->
            val numbers = line.replace(" |  "," | ").replace(":  ",": ").split(": ", " | ")
            val winning = numbers[1].replace("  "," ").split(" ").map { it.toInt() }
            val playing = numbers[2].replace("  "," ").split(" ").map { it.toInt() }
            var exponent = 0
            winning.forEach { winningNumber ->
                if(playing.any { it == winningNumber }) {
                    exponent++
                }
            }

            for(it in 1..exponent)  {
                numberOfCopies[index+it] += numberOfCopies[index]
            }
        }
        val countOfAllCards = numberOfCopies.sum()
        println("Total cards: $countOfAllCards")
    }
}