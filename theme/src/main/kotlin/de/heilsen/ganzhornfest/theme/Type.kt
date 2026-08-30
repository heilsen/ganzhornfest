package de.heilsen.ganzhornfest.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

// Both families are bundled TTFs, not downloaded from GMS. A downloadable font with no
// bundled fallback silently degrades to the platform sans on a non-GMS device or a cold
// offline launch, and Source Sans 3 was already bundled, so querying GMS for it too just
// meant three redundant network requests for a font already in the APK.
val GanzhornfestSerif =
    FontFamily(
        Font(R.font.fraunces_regular, FontWeight.Normal),
        Font(R.font.fraunces_medium, FontWeight.Medium),
        Font(R.font.fraunces_semibold, FontWeight.SemiBold),
    )

val GanzhornfestSans =
    FontFamily(
        Font(R.font.source_sans_3_regular, FontWeight.Normal),
        Font(R.font.source_sans_3_medium, FontWeight.Medium),
        Font(R.font.source_sans_3_semibold, FontWeight.SemiBold),
    )

fun ganzhornfestTypography(): Typography {
    val display = GanzhornfestSerif
    val body = GanzhornfestSans
    val base = Typography()
    return base.copy(
        displayLarge = base.displayLarge.copy(fontFamily = display, fontWeight = FontWeight.Medium),
        displayMedium = base.displayMedium.copy(fontFamily = display, fontWeight = FontWeight.Medium),
        displaySmall = base.displaySmall.copy(fontFamily = display, fontWeight = FontWeight.Medium),
        headlineLarge = base.headlineLarge.copy(fontFamily = body, fontWeight = FontWeight.SemiBold),
        headlineMedium = base.headlineMedium.copy(fontFamily = body, fontWeight = FontWeight.SemiBold),
        headlineSmall = base.headlineSmall.copy(fontFamily = body, fontWeight = FontWeight.SemiBold),
        titleLarge = base.titleLarge.copy(fontFamily = body, fontWeight = FontWeight.SemiBold),
        titleMedium = base.titleMedium.copy(fontFamily = body, fontWeight = FontWeight.SemiBold),
        titleSmall = base.titleSmall.copy(fontFamily = body, fontWeight = FontWeight.SemiBold),
        bodyLarge = base.bodyLarge.copy(fontFamily = body, fontWeight = FontWeight.Medium),
        bodyMedium = base.bodyMedium.copy(fontFamily = body, fontWeight = FontWeight.Medium),
        bodySmall = base.bodySmall.copy(fontFamily = body, fontWeight = FontWeight.Medium),
        labelLarge = base.labelLarge.copy(fontFamily = body, fontWeight = FontWeight.SemiBold),
        labelMedium = base.labelMedium.copy(fontFamily = body, fontWeight = FontWeight.Medium),
        labelSmall = base.labelSmall.copy(fontFamily = body, fontWeight = FontWeight.Medium),
    )
}
