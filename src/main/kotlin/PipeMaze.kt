import org.jetbrains.kotlinx.multik.api.d2array
import org.jetbrains.kotlinx.multik.api.d2arrayIndices
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.ndarray.data.D2Array
import org.jetbrains.kotlinx.multik.ndarray.data.get
import org.jetbrains.kotlinx.multik.ndarray.data.r
import org.jetbrains.kotlinx.multik.ndarray.operations.find
import org.jetbrains.kotlinx.multik.ndarray.operations.indexOf
import org.jetbrains.kotlinx.multik.ndarray.operations.scanMultiIndexed
import java.io.File
import java.util.concurrent.CompletableFuture.anyOf

object PipeMaze {
    fun maze() {
        val fileName = "src/main/resources/PipeMaze.txt"
        val file = File(fileName).readLines()
        /*val matrixString = file.replace('S', '0')
            .replace('|', '1')
            .replace('7', '2')
            .replace('-', '3')
            .replace('J', '4')
            .replace('L', '5')
            .replace('F', '6')
            .replace('.', '9')
            .split("\n")*/

        //println(matrixString)
        //val mazeMap : Array<Array<Char>> = arrayOf()
        var start: Pair<Int, Int> = Pair(-1, -1)
        val mazeMap = Array(file.size) { row ->
            Array(file[row].length) { col ->
                file[row][col]
            }
        }

        mazeMap.forEachIndexed { row, charArray ->
            charArray.forEachIndexed { col, c -> if(c == 'S') start = Pair(row, col) }
        }

        //first steps

        // mazeMap[start.first][start.second] = 'X'
        //dve cesty kym sa nestretnu
        //var road1Index = Pair(start.first - 1, start.second)
        //var road2Index = Pair(start.first + 1, start.second)

        val path1 = mutableListOf<Pair<Int, Int>>()
        val path2 = mutableListOf<Pair<Int, Int>>()

        var road1Index = Pair(start.first + 1, start.second)
        var road2Index = Pair(start.first, start.second + 1)
        var steps = 1

        while(road1Index != road2Index) {
            path1.add(road1Index)
            road1Index = mazeMap.nextStep(road1Index)
            println(road1Index)
            path2.add(road2Index)
            road2Index = mazeMap.nextStep(road2Index)
            steps ++
        }

        println("Steps: $steps")
        println("Path1: $path1")
        println("Path2: $path2" )
        //val visited = Array(mazeMap.size) { Array(mazeMap[0].size) { false } }
        //val count = floodFillCount(mazeMap, startX, startY, visited) // startX and startY should be inside your area
        //println("Number of tiles inside: $count")

        val count = countEnclosedTiles(path1, path2)
        println("Number of enclosed tiles: $count")

        mazeMap.forEach {

        }
        println("Tiles inside: ${mazeMap.enclosedTiles()}")
        mazeMap.forEach {
            println("New map: ${it.joinToString()}")
        }
    }

    private fun Array<Array<Char>>.enclosedTiles(): Int {
        var tilesInside = 0
        this.forEachIndexed { row, line ->
            var inside = false
            var upOrDown : Char = 'X'
            line.forEachIndexed { index, c ->
                var counter = 0
                //val column = this.map { it[index] }.slice(IntRange(0, row))
                //counter = column.count { it == 'X' }
                val countInLineM = line.sliceArray(IntRange(index, line.size -1)).joinToString("").replace(Regex("\\*X+\\*"), "*X*").replace(Regex("X+\\*"), "X*").replace(Regex("\\*X+"), "*X")
                println(countInLineM)
                //F*XSXXXXXX
                val countInLine = countInLineM.count {it == '*' || it == 'X'}

                if(countInLine % 2 != 0 && c != 'X' && c != '*') {
                    tilesInside++
                    line[index] = 'O'
                }
            }
//            line.forEachIndexed { col, it ->
//                if(it != 'E' && it != 'U' && it != 'R' && it != 'D' && it != 'S') {
//                    if(inside) {
//                        tilesInside++
//                        this[row][col] = 'X'
//                    }
//                }
//                    val count = line.sliceArray(IntRange(col, line.size - 1)).count { it == 'U' || it == 'D' }
//                    if((it == 'U' && upOrDown != 'U') || (it == 'D' && upOrDown != 'D' ))  {
//                        inside = !inside
//                        upOrDown = it
//                    }
//                    if ((it == 'U' && line.getOrNull(col - 1) == 'U') || (it == 'D' && line.getOrNull(col - 1) == 'D')) {
//                        inside = !inside
//                    }
//
//                    if ((it == 'U' || it == 'D') && count == 1) {
//                        inside = false
//                    }
//
//
//            }
        }
        return tilesInside
    }

    fun markPath(grid: Array<Array<Boolean>>, path: List<Pair<Int, Int>>) {
        path.forEach { (x, y) ->
            grid[x*2][y*2] = true
            grid[2*x + 1][2 * y] = true
            grid[2*x][2 * y + 1] = true
        }
    }

