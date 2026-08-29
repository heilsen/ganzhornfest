package de.heilsen.ganzhornfest.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

internal val Wine = Color(0xFF7A1F2B)
internal val WineContainer = Color(0xFFF3D5D4)
internal val WineDark = Color(0xFF3F0F16)
internal val CreamWhite = Color(0xFFFFF8F0)
internal val Gold = Color(0xFFC4A35A)
internal val GoldContainer = Color(0xFFF3E6C4)
internal val Ink = Color(0xFF12100E)
internal val InkMuted = Color(0xFF3A332E)
internal val Cream = Color(0xFFF6EFE4)
internal val CreamVariant = Color(0xFFEDE3D4)
internal val Paper = Color(0xFFFFFBF5)
internal val Stone = Color(0xFF8A7F74)
internal val ErrorRed = Color(0xFFB3261E)
internal val Forest = Color(0xFF5C7A4A)

internal val NightInk = Color(0xFF1C1410)
internal val NightSurface = Color(0xFF2A1F1A)
internal val NightWine = Color(0xFFE8B4B8)
internal val NightWineContainer = Color(0xFF5C1820)
internal val NightGold = Color(0xFFE0C47A)
internal val NightCream = Color(0xFFF6EFE4)

internal val LightColorScheme =
    lightColorScheme(
        primary = Wine,
        onPrimary = CreamWhite,
        primaryContainer = WineContainer,
        onPrimaryContainer = WineDark,
        secondary = Gold,
        onSecondary = Ink,
        secondaryContainer = GoldContainer,
        onSecondaryContainer = Ink,
        tertiary = Forest,
        onTertiary = CreamWhite,
        tertiaryContainer = Color(0xFFD5E3C8),
        onTertiaryContainer = Color(0xFF1A2A14),
        error = ErrorRed,
        onError = CreamWhite,
        errorContainer = Color(0xFFF9DEDC),
        onErrorContainer = Color(0xFF410E0B),
        background = Cream,
        onBackground = Ink,
        surface = Cream,
        onSurface = Ink,
        surfaceVariant = CreamVariant,
        onSurfaceVariant = InkMuted,
        outline = Stone,
        outlineVariant = Color(0xFFD4C8B8),
        inverseSurface = Ink,
        inverseOnSurface = Cream,
        inversePrimary = NightWine,
        surfaceTint = Wine,
        scrim = Color(0xFF000000),
        surfaceBright = Paper,
        surfaceDim = Color(0xFFE8DCC8),
        surfaceContainerLowest = Paper,
        surfaceContainerLow = Color(0xFFFBF4EA),
        surfaceContainer = Color(0xFFF3EADC),
        surfaceContainerHigh = Color(0xFFEBE0CE),
        surfaceContainerHighest = Color(0xFFE3D6C0),
    )

internal val DarkColorScheme =
    darkColorScheme(
        primary = NightWine,
        onPrimary = WineDark,
        primaryContainer = NightWineContainer,
        onPrimaryContainer = NightWine,
        secondary = NightGold,
        onSecondary = Ink,
        secondaryContainer = Color(0xFF4A3C1C),
        onSecondaryContainer = NightCream,
        tertiary = Color(0xFFB5C9A4),
        onTertiary = Color(0xFF1A2A14),
        tertiaryContainer = Color(0xFF3D4F32),
        onTertiaryContainer = Color(0xFFD5E3C8),
        error = Color(0xFFF2B8B5),
        onError = Color(0xFF601410),
        errorContainer = Color(0xFF8C1D18),
        onErrorContainer = Color(0xFFF9DEDC),
        background = NightInk,
        onBackground = NightCream,
        surface = NightInk,
        onSurface = NightCream,
        surfaceVariant = NightSurface,
        onSurfaceVariant = NightCream,
        outline = Color(0xFFA89A8C),
        outlineVariant = Color(0xFF4A3F38),
        inverseSurface = Cream,
        inverseOnSurface = Ink,
        inversePrimary = Wine,
        surfaceTint = NightWine,
        scrim = Color(0xFF000000),
        surfaceBright = Color(0xFF3D2E27),
        surfaceDim = NightInk,
        surfaceContainerLowest = Color(0xFF16100D),
        surfaceContainerLow = Color(0xFF1F1712),
        surfaceContainer = NightSurface,
        surfaceContainerHigh = Color(0xFF332620),
        surfaceContainerHighest = Color(0xFF3D2E27),
    )
