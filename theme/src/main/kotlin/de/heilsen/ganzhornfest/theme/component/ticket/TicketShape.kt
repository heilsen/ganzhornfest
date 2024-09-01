package de.heilsen.ganzhornfest.theme.component.ticket

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp

@Composable
fun TicketShape(cornerRadius: Float, density: Density = LocalDensity.current) =
    GenericShape { size, _ ->
        drawTicketPath(size, cornerRadius, density)
    }

fun Path.drawTicketPath(size: Size, cornerRadius: Float, density: Density): Path {
    val smallRadius = with(density) {
        cornerRadius.dp.toPx() / 2
    }
    val bigRadius = with(density) {
        cornerRadius.dp.toPx()
    }
    // just making sure that the 3/5 circles can be drawn might lead to consecutively drawn circles, which is not looking great
    val smallVisualBreak = 10
    val canDraw3Circles: Boolean = size.height - 2 * bigRadius - 2 * smallRadius > smallVisualBreak
    val canDraw5Circles: Boolean = size.height - 2 * bigRadius - 6 * smallRadius > smallVisualBreak
    val space = (size.height - 2*bigRadius - 2*smallRadius -2*smallRadius -2*smallRadius)/4
    return apply {
        reset()
        // left border
        if (canDraw3Circles) {
            drawQuadrant(Offset(0f, 0f), smallRadius, 0f, 90f)
        }
        if (canDraw5Circles) {
            lineTo(0f, space + smallRadius)
            drawSemiCircle(
                Offset(0f, space + smallRadius + smallRadius),
                smallRadius
            )
        }
        lineTo(0f, size.height / 2 - bigRadius)
        drawSemiCircle(
            Offset(0f, size.height / 2), bigRadius
        )
        if (canDraw5Circles) {
            lineTo(0f, size.height - 2*smallRadius - space)
            drawSemiCircle(Offset(0f,(size.height - 2*smallRadius - space)), smallRadius)
        }
        if (canDraw3Circles) {
            lineTo(0f, size.height)
            drawQuadrant(Offset(0f , size.height), smallRadius, -90f, 90f)
        }
        lineTo(size.width, size.height)

        // right border
        if (canDraw3Circles) {
            drawQuadrant(Offset(size.width, size.height), smallRadius, 180f, 90f)
        }
        if(canDraw5Circles) {
            lineTo(size.width, size.height - 2*smallRadius - space)
            drawSemiCircle(
                Offset(size.width,(size.height - 2*smallRadius - space)),
                smallRadius,
                90f
            )
        }
        lineTo(size.width, size.height/2 +bigRadius)
        drawSemiCircle(Offset(size.width,(size.height/2)), bigRadius, 90f)
        if (canDraw5Circles) {
            lineTo(size.width, space + 3*smallRadius)
            drawSemiCircle(
                Offset(size.width, space + 2*smallRadius),
                smallRadius,
                90f
            )
        }
        if (canDraw3Circles) {
            lineTo(size.width, smallRadius)
            drawQuadrant(Offset(size.width, 0f), smallRadius, 90f, 90f)
        }
        lineTo(size.width, 0f)

        close()
    }
}

private fun Path.drawSemiCircle(
    center: Offset,
    radius: Float,
    startAngle: Float = -90.0f,
) {
    arcTo(
        rect = Rect(center, radius),
        startAngleDegrees = startAngle,
        sweepAngleDegrees = 180f,
        forceMoveTo = false
    )
}

private fun Path.drawQuadrant(
    center: Offset,
    radius: Float,
    startAngle: Float,
    sweepAngle: Float
) {
    arcTo(
        rect = Rect(center, radius),
        startAngleDegrees = startAngle,
        sweepAngleDegrees = sweepAngle,
        forceMoveTo = false
    )
}
