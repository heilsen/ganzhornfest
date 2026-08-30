package de.heilsen.ganzhornfest.theme.component.ticket

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

@Composable
fun TicketShape(density: Density = LocalDensity.current) =
    GenericShape { size, _ ->
        drawTicketPath(size, density)
    }

fun Path.drawTicketPath(
    size: Size,
    density: Density,
): Path {
    val corner = with(density) { 12.dp.toPx() }
    val notch = with(density) { 10.dp.toPx() }
    val minGap = with(density) { 8.dp.toPx() }
    val endInset = corner + notch + minGap
    val span = size.height - 2f * endInset
    val count =
        if (span < 0f) {
            0
        } else {
            (1 + (span / (2f * notch + minGap)).toInt()).coerceIn(1, 5)
        }

    val outline =
        Path().apply {
            addRoundRect(
                RoundRect(
                    left = 0f,
                    top = 0f,
                    right = size.width,
                    bottom = size.height,
                    cornerRadius = CornerRadius(corner, corner),
                ),
            )
        }
    if (count == 0) {
        addPath(outline)
        return this
    }

    val punches =
        Path().apply {
            for (index in 0 until count) {
                val y =
                    if (count == 1) {
                        size.height / 2f
                    } else {
                        endInset + span * index / (count - 1)
                    }
                addOval(Rect(Offset(0f, y), notch))
                addOval(Rect(Offset(size.width, y), notch))
            }
        }
    op(outline, punches, PathOperation.Difference)
    return this
}