    fun floodFill(grid: Array<Array<Boolean>>, x: Int, y: Int) {
        if (x !in 0 until 40 || y !in 0 until 40 || grid[x][y]) return
        grid[x][y] = true
        floodFill(grid, x + 1, y)
        floodFill(grid, x - 1, y)
        floodFill(grid, x, y + 1)
        floodFill(grid, x, y - 1)
    }

    fun countEnclosedTiles(path1: List<Pair<Int, Int>>, path2: List<Pair<Int, Int>>): Int {
        val grid = Array(40) { Array(40) { false } }

        // Mark the paths on the grid
        markPath(grid, path1)
        markPath(grid, path2)



        // Use flood-fill from the edges to mark accessible tiles
        for (x in 0 until 20) {
            floodFill(grid, x, 0)
            floodFill(grid, x, 20 - 1)
        }
        for (y in 0 until 20) {
            floodFill(grid, 0, y)
            floodFill(grid, 20 - 1, y)
        }

        grid.forEach {
            it.forEach { bool ->
                print(bool)
            }
            println("\n")
        }

        // Count unmarked (enclosed) tiles
        return grid.sumBy { row -> row.count { !it } }
    }

    private fun Array<Array<Char>>.nextStep(elementIndex: Pair<Int, Int>) : Pair<Int, Int> {
        val element = this[elementIndex.first][elementIndex.second]
        this[elementIndex.first][elementIndex.second] = 'X'
        when(element){
            'F' -> { val rightElement = this[elementIndex.first][ elementIndex.second + 1]
                if(rightElement == '7' || rightElement == 'J' || rightElement == '-') {
                    return Pair(elementIndex.first, elementIndex.second + 1)
                }
                    val lowerElement = this[elementIndex.first + 1][ elementIndex.second]
                if(lowerElement == '|' || lowerElement == 'J' || lowerElement == 'L') {
                    this[elementIndex.first][elementIndex.second] = '*'
                    return Pair(elementIndex.first + 1, elementIndex.second)
                }
            }
            '7' -> { val leftElement = this[elementIndex.first][ elementIndex.second - 1]
                if(leftElement == 'F' || leftElement == 'L' || leftElement == '-') {
                    return Pair(elementIndex.first, elementIndex.second - 1)
                }
                val lowerElement = this[elementIndex.first + 1][ elementIndex.second]
                if(lowerElement == '|' || lowerElement == 'J' || lowerElement == 'L'){
                    this[elementIndex.first][elementIndex.second] = '*'
                    return Pair(elementIndex.first + 1, elementIndex.second)
                }
            }
            '|' -> { val upperElement = this[elementIndex.first - 1][ elementIndex.second]
                if(upperElement == 'F' || upperElement == '7' || upperElement == '|') {
                    this[elementIndex.first][elementIndex.second] = '*'
                    return Pair(elementIndex.first - 1, elementIndex.second)
                }
                val lowerElement = this[elementIndex.first + 1][ elementIndex.second]
                if(lowerElement == '|' || lowerElement == 'J' || lowerElement == 'L'){
                    this[elementIndex.first][elementIndex.second] = '*'
                    return Pair(elementIndex.first + 1, elementIndex.second)
                }
            }
            '-' -> { val leftElement = this[elementIndex.first][ elementIndex.second - 1]
                if(leftElement == 'F' || leftElement == 'L' || leftElement == '-'){
                    return Pair(elementIndex.first, elementIndex.second - 1)
                }
                val rightElement = this[elementIndex.first][ elementIndex.second + 1]
                if(rightElement == '7' || rightElement == 'J' || rightElement == '-'){
                    return Pair(elementIndex.first, elementIndex.second + 1)
                }
            }
            'J' -> { val leftElement = this[elementIndex.first][ elementIndex.second - 1]
                if(leftElement == 'F' || leftElement == 'L' || leftElement == '-'){
                    return Pair(elementIndex.first, elementIndex.second - 1)
                }
                val upperElement = this[elementIndex.first - 1][ elementIndex.second]
                if(upperElement == 'F' || upperElement == '7' || upperElement == '|'){
                    this[elementIndex.first][elementIndex.second] = '*'
                    return Pair(elementIndex.first - 1, elementIndex.second)
                }
            }
            'L' -> { val rightElement = this[elementIndex.first][ elementIndex.second + 1]
                if(rightElement == '7' || rightElement == 'J' || rightElement == '-'){
                    return Pair(elementIndex.first, elementIndex.second + 1)
                }
                val upperElement = this[elementIndex.first - 1][ elementIndex.second]
                if(upperElement == 'F' || upperElement == '7' || upperElement == '|'){
                    this[elementIndex.first][elementIndex.second] = '*'
                    return Pair(elementIndex.first - 1, elementIndex.second)
                }
            }
            else -> return Pair(-1, -1)
        }
        return Pair(-1, -1)
    }
}