package entities

import enums.Direction
import ui.getXOffset
import ui.getYOffset
import ui.isHead
import java.util.LinkedList

class Snake(private val scale: Int) {
    var direction: Direction = Direction.RIGHT
    var cells: LinkedList<Cell> = LinkedList()
    var head: Cell

    init {
        val cell = Cell(0, 0)
        cells.add(cell)

        head = cells[0]

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

    fun hasFoundFood(foodCell: Cell): Boolean {
        return head.x == foodCell.x && head.y == foodCell.y
    }

    fun move() {
        val originalPositions = cells.map {
            it.copy()
        }

        for ((index, cell) in cells.withIndex()) {
            if (isHead(index)) {
                when (direction) {
                    Direction.LEFT -> moveToTheLeft()
                    Direction.RIGHT -> moveToTheRight()
                    Direction.UP -> moveToTheTop()
                    Direction.DOWN -> moveToTheBottom()
                }
            } else {
                val previousCellIndex = index - 1
                cell.x = originalPositions[previousCellIndex].x
                cell.y = originalPositions[previousCellIndex].y
            }
        }
    }

    private fun moveToTheLeft() {
        if (head.x <= 0) head.x = getXOffset()
        else head.x -= scale
    }

    private fun moveToTheRight() {
        if (head.x >= getXOffset()) head.x = 0
        else head.x += scale
    }

    private fun moveToTheTop() {
        if (head.y == 0) head.y = getYOffset()
        else head.y -= scale
    }

    private fun moveToTheBottom() {
        if (head.y >= getYOffset()) head.y = 0
        else head.y += scale
    }
}