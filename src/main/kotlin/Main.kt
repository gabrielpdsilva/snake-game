import ui.GamePanel
import ui.Renderer
import javax.swing.JFrame

fun main() {
    val frame = JFrame("Snake Game")

    val renderer = Renderer()
    val panel = GamePanel(renderer)

    frame.add(panel)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    frame.setSize(renderer.width, renderer.height)
    frame.setLocationRelativeTo(null)

    frame.isResizable = false
    frame.isVisible = true
}
