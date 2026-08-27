package de.heilsen.ganzhornfest.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.font.Font as ResourceFont
import androidx.compose.ui.text.googlefonts.Font as GoogleFontFont

private val googleFontProvider =
    GoogleFont.Provider(
        providerAuthority = "com.google.android.gms.fonts",
        providerPackage = "com.google.android.gms",
        certificates = R.array.com_google_android_gms_fonts_certs,
    )

val GanzhornfestSerif =
    FontFamily(
        GoogleFontFont(
            googleFont = GoogleFont("Fraunces"),
            fontProvider = googleFontProvider,
            weight = FontWeight.Normal,
        ),
        GoogleFontFont(
            googleFont = GoogleFont("Fraunces"),
            fontProvider = googleFontProvider,
            weight = FontWeight.Medium,
        ),
        GoogleFontFont(
            googleFont = GoogleFont("Fraunces"),
            fontProvider = googleFontProvider,
            weight = FontWeight.SemiBold,
        ),
    )

val GanzhornfestSans =
    FontFamily(
        GoogleFontFont(
            googleFont = GoogleFont("Source Sans 3"),
            fontProvider = googleFontProvider,
            weight = FontWeight.Normal,
        ),
        ResourceFont(R.font.source_sans_3_regular, FontWeight.Normal),
        GoogleFontFont(
            googleFont = GoogleFont("Source Sans 3"),
            fontProvider = googleFontProvider,
            weight = FontWeight.Medium,
        ),
        ResourceFont(R.font.source_sans_3_medium, FontWeight.Medium),
        GoogleFontFont(
            googleFont = GoogleFont("Source Sans 3"),
            fontProvider = googleFontProvider,
            weight = FontWeight.SemiBold,
        ),
        ResourceFont(R.font.source_sans_3_semibold, FontWeight.SemiBold),
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
