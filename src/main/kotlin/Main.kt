import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel

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

class Snake {
    var cells: LinkedList<Cell> = LinkedList()

    init {
        val cell = Cell(1, 33)
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

fun generateFoodCell(renderer: Renderer): Cell {
    val randomX = generateMultipleOf10(getXOffset())
    val randomY = generateMultipleOf10(getYOffset())
    return Cell(randomX, randomY)
}

class GamePanel(private val renderer: Renderer): JPanel() {
    private val cell = Cell(0, 0)
    private var snake = Snake()
    private var foodCell = generateFoodCell(renderer)
    private val scale: Int = renderer.scale

    init {

        isFocusable = true
        requestFocusInWindow()

        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                super.keyPressed(e)
                if (e == null) return
                when (e.keyCode) {
                    KeyEvent.VK_RIGHT -> moveRight()
                    KeyEvent.VK_LEFT -> moveLeft()
                    KeyEvent.VK_UP -> moveUp()
                    KeyEvent.VK_DOWN -> moveDown()
                }
                repaint()
            }
        })
        /*
        // Timer to move the square every 16 ms (about 60 FPS)
        Timer(16) {
            moveSquare()
            repaint() // Repaint the panel to update the position of the square
        }.start()
        */
    }

    private fun moveLeft() {
        if (cell.x <= 0) cell.x = getXOffset()
        else cell.x -= renderer.scale
    }

    private fun moveRight() {
        if (cell.x >= getXOffset()) cell.x = 0
        else cell.x += renderer.scale
    }

    private fun moveUp() {
        if (cell.y == 0) cell.y = getYOffset()
        else cell.y -= renderer.scale
    }

    private fun moveDown() {
        if (cell.y >= getYOffset()) cell.y = 0
        else cell.y += renderer.scale
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val width = renderer.width
        val height = renderer.height

        if (foodFound(cell, foodCell)) {
            foodCell = generateFoodCell(renderer)
        }

        g.clearRect(0, 0, width, height)

        g.color = Color.RED
        fillCell(g, foodCell)

        g.color = Color.GREEN
        fillCell(g, cell)

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