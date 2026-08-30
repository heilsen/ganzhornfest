package de.heilsen.ganzhornfest.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.util.concurrent.ConcurrentHashMap
import android.graphics.Color as AndroidColor

object PinBitmapFactory {
    private val cache = ConcurrentHashMap<CacheKey, BitmapDescriptor>()

    // The stock defaultMarker teardrop is about 27x43dp and anchors at its tip, so its tap
    // target covers 10x17m of ground at zoom 18 and sits entirely north of the coordinate.
    // Stands are a median 11m apart, so a pin swallowed taps meant for its neighbour.
    fun icon(
        type: MarkerUiType,
        emphasis: PinEmphasis,
        sizePx: Int,
    ): BitmapDescriptor =
        cache.getOrPut(CacheKey(type, emphasis, sizePx)) {
            BitmapDescriptorFactory.fromBitmap(dotBitmap(colorFor(type, emphasis), sizePx))
        }

    // The legend swatch has to match the pin it labels, in whatever emphasis that pin
    // currently has. Both derive their colour here, so they cannot drift apart.
    internal fun swatchColor(
        type: MarkerUiType,
        emphasis: PinEmphasis,
    ): Int = colorFor(type, emphasis)

    private fun colorFor(
        type: MarkerUiType,
        emphasis: PinEmphasis,
    ): Int = AndroidColor.HSVToColor(hsvFor(type, emphasis))

    // Emphasis scales whatever the family set, so one rule covers all seven types. The floors
    // on Highlighted matter more than the multipliers. A slate WC has to become a real blue
    // when you ask for toilets, and a dark slate bus stop has to get brighter, not just bigger.
    internal fun hsvFor(
        type: MarkerUiType,
        emphasis: PinEmphasis,
    ): FloatArray {
        val (hue, saturation, value) = baseHsv(type)
        return when (emphasis) {
            PinEmphasis.Highlighted ->
                floatArrayOf(
                    hue,
                    (saturation * 1.35f).coerceIn(0.75f, 1f),
                    (value * 1.10f).coerceIn(0.80f, 0.98f),
                )
            PinEmphasis.Default -> floatArrayOf(hue, saturation, value)
            PinEmphasis.Dimmed -> floatArrayOf(hue, saturation * 0.35f, value * 0.75f)
        }
    }

    // Hue is the category. Saturation and value are the family.
    private fun baseHsv(type: MarkerUiType): Triple<Float, Float, Float> =
        when (type) {
            // Content, the things you came for. Four hues, 65 to 145 degrees apart. The green
            // arc is left empty. A green dot disappears into grass and tree canopy on HYBRID.
            // S 0.78 keeps a chroma step for Highlighted while still reading clean, not brown.
            MarkerUiType.CLUB -> Triple(28f, 0.78f, 0.90f)
            MarkerUiType.PLAYGROUND -> Triple(175f, 0.78f, 0.90f)
            MarkerUiType.ATTRACTION -> Triple(255f, 0.78f, 0.90f)
            MarkerUiType.EVENT_LOCATION -> Triple(325f, 0.78f, 0.90f)
            // Safety. Hotter than content at rest so it reads without being asked for.
            MarkerUiType.FIRST_AID -> Triple(0f, 0.92f, 0.95f)
            // Service, the things you need occasionally. One hue, separated by lightness. Two
            // blues 30 degrees apart is what made the old azure and blue pins indistinguishable.
            MarkerUiType.WC -> Triple(210f, 0.30f, 0.90f)
            MarkerUiType.BUS_STOP -> Triple(210f, 0.35f, 0.50f)
        }

    // The white ring keeps the dot readable on the HYBRID aerial imagery.
    private fun dotBitmap(
        color: Int,
        sizePx: Int,
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
        val centre = sizePx / 2f
        val ring = sizePx / 8f
        val radius = centre - ring / 2f
        val canvas = Canvas(bitmap)
        canvas.drawCircle(
            centre,
            centre,
            radius,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color },
        )
        canvas.drawCircle(
            centre,
            centre,
            radius,
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                this.color = AndroidColor.WHITE
                style = Paint.Style.STROKE
                strokeWidth = ring
            },
        )
        return bitmap
    }

    private data class CacheKey(
        val type: MarkerUiType,
        val emphasis: PinEmphasis,
        val sizePx: Int,
    )
}
