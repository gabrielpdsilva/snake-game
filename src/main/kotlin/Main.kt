import ui.GamePanel
import ui.RendererConfig
import javax.swing.JFrame

fun main() {
    val rendererConfig = RendererConfig()
    val panel = GamePanel(rendererConfig)

    val frame = JFrame("Snake Game")

    frame.add(panel)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    frame.setSize(rendererConfig.width, rendererConfig.height)
    frame.setLocationRelativeTo(null)

    frame.isResizable = false
    frame.isVisible = true
}
