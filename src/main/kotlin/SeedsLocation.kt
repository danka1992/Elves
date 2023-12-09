import java.io.File

object SeedsLocation {
 /*Seed 79, soil 81, fertilizer 81, water 81, light 74, temperature 78, humidity 78, location 82.
Seed 14, soil 14, fertilizer 53, water 49, light 42, temperature 42, humidity 43, location 43.
Seed 55, soil 57, fertilizer 57, water 53, light 46, temperature 82, humidity 82, location 86.
Seed 13, soil 13, fertilizer 52, water 41, light 34, temperature 34, humidity 35, location 35 */
    fun seedsLocationMin() {
        val fileName = "src/main/resources/SeedsLocation.txt"
        val file = File(fileName).readText()
        val allTables = file.split("\n\n")
        val tables = mutableListOf<MutableList<Pair<LongRange, LongRange>>>()
        allTables.subList(1, allTables.size).forEachIndexed { index, it ->
            val splitTable = it.split(": ", "\n")
            val list = mutableListOf<Pair<LongRange, LongRange>>()
            splitTable.subList(1, splitTable.size).forEachIndexed { index2, table ->
                val data = table.split(" ").map { it.toLong() }
                list.add(Pair(LongRange(data[0], data[0] + data[2]), LongRange(data[1], data[1] + data[2])))
            }
            println("List:  $list")
            tables.add(list)
        }
        val seeds = allTables[0].split(": ", " ")
        val seedsNumbers = seeds.subList(1, seeds.size).map { it.toLong() }
        var minimum = 1000000000000000L
        seedsNumbers.forEachIndexed { index, seed ->
            var seedMapped: Long = seed
            tables.forEach { table ->
                var seedFoundInTable = 0L
                table.forEach { line ->
                    if(line.second.contains(seedMapped)) {
                        seedFoundInTable = line.first.first + (seedMapped - line.second.first)
                        println("${line.second.first} + $seed - ${line.first.first}")
                        println("Seed found, index $index, $seedMapped, seedFoundInTable = $seedFoundInTable")
                    }
                }
                if(seedFoundInTable != 0L) seedMapped = seedFoundInTable

            }
            if(minimum > seedMapped) minimum = seedMapped
            println("Minimum: $minimum")
            println("SeedMapped $index $seedMapped")
        }

        println("Minimum: $minimum")
    }

    fun seedsRangesLocationMin() {
        val fileName = "src/main/resources/SeedsLocation.txt"
        val file = File(fileName).readText()
        val allTables = file.split("\n\n")
        val tables = mutableListOf<MutableList<Pair<LongRange, LongRange>>>()
        allTables.subList(1, allTables.size).forEachIndexed { index, it ->
            val splitTable = it.split(": ", "\n")
            val list = mutableListOf<Pair<LongRange, LongRange>>()
            splitTable.subList(1, splitTable.size).forEachIndexed { index2, table ->
                val data = table.split(" ").map { it.toLong() }
                list.add(Pair(LongRange(data[0], data[0] + data[2] - 1), LongRange(data[1], data[1] + data[2] - 1)))
            }
            //println("List:  $list")
            tables.add(list)
        }
        val seeds = allTables[0].split(": ", " ")
        val seedsNumbers = seeds.subList(1, seeds.size).map { it.toLong() }
        val seedRanges = mutableListOf<LongRange>()
        for(i in seedsNumbers.indices step 2){
            seedRanges.add(LongRange(seedsNumbers[i], seedsNumbers[i] + seedsNumbers[i+1] - 1))
        }
        var minimum = 1000000000000000L
        seedRanges.forEach {
            it.forEach { seed ->
            var seedMapped: Long = seed
            tables.forEach tables@ { table ->
                //println("SeedMapped $seedMapped")
                var seedFoundInTable = 0L
                table.forEach { line ->
                    if (line.second.contains(seedMapped)) {
                        seedFoundInTable = line.first.first + (seedMapped - line.second.first)
                        //println("${line.second.first} + $seedMapped - ${line.first.first}")
                        //println("Seed found, index $index, $seedMapped, seedFoundInTable = $seedFoundInTable")
                    }
                }
                if(seedFoundInTable != 0L) seedMapped = seedFoundInTable

            }
            if (minimum > seedMapped) minimum = seedMapped
            //println("Minimum: $minimum \n")
        }
        }

        println("Minimum: $minimum")
    }
}