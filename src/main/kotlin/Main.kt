package jetbrains.compose.calculator

import TestJava
import TTOpen
import androidx.compose.desktop.Window
import androidx.compose.foundation.ExperimentalDesktopApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.mouseClickable
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntSize
import jetbrains.compose.calculator.resources.Assets
//import jetbrains.compose.calculator.resources.Assets
import jetbrains.compose.calculator.resources.lightThemeColors
import jetbrains.compose.calculator.view.DisplayPanel
import jetbrains.compose.calculator.view.Keyboard
import androidx.compose.material.Text as Text1

const val DEFAULT_WIDTH = 500
const val DEFAULT_HEIGHT = 500

@OptIn(ExperimentalDesktopApi::class)
fun main() = Window(
    title = "Compose Calculator - simply-how.com",
    size = IntSize(DEFAULT_WIDTH, DEFAULT_HEIGHT),
    icon = Assets.WindowIcon
) {
    val test = TestJava()

    MaterialTheme(colors = lightThemeColors) {
        val mainOutput = remember { mutableStateOf(TextFieldValue("0")) }
        Column(Modifier.fillMaxHeight()) {
//            Button(
//             onClick = {}
//            ){
//                Text(text= test.text)
//            }
            DisplayPanel(
                Modifier.weight(1f),
                mainOutput
            )
            Keyboard(
                Modifier.weight(4f),
                mainOutput
            )
        }
    }
}
