import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.Timer

fun main() {
    val frame = JFrame("Snake Game")

    val renderer = Renderer()
    val panel = GamePanel(renderer)

    frame.add(panel)
    frame.setSize(renderer.width, renderer.height)

    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.setLocationRelativeTo(null)
    frame.isResizable = false
    frame.isVisible = true
}

class Renderer {
    private val cols = 32
    private val rows = 32

    val scale = 10
    val width = cols * scale
    val height = rows * scale
}

enum class Direction {
    RIGHT, LEFT, UP, DOWN
}

class Snake {
    var direction: Direction = Direction.RIGHT
    var cells: LinkedList<Cell> = LinkedList()

    init {
        val cell = Cell(0, 0)
        cells.add(cell)
    }

    fun hasTouchedItself(newCell: Cell): Boolean {
        val found = cells.find {
            return it.x == newCell.x && it.y == newCell.y
        }
        return found != null
    }
}

data class Cell(var x: Int, var y: Int)

fun generateMultipleOf10(max: Int) = (0..max / 10).random() * 10

fun generateFoodCell(): Cell {
    val randomX = generateMultipleOf10(getXOffset())
    val randomY = generateMultipleOf10(getYOffset())
    return Cell(randomX, randomY)
}

class GamePanel(private val renderer: Renderer): JPanel() {
    private var head: Cell
    private var snake = Snake()
    private var foodCell = generateFoodCell()
    private val scale: Int = renderer.scale

    init {

        head = snake.cells[0]

        isFocusable = true
        requestFocusInWindow()

        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                super.keyPressed(e)
                if (e == null) return
                when (e.keyCode) {
                    KeyEvent.VK_RIGHT -> snake.direction = Direction.RIGHT
                    KeyEvent.VK_LEFT -> snake.direction = Direction.LEFT
                    KeyEvent.VK_UP -> snake.direction = Direction.UP
                    KeyEvent.VK_DOWN -> snake.direction = Direction.DOWN
                }
            }
        })

        Timer(100) {
            move(snake.direction)
            repaint()
        }.start()
    }

    private fun move(direction: Direction) {
        println(snake.cells)
        when (direction) {
            Direction.RIGHT -> moveRight()
            Direction.LEFT -> moveLeft()
            Direction.UP -> moveUp()
            Direction.DOWN -> moveDown()
        }
    }

    private fun moveLeft() {
        if (head.x <= 0) head.x = getXOffset()
        else head.x -= renderer.scale

        for ((index, cell) in snake.cells.withIndex()) {
            if (index != 0) {
                val previousCellIndex = index - 1
                cell.x = snake.cells[previousCellIndex].x + renderer.scale
                cell.y = snake.cells[previousCellIndex].y
            }
        }
    }

    private fun moveRight() {
        if (head.x >= getXOffset()) head.x = 0
        else head.x += renderer.scale

        for ((index, cell) in snake.cells.withIndex()) {
            if (index != 0) {
                val previousCellIndex = index - 1
                cell.x = snake.cells[previousCellIndex].x - renderer.scale
                cell.y = snake.cells[previousCellIndex].y
            }
        }
    }

    private fun moveUp() {
        if (head.y == 0) head.y = getYOffset()
        else head.y -= renderer.scale

        for ((index, cell) in snake.cells.withIndex()) {
            if (index != 0) {
                val previousCellIndex = index - 1
                cell.x = snake.cells[previousCellIndex].x
                cell.y = snake.cells[previousCellIndex].y + renderer.scale
            }
        }
    }

    private fun moveDown() {
        if (head.y >= getYOffset()) head.y = 0
        else head.y += renderer.scale

        for ((index, cell) in snake.cells.withIndex()) {
            if (index != 0) {
                val previousCellIndex = index - 1
                cell.x = snake.cells[previousCellIndex].x
                cell.y = snake.cells[previousCellIndex].y - renderer.scale
            }
        }
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val width = renderer.width
        val height = renderer.height

        if (foodFound(head, foodCell)) {
            foodCell = generateFoodCell()

            val newCell = Cell(head.x, head.y)
            snake.cells.add(newCell)
        }

        g.clearRect(0, 0, width, height)

        g.color = Color.RED
        fillCell(g, foodCell)

        g.color = Color.GREEN
        fillCell(g, head)

        g.color = Color.BLACK
        snake.cells.map {
            fillCell(g, it)
        }
    }

    private fun fillCell(g: Graphics, cell: Cell) {
        g.fillRect(cell.x, cell.y, scale, scale)
    }
}

fun foodFound(cell: Cell, foodCell: Cell) = cell.x == foodCell.x && cell.y == foodCell.y

fun getXOffset() = 280

fun getYOffset() = 260