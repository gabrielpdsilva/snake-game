package entities

import enums.Direction
import java.util.*

class Snake {
    var direction: Direction = Direction.RIGHT
    var cells: LinkedList<Cell> = LinkedList()

    init {
        val cell = Cell(0, 0)
        cells.add(cell)
    }

    fun hasTouchedItself(): Boolean {
        if (cells.size == 1) return false

        val head = cells[0]
        val found = cells.withIndex().firstOrNull {
            if (it.index == 0) return false
            val cell = it.value
            return cell.x == head.x && cell.y == head.y
        }
        return found != null
    }
}