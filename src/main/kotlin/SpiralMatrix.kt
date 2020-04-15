object SpiralMatrix {

    fun ofSize(size: Int): Array<IntArray> {

        val spiralMatrix = Array(size) { IntArray(size) }
        var coordinate = Coordinate(0, 0)
        var direction = Direction.EAST
        var value = 1

        while (coordinate.isValid(spiralMatrix)) {

            spiralMatrix.setValue(coordinate, value)
            value += 1

            if (coordinate.goAhead(direction).isValid(spiralMatrix)) {
                coordinate = coordinate.goAhead(direction)
            } else if (coordinate.turnRight(direction).isValid(spiralMatrix)) {
                coordinate = coordinate.turnRight(direction)
                direction = direction.turnRight()
            } else {
                break
            }
        }

        return spiralMatrix
    }

    private fun Coordinate.isValid(spiralMatrix: Array<IntArray>) =
            this.isInBounds(spiralMatrix.size) && spiralMatrix[y][x] == 0

    private fun Coordinate.isInBounds(size: Int) =
            (x in 0 until size) && (y in 0 until size)

    private fun Coordinate.goAhead(direction: Direction) =
            this + direction.coordinate

    private fun Coordinate.turnRight(direction: Direction) =
            this.goAhead(direction.turnRight())

    private fun Array<IntArray>.setValue(coordinate: Coordinate, value: Int) {
        this[coordinate.y][coordinate.x] = value
    }
}

data class Coordinate(val x: Int, val y: Int) {

    operator fun plus(other: Coordinate) =
            Coordinate(x + other.x, y + other.y)
}

enum class Direction(val coordinate: Coordinate) {

    NORTH(Coordinate(0, -1)),
    EAST(Coordinate(1, 0)),
    SOUTH(Coordinate(0, 1)),
    WEST(Coordinate(-1, 0));

    fun turnRight(): Direction =
            values()[Math.floorMod(ordinal + 1, values().size)]
}
