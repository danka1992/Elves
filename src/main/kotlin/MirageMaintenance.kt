import java.io.File

object MirageMaintenance {

    fun extrapolateValue() {
        val fileName = "src/main/resources/MirageMaintenance.txt"
        val file = File(fileName).readLines()

        var result: Int = 0

        file.forEach { line ->
            var numbers = line.split(" ").map { it.toInt() }.toMutableList()
            val lastValues = mutableListOf<Int>()
            var localResult = 0
            while (numbers.count { it == 0 } != numbers.size) {
                println(numbers)
                lastValues.add(numbers.last())
                numbers = numbers.zipWithNext { a, b -> b - a }.toMutableList()
                localResult = lastValues.reduce{ x: Int, y: Int -> x + y}
            }
            print("Local result: $localResult")
            result += localResult

        }
        print("Result: $result")
    }

    fun extrapolateOnTheBeginning() {
        val fileName = "src/main/resources/MirageMaintenance.txt"
        val file = File(fileName).readLines()

        var result: Int = 0

        file.forEach { line ->
            var numbers = line.split(" ").map { it.toInt() }.reversed().toMutableList()
            val lastValues = mutableListOf<Int>()
            var localResult = 0
            while (numbers.count { it == 0 } != numbers.size) {
                println(numbers)
                lastValues.add(numbers.last())
                numbers = numbers.zipWithNext { a, b -> b - a }.toMutableList()
                localResult = lastValues.reduce{ x: Int, y: Int -> x + y}
            }
            print("Local result: $localResult")
            result += localResult

        }
        //1953784207
        print("Result: $result")
    }
}