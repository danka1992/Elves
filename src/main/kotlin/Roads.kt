import java.io.File

object Roads {

    fun stepsToReachZZZ() {
        val fileName = "src/main/resources/CamelMaps.txt"
        val file = File(fileName).readLines()
        val instructions = file[0]
        val instructionsSize = instructions.length

        val mapOfNodsRight = HashMap<String, String>()
        val mapOfNodsLeft = HashMap<String, String>()
        for(line in 2..< file.size) {
            val allLetters = file[line].filter { it.isLetter() }
            val key = allLetters.slice(IntRange(0, 2))
            mapOfNodsLeft[key] = allLetters.slice(IntRange(3, 5))
            mapOfNodsRight[key] = allLetters.slice(IntRange(6, 8))
        }

        var result = "AAA"
        println("Result: $result")
        var steps = 0
        while(result != "ZZZ") {
            val index = steps.rem(instructionsSize)
            //println("Index: $index, steps: $steps, instructionSize: $instructionsSize")

            if(instructions[index] == 'L') {
                result = mapOfNodsLeft[result]!!
            } else if (instructions[index] == 'R' ) {
                result = mapOfNodsRight[result]!!
            }
            steps += 1
        }

        println("Steps: $steps")
    }

    fun stepsToReachZZZForGhosts() {
        val fileName = "src/main/resources/CamelMaps.txt"
        val file = File(fileName).readLines()
        val instructions = file[0]
        val instructionsSize = instructions.length

        val mapOfNodsRight = HashMap<String, String>()
        val mapOfNodsLeft = HashMap<String, String>()
        for(line in 2..< file.size) {
            val allLetters = file[line].filter { it.isLetter() || it.isDigit() }
            val key = allLetters.slice(IntRange(0, 2))
            mapOfNodsLeft[key] = allLetters.slice(IntRange(3, 5))
            mapOfNodsRight[key] = allLetters.slice(IntRange(6, 8))
        }

        val mapOfStarts = mapOfNodsLeft.filterKeys { it.last() == 'A' }.keys
        val stepsOfAll = mutableListOf<Int>()

        for(i in mapOfStarts) {
            var result = i
            var steps = 0

            while (result.last() != 'Z') {
                val index = steps.rem(instructionsSize)

                if (instructions[index] == 'L') {
                    result = mapOfNodsLeft[result]!!
                } else if (instructions[index] == 'R') {
                    result = mapOfNodsRight[result]!!
                }
                steps += 1
            }
            stepsOfAll.add(steps)
        }

        val allSteps = lcm(stepsOfAll.map { it.toLong() })

        println("Steps to all Zs: $allSteps, steps: $stepsOfAll")
    }


    // 12,927,600,769,609

    private fun lcm(numbers: List<Long>): Long {
        println(numbers.fold(numbers[0]) { x: Long, y: Long -> x * y / gcd(x, y) })
        return numbers.reduce { x: Long, y: Long -> x * y / gcd(x, y) }
    }

    private fun gcd(a: Long, b: Long): Long {
        return if (b == 0L) a else gcd(b, a % b)
    }
}