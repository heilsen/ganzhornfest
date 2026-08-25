package de.heilsen.ganzhornfest.map

import android.content.Context
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapsInitializer.Renderer
import timber.log.Timber

@Suppress("DEPRECATION")
fun initializeMapsRenderer(context: Context) {
    MapsInitializer.initialize(context, Renderer.LEGACY) { renderer ->
        Timber.d("Maps renderer: $renderer")
    }
}
