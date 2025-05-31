import entities.Cell

// TODO fix offset
fun getXOffset() = 290

fun getYOffset() = 270

fun generateMultipleOf10(max: Int) = (0..max / 10).random() * 10

fun generateFoodCell(): Cell {
    val randomX = generateMultipleOf10(getXOffset())
    val randomY = generateMultipleOf10(getYOffset())
    return Cell(randomX, randomY)
}
