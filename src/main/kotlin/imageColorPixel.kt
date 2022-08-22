package jetbrains.compose.calculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageBitmap

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAwtImage
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import java.awt.Color.*
import java.io.File
import java.net.URL

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)

fun loadImageBitmap(file: File): ImageBitmap =
    file.inputStream().buffered().use(::loadImageBitmap)
@Composable
fun TouchOnImageExample() {

    val imageBitmap :ImageBitmap = loadImageBitmap(File("C:\\Users\\Sedra\\Desktop\\Compose Desktop Projects\\Calculator\\src\\main\\resources\\images.jpg"))
//    val imageBitmap: ImageBitmap = ImageBitmap.imageResource(
//        LocalContext.current.resources,
//        R.drawable.landscape6
//    )

    val bitmapWidth = imageBitmap.width
    val bitmapHeight = imageBitmap.height

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var imageSize by remember { mutableStateOf(Size.Zero) }

    // These are for debugging
    var text by remember { mutableStateOf("") }
    var colorInTouchPosition by remember { mutableStateOf(Color.Unspecified) }

    val imageModifier = Modifier
        .background(Color.LightGray)
        .fillMaxWidth()
        // This is for displaying different ratio, optional
        .aspectRatio(4f / 3)
        .pointerInput(Unit) {
            detectTapGestures { offset: Offset ->
                // Touch coordinates on image
                offsetX = offset.x
                offsetY = offset.y

// Scale from Image touch coordinates to range in Bitmap
                val scaledX = (bitmapWidth/imageSize.width)*offsetX
                val scaledY = (bitmapHeight/imageSize.height)*offsetY


// TODO This section needed when Bitmap does not fill Image completely
// However i couldn't find a solution to find spaces correctly

//                // Need to calculate spaces at edges of the bitmap inside Image Composable if
//                // not exactly filling the bounds of Image
//                val startImageX = 0f
//                val startImageY = 0f
//
//                // End positions, this might be less than Image dimensions if bitmap doesn't fit Image
//                val endImageX = imageSize.width - startImageX
//                val endImageY = imageSize.height - startImageY
//                val scaledX =
//                    scale(
//                        start1 = startImageX,
//                        end1 = endImageX,
//                        pos = offsetX,
//                start2 = 0f,
//                        end2 = bitmapWidth.toFloat()
//                    ).coerceAtMost(bitmapWidth.toFloat())

//                val scaledY =
//                    scale(
//                        start1 = startImageY,
//                        end1 = endImageY,
//                        pos = offsetY,
//                        start2 = 0f,
//                        end2 = bitmapHeight.toFloat()
//                    ).coerceAtMost(bitmapHeight.toFloat())

                try {

                    val pixel: Int =
                        imageBitmap
                            .asAwtImage()
                            .getRGB(scaledX.toInt(), scaledY.toInt())

//                    val pixel: Int =
//                        imageBitmap
//                            .asAndroidBitmap()
//                            .getPixel(scaledX.toInt(), scaledY.toInt())

                    // Don't know if there is a Compose counterpart for this
//                    val red = android.graphics.Color.red(pixel)
//                    val green = android.graphics.Color.green(pixel)
//                    val blue = android.graphics.Color.blue(pixel)


                    text = "Image Touch: $offsetX, offsetY: $offsetY\n" +
                            "size: $imageSize\n" +
                            "bitmap width: ${bitmapWidth}, height: $bitmapHeight\n" +
                            "scaledX: $scaledX, scaledY: $scaledY\n"
//                    + "red: $red, green: $green, blue: $blue\n"

//                    colorInTouchPosition = Color(red,green,blue)
                    colorInTouchPosition = Color(pixel)
                }catch (e:Exception){
                    println("Exception e: ${e.message}")
                }
            }
        }
        .onSizeChanged { imageSize = it.toSize() }

    Column {
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = imageModifier,
            contentScale = ContentScale.FillBounds
        )
        Row {
            Box(
                modifier = Modifier
                    .then(
                        if (colorInTouchPosition == Color.Unspecified) {
                            Modifier
                        } else {
                            Modifier.background(colorInTouchPosition)
                        }
                    )
                    .size(100.dp)
            )
            Text(text = text)

        }

    }

}


/**
 * Interpolate position x linearly between start and end
 */
fun lerp(start: Float, end: Float, amount: Float): Float {
    return start + amount * (end - start)
}

/**
 * Scale x1 from start1..end1 range to start2..end2 range
 */
fun scale(start1: Float, end1: Float, pos: Float, start2: Float, end2: Float) =
    lerp(start2, end2, calculateFraction(start1, end1, pos))

/**
 * Calculate fraction for value between a range [end] and [start] coerced into 0f-1f range
 */
fun calculateFraction(start: Float, end: Float, pos: Float) =
    (if (end - start == 0f) 0f else (pos - start) / (end - start)).coerceIn(0f, 1f)
