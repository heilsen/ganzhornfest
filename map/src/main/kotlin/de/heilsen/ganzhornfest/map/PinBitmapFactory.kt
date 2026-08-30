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
        sizePx: Int,
    ): BitmapDescriptor =
        cache.getOrPut(CacheKey(type, sizePx)) {
            BitmapDescriptorFactory.fromBitmap(dotBitmap(colorFor(type), sizePx))
        }

    internal fun hueFor(type: MarkerUiType): Float =
        when (type) {
            MarkerUiType.CLUB -> 32f // #FF8800
            MarkerUiType.EVENT_LOCATION -> 275f // #9500FF
            MarkerUiType.PLAYGROUND -> 330f // #FF0080
            MarkerUiType.ATTRACTION -> 55f // #FFEA00
            MarkerUiType.WC -> 180f // #00FFFF
            MarkerUiType.FIRST_AID -> 0f // #FF0000
            MarkerUiType.BUS_STOP -> 220f // #0055FF
        }

    private fun colorFor(type: MarkerUiType): Int = AndroidColor.HSVToColor(floatArrayOf(hueFor(type), 1f, 1f))

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
        val sizePx: Int,
    )
}
