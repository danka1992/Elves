import java.io.File

class Gondola {

    private fun readFileByLines(): List<String> {
        val fileName = "src/main/resources/GondolaEngin.txt"
        return File(fileName).readLines()
    }

    fun gearsCount() {
        val file =  readFileByLines()
        val numbers = mutableListOf<Sequence<MatchResult>>()
        val asterisks = mutableListOf<Sequence<MatchResult>>()
        var result = 0

        file.forEachIndexed { index, line ->
            numbers.add(index, Regex("[0-9]+").findAll(line))
            asterisks.add(index, Regex("\\*").findAll(line))
        }
        asterisks.forEachIndexed { index, lineOfAsterisks ->
            lineOfAsterisks.forEach { asterisk ->
                val indexInLineAsterisk = asterisk.range.first
                val numbersNextAsterisk = mutableListOf<Int>()
                numbers.getOrNull(index - 1)?.forEach {
                    if(it.range.contains(indexInLineAsterisk - 1) ||
                                it.range.contains(indexInLineAsterisk) ||
                                it.range.contains(indexInLineAsterisk + 1)) {
                        numbersNextAsterisk.add(it.value.toInt())
                    }
                }
                numbers.getOrNull(index)?.forEach {
                    if(it.range.contains(indexInLineAsterisk - 1) ||
                        it.range.contains(indexInLineAsterisk) ||
                        it.range.contains(indexInLineAsterisk + 1)) {
                        numbersNextAsterisk.add(it.value.toInt())
                    }
                }
                numbers.getOrNull(index + 1)?.forEach {
                    if(it.range.contains(indexInLineAsterisk - 1) ||
                        it.range.contains(indexInLineAsterisk) ||
                        it.range.contains(indexInLineAsterisk + 1)) {
                        numbersNextAsterisk.add(it.value.toInt())
                    }
                }

                if(numbersNextAsterisk.size == 2) {
                    result += numbersNextAsterisk[0] * numbersNextAsterisk[1]
                }
            }
        }
        println("Sum of all numbers next asterisks is: $result")
    }

    fun countNumbersNearSymbols() {
        val file =  readFileByLines()
        val numbers = mutableListOf<Sequence<MatchResult>>()
        file.forEachIndexed { index, line ->
            numbers.add(index, Regex("[0-9]+").findAll(line))
        }
        var sumOfNumbers = 0
        numbers.forEachIndexed { index, lineOfNumbers ->
            lineOfNumbers.forEach { number ->
                val start = if(number.range.first - 1 < 0) 0 else number.range.first - 1
                val end = if(number.range.last + 1 > 139) 139 else number.range.last + 1
                val symbolRange = IntRange(start, end)
                println("Cislo ${number.value}, na riadku $index")

                val upperLine = file.getOrNull(index - 1)?.substring(symbolRange)?.filterNot { it == '.' || it.isDigit()}.isNullOrEmpty()
                val line = file.getOrNull(index)?.substring(symbolRange)?.filterNot { it == '.' || it.isDigit()}.isNullOrEmpty()
                val bottomLine = file.getOrNull(index + 1)?.substring(symbolRange)?.filterNot { it == '.' || it.isDigit()}.isNullOrEmpty()

                if(!upperLine || !line || !bottomLine) {
                    sumOfNumbers += number.value.toInt()
                    println("Cislo je: ${number.value}, sucet je $sumOfNumbers.")
                }
            }
        }
        println("Sum of all numbers with symbols is: $sumOfNumbers")
    }
}