package ui

import entities.Cell
import entities.Snake
import enums.Direction
import generateFoodCell
import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JPanel
import javax.swing.Timer

class GamePanel(private val rendererConfig: RendererConfig): JPanel() {

    private var snake = Snake(rendererConfig.scale)
    private var foodCell = generateFoodCell()
    private val scale: Int = rendererConfig.scale

    init {
        isFocusable = true
        requestFocusInWindow()

        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent?) {
                super.keyPressed(e)
                if (e == null) return
                when (e.keyCode) {
                    KeyEvent.VK_LEFT -> if (snake.direction != Direction.RIGHT) snake.direction = Direction.LEFT
                    KeyEvent.VK_RIGHT -> if (snake.direction != Direction.LEFT) snake.direction = Direction.RIGHT
                    KeyEvent.VK_UP -> if (snake.direction != Direction.DOWN) snake.direction = Direction.UP
                    KeyEvent.VK_DOWN -> if (snake.direction != Direction.UP) snake.direction = Direction.DOWN
                }
            }
        })

        Timer(rendererConfig.timer) {
            snake.move()
            repaint()
        }.start()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        val width = rendererConfig.width
        val height = rendererConfig.height

        // TODO finish
        if (snake.hasTouchedItself()) snake = Snake(rendererConfig.scale)

        if (snake.hasFoundFood(foodCell)) {
            val newCell = Cell(snake.head.x, snake.head.y)
            snake.cells.add(newCell)

            foodCell = generateFoodCell()
        }

        g.clearRect(0, 0, width, height)

        g.color = Color.RED
        fillCell(g, foodCell)

        g.color = Color.BLACK
        snake.cells.map {
            fillCell(g, it)
        }
    }

    private fun fillCell(g: Graphics, cell: Cell) {
        g.fillRect(cell.x, cell.y, scale, scale)
    }
}
