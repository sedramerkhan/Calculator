@file:Suppress("SameParameterValue")

package jetbrains.compose.calculator.resources

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object Assets {
    val OutlineBackspace = vectorBuilder().addPath(
        pathData = parsePath("M22,3L7,3c-0.69,0 -1.23,0.35 -1.59,0.88L0,12l5.41,8.11c0.36,0.53 0.9,0.89 1.59,0.89h15c1.1,0 2,-0.9 2,-2L24,5c0,-1.1 -0.9,-2 -2,-2zM22,19L7.07,19L2.4,12l4.66,-7L22,5v14zM10.41,17L14,13.41 17.59,17 19,15.59 15.41,12 19,8.41 17.59,7 14,10.59 10.41,7 9,8.41 12.59,12 9,15.59z"),
        fill = SolidColor(Color.Blue)
    ).build()

    val WindowIcon = imageFromFile("/icon.png")
}

private fun vectorBuilder() = ImageVector.Builder(
    defaultWidth = 24.dp,
    defaultHeight = 24.dp,
    viewportWidth = 24f,
    viewportHeight = 24f
)

private fun parsePath(pathStr: String) = PathParser().parsePathString(pathStr).toNodes()

fun imageFromFile(path: String): BufferedImage {
    var image: BufferedImage? = null
    try {
        image = ImageIO.read(Assets.javaClass.getResource(path))
    } catch (e: Exception) {
        // image file does not exist
    }

    if (image == null) {
        image = BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)
    }

    return image
}

