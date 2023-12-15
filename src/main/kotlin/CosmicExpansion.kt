import java.io.File
import kotlin.math.abs

object CosmicExpansion {

    fun shortestPathBetweenGalaxies() {
        val fileName = "src/main/resources/Galaxies.txt"
        val matrix = File(fileName).readLines().map { it.toCharArray().toMutableList() }.toMutableList()
        //val matrix = mutableListOf<MutableList<Char>>()
        //file.forEachIndexed { rowIndex, line -> matrix.add(line.toMutableList()) }

        val numberOfRows = matrix.size
        val numberOfColumns = matrix[0].size
        val emptyColumn = Array<Int>(numberOfColumns) { 0 }
        val emptyLine = Array<Int>(numberOfRows) { 0 }
        var counter = 1
        val galaxiesPositions = mutableListOf<Pair<Int, Int>>()
        matrix.forEachIndexed { rowIndex, matrixRow ->
            matrixRow.forEachIndexed { columnIndex, c ->
                if(c == '#')  {
                    emptyColumn[columnIndex] += 1
                    emptyLine[rowIndex] += 1
                    matrixRow[columnIndex] ='X'
                    counter ++
                }
            }
        }

        val list = MutableList(matrix[0].size) {1}
        val map = MutableList(matrix.size) {list}

        emptyLine.forEachIndexed { index, i ->
            if (i == 0) {
                map[index] = MutableList(map[0].size) {1000000}
            }
        }


        emptyColumn.forEachIndexed { index, i ->
            if (i == 0) {
                map.forEachIndexed { _, ints ->
                    ints[index] = 1000000
                }
            }
        }
        //1284771987195
        //702770569197

        map.forEach { println(it) }
        var walkingThrough = 1
        var position = 0

        for(i in 0..< matrix.size) {
            position = matrix[i].indexOf('X')
            while(position != -1 && walkingThrough < counter){
                matrix[i][position] = 'W'
                walkingThrough ++
                galaxiesPositions.add(Pair(i, position))
                position = matrix[i].indexOf('X')
            }
        }

        var sumShortestPath = 0L

        repeat(galaxiesPositions.size - 1) {
            for(i in it..< galaxiesPositions.size - 1) {
                for (j in 0..< abs(galaxiesPositions[i + 1].second - galaxiesPositions[it].second)) {
                    val pos = galaxiesPositions[i + 1].second.compareTo(galaxiesPositions[it].second)
                    //println("1 position: ${galaxiesPositions[it].first} and 2 position: ${galaxiesPositions[it].second + j}")
                    sumShortestPath += map[galaxiesPositions[it].first][galaxiesPositions[it].second + j*pos]
                }

                for(j in 0..< abs(galaxiesPositions[i + 1].first - galaxiesPositions[it].first)) {
                    val pos = galaxiesPositions[i + 1].first.compareTo(galaxiesPositions[it].first)
                    sumShortestPath += map[galaxiesPositions[it].first + j*pos][galaxiesPositions[i + 1].second]
                }


                //val path = abs(galaxiesPositions[it].first - galaxiesPositions[i + 1].first) + abs(galaxiesPositions[it].second - galaxiesPositions[i + 1].second)
                //sumShortestPath += path
                //println("Galaxy ${galaxiesPositions[it]} and galaxy ${galaxiesPositions[i + 1]}, path $path")
                //println("SUM: $sumShortestPath")
            }
        }

        println("Shortest path: $sumShortestPath")

    }

    fun shortestPathBetweenGalaxiesFirst() {
        val fileName = "src/main/resources/Galaxies.txt"
        val matrix = File(fileName).readLines().map { it.toCharArray().toMutableList() }.toMutableList()
        //val matrix = mutableListOf<MutableList<Char>>()
        //file.forEachIndexed { rowIndex, line -> matrix.add(line.toMutableList()) }

        val numberOfRows = matrix.size
        val numberOfColumns = matrix[0].size
        val emptyColumn = Array<Int>(numberOfColumns) { 0 }
        val emptyLine = Array<Int>(numberOfRows) { 0 }
        var counter = 1
        val galaxiesPositions = mutableListOf<Pair<Long, Long>>()
        matrix.forEachIndexed { rowIndex, matrixRow ->
            matrixRow.forEachIndexed { columnIndex, c ->
                if(c == '#')  {
                    emptyColumn[columnIndex] += 1
                    emptyLine[rowIndex] += 1
                    matrixRow[columnIndex] ='X'
                    counter ++
                }
            }
        }

        val list = List(matrix[0].size) {1}
        val map = List(matrix.size) {list}

        emptyLine.reversed().forEachIndexed { index, i ->
            if (i == 0) {
                matrix.add(numberOfRows - index, MutableList(matrix[0].size) { '.' })
            }
        }


        emptyColumn.reversed().forEachIndexed { index, i ->
            if (i == 0) {
                matrix.forEach { it.add(numberOfColumns - index, '.' ) }
            }
        }

        var walkingThrough = 1
        var position = 0

        for(i in 0..< matrix.size) {
            position = matrix[i].indexOf('X')
            while(position != -1 && walkingThrough < counter){
                matrix[i][position] = 'W'
                walkingThrough ++
                galaxiesPositions.add(Pair(i.toLong(), position.toLong()))
                position = matrix[i].indexOf('X')
            }
        }

        var sumShortestPath = 0L

        repeat(galaxiesPositions.size) {
            for(i in it..< galaxiesPositions.size - 1) {
                val path = abs(galaxiesPositions[it].first - galaxiesPositions[i + 1].first) + abs(galaxiesPositions[it].second - galaxiesPositions[i + 1].second)
                sumShortestPath += path
                //println("Galaxy ${galaxiesPositions[it]} and galaxy ${galaxiesPositions[i + 1]}, path $path")
                //println("SUM: $sumShortestPath")
            }
        }

        println("Shortest path: $sumShortestPath")

    }
}