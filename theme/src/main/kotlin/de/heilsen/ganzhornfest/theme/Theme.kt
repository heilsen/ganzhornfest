package de.heilsen.ganzhornfest.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

private fun shapes() =
    Shapes(
        extraSmall = RoundedCornerShape(4.0.dp),
        small = RoundedCornerShape(8.0.dp),
        medium = RoundedCornerShape(8.0.dp),
        large = RoundedCornerShape(16.0.dp),
        extraLarge = RoundedCornerShape(28.0.dp),
    )

@Composable
fun GanzhornfestTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = remember { ganzhornfestTypography() },
        shapes = shapes(),
        content = content,
    )
}
