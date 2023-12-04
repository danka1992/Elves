import java.io.File

class BagWithColors {

    private fun readFileByLines(): List<String> {
        val fileName = "src/main/resources/BagGame.txt"
        return File(fileName).readLines()
    }

    fun sumOfGames(){
        val lines = readFileByLines()
        var sumOfGamesIndex = 0
        lines.forEachIndexed { index, line ->
            val possibleToPlay = possibleToPlayGame(line)
            println("Index: $index a je mozne ju hrat $possibleToPlay")
            if(possibleToPlay) sumOfGamesIndex += index + 1
        }
        println("Sucet indexov je: $sumOfGamesIndex")
    }

    fun minimumsForAllGamesMultiplied() {
        val lines = readFileByLines()
        var sumOfMultipliedValues = 0
        lines.forEach{ line ->
            val result = resultOneGame(line)
            sumOfMultipliedValues += result
        }
        println("Sucet vynasobenych kociek je: $sumOfMultipliedValues")
    }

    private fun possibleToPlayGame(line: String): Boolean {
        val splitGames = line.split(": ")[1].split("; ")
        splitGames.forEach { oneGame ->
            val map = mutableMapOf<String, Int>()
            oneGame.split(", ").forEach {
                map[it.split(" ")[1]] = it.split(" ")[0].toInt()
            }
            println("Mapa hier: $map")
            val possibleToPlay = filterGames(map)
            if (!possibleToPlay) return false
        }
        return true
    }

    private fun resultOneGame(line: String) : Int {
        val splitGames = line.split(": ")[1].split("; ")
        val allGamesOnOneLine = mutableListOf<MutableMap<String, Int>>()
        splitGames.forEach { oneGame ->
            val map = mutableMapOf<String, Int>()
            oneGame.split(", ").forEach {
                map[it.split(" ")[1]] = it.split(" ")[0].toInt()
            }
            allGamesOnOneLine.add(map)
        }

        val blue = mutableListOf<Int>()
        val green = mutableListOf<Int>()
        val red = mutableListOf<Int>()
        allGamesOnOneLine.forEachIndexed { index, mutableMap ->
            blue.add(index, mutableMap.getOrDefault("blue", 0))
            green.add(index, mutableMap.getOrDefault("green", 0))
            red.add(index, mutableMap.getOrDefault("red", 0))
        }
        println("Modra = ${blue.max()}, zelena = ${green.max()} a cervena = ${red.max()}")
        return blue.max() * green.max() * red.max()
    }

    private fun filterGames(oneGame: MutableMap<String, Int>) : Boolean {
         oneGame.let {
             if (it.getOrDefault("blue", 0) > 14 ||
                 it.getOrDefault("green", 0) > 13 ||
                 it.getOrDefault("red", 0) > 12) {
                 return false
             }
         }
        return true
    }
}