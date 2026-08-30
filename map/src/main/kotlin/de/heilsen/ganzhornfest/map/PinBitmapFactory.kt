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

    // Hue names the category. Saturation and value keep the seven pins apart under
    // protanopia, deuteranopia and tritanopia, not only normal vision. The set was picked by
    // simulating each dichromacy and maximising the smallest CIEDE2000 gap between any two
    // categories. Worst pair sits at dE 24, against dE 8 for the previous scheme. The green
    // arc 85 to 160 stays empty because a green pin vanishes into canopy on the HYBRID basemap.
    private fun baseHsv(type: MarkerUiType): Triple<Float, Float, Float> =
        when (type) {
            // Bright yellow, the loudest pin, on the stages since that is what people head for.
            MarkerUiType.EVENT_LOCATION -> Triple(60f, 0.75f, 1.00f)
            // Warm orange. Stand is the wallpaper so it stays calm. Default S 0.75 leaves a
            // step for Highlighted to lift it.
            MarkerUiType.CLUB -> Triple(20f, 0.75f, 0.85f)
            MarkerUiType.ATTRACTION -> Triple(260f, 0.60f, 1.00f)
            MarkerUiType.PLAYGROUND -> Triple(180f, 0.45f, 0.40f)
            // Pale and calm at rest. Wakes to a full blue when a WC category is filtered, via
            // the Highlighted saturation floor.
            MarkerUiType.WC -> Triple(200f, 0.30f, 1.00f)
            // Two near-black anchors. They separate from every lighter pin by luminance, the
            // one channel all three dichromacies keep.
            MarkerUiType.FIRST_AID -> Triple(0f, 0.90f, 0.40f)
            MarkerUiType.BUS_STOP -> Triple(250f, 0.90f, 0.40f)
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
