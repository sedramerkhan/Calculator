package jetbrains.compose.calculator.view

import androidx.compose.foundation.ExperimentalDesktopApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.mouseClickable
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalDesktopApi::class)
@Composable
fun mouseExample(){
    Box {
        var clickableText by remember { mutableStateOf("Click me!") }

        Text(
            modifier = Modifier.mouseClickable(
                onClick = {
                    if (buttons.isPrimaryPressed && keyboardModifiers.isShiftPressed)  {
                        clickableText = "Shift + left-mouse click!"
                    } else {
                        clickableText = "Wrong combination, try again!"
                    }
                }
            ),
            text = clickableText
        )

    }

}