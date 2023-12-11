import java.io.File

object WaitForIt {

    fun raceResult() {
        val fileName = "src/main/resources/WaitForIt.txt"
        val file = File(fileName).readLines()

        val time = file[0].replace(Regex("\\s+"), " ").split(": ").last().split(" ").map { it.toInt() }
        val record = file[1].replace(Regex("\\s+"), " ").split(": ").last().split(" ").map { it.toInt() }

        var result = 1

        time.forEachIndexed { index, time ->
            var counter = 0
            for(t in 1..< time) {
                val distance = t * (time - t)
                if (record[index] < distance) counter++
            }
            result *= counter
        }
        println("Result: $result")
    }

    fun secondRaceResult() {
        val fileName = "src/main/resources/WaitForIt.txt"
        val file = File(fileName).readLines()

        val time = file[0].filter { it.isDigit() }.toLong()
        val record = file[1].filter { it.isDigit() }.toLong()

        var result = 1


            var counter = 0
            for(t in 1..< time) {
                val distance = t * (time - t)
                if (record < distance) counter++
            }
            result *= counter

        println("Result: $result")
    }
}