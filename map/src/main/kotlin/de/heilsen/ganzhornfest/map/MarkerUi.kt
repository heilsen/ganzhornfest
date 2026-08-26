package de.heilsen.ganzhornfest.map

import com.google.android.gms.maps.model.LatLng

data class MarkerUi(
    val title: String,
    val latLng: LatLng,
    val markerUiType: MarkerUiType,
)
