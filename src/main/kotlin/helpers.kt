import entities.Cell

fun getXOffset() = 280

fun getYOffset() = 260

fun generateMultipleOf10(max: Int) = (0..max / 10).random() * 10

fun generateFoodCell(): Cell {
    val randomX = generateMultipleOf10(getXOffset())
    val randomY = generateMultipleOf10(getYOffset())
    return Cell(randomX, randomY)
}

// TODO move these files to another place