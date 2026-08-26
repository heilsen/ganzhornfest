package de.heilsen.ganzhornfest.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.util.concurrent.ConcurrentHashMap

object PinBitmapFactory {
    private data class Key(
        val type: MarkerUiType,
        val emphasis: PinEmphasis,
    )

    private val cache = ConcurrentHashMap<Key, BitmapDescriptor>()

    fun icon(
        type: MarkerUiType,
        emphasis: PinEmphasis,
    ): BitmapDescriptor =
        cache.getOrPut(Key(type, emphasis)) {
            BitmapDescriptorFactory.fromBitmap(draw(type, emphasis))
        }

    internal fun hueFor(type: MarkerUiType): Float =
        when (type) {
            MarkerUiType.CLUB -> BitmapDescriptorFactory.HUE_VIOLET
            MarkerUiType.EVENT_LOCATION -> BitmapDescriptorFactory.HUE_MAGENTA
            MarkerUiType.PLAYGROUND -> BitmapDescriptorFactory.HUE_ORANGE
            MarkerUiType.ATTRACTION -> BitmapDescriptorFactory.HUE_GREEN
            MarkerUiType.WC -> BitmapDescriptorFactory.HUE_AZURE
            MarkerUiType.FIRST_AID -> BitmapDescriptorFactory.HUE_RED
            MarkerUiType.BUS_STOP -> BitmapDescriptorFactory.HUE_BLUE
        }

    private fun draw(
        type: MarkerUiType,
        emphasis: PinEmphasis,
    ): Bitmap {
        val actionable = type.isActionable()
        val selected = emphasis == PinEmphasis.Highlighted
        val scale =
            when {
                selected -> 1.15f
                actionable -> 1f
                else -> 0.72f
            }
        val saturation =
            when {
                !actionable && emphasis != PinEmphasis.Highlighted -> 0.45f
                else -> 1f
            }
        val size = (48f * scale).toInt().coerceAtLeast(28)
        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val cx = size / 2f
        val cy = size * 0.38f
        val radius = size * 0.28f
        val fill = Color.HSVToColor(floatArrayOf(hueFor(type), saturation, 1f))
        if (selected) {
            val halo =
                Paint(Paint.ANTI_ALIAS_FLAG).apply {
                    color = Color.WHITE
                    style = Paint.Style.FILL
                }
            canvas.drawCircle(cx, cy, radius * 1.35f, halo)
        }
        val paint =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = fill
                style = Paint.Style.FILL
            }
        val path =
            Path().apply {
                addCircle(cx, cy, radius, Path.Direction.CW)
                moveTo(cx - radius * 0.72f, cy + radius * 0.5f)
                lineTo(cx, size * 0.95f)
                lineTo(cx + radius * 0.72f, cy + radius * 0.5f)
                close()
            }
        canvas.drawPath(path, paint)
        val hole =
            Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
                style = Paint.Style.FILL
            }
        canvas.drawCircle(cx, cy, radius * 0.32f, hole)
        return bitmap
    }
}
