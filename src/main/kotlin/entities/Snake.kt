package entities

import enums.Direction
import getXOffset
import getYOffset
import java.util.LinkedList

class Snake(private val scale: Int) {
    var direction: Direction = Direction.RIGHT
    val head: Cell = Cell(0, 0)
    var cells: LinkedList<Cell> = LinkedList()

    init {
        cells.add(head)
    }

    fun hasTouchedItself(): Boolean {
        if (!hasBody()) return false

        return cells.withIndex()
            .filterNot {(index) -> isHead(index) }
            .any { it.value.x == head.x && it.value.y == head.y }
    }

    fun hasFoundFood(foodCell: Cell): Boolean {
        return head.x == foodCell.x && head.y == foodCell.y
    }

    fun move() {
        val originalPositions = cells.map {
            it.copy()
        }

        cells.withIndex().forEach { (index, cell) ->
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

    private fun hasBody() = cells.size > 1

    private fun isHead(index: Int) = index == 0

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