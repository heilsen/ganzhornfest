package de.heilsen.ganzhornfest.map

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng

data class MarkerUi(
    val title: String,
    val latLng: LatLng,
    val markerUiType: MarkerUiType,
) {

    val icon = BitmapDescriptorFactory.defaultMarker(convertMarkerUiTypeToMarkerHue(markerUiType))

    private fun convertMarkerUiTypeToMarkerHue(markerUiType: MarkerUiType) = when (markerUiType) {
        MarkerUiType.CLUB -> BitmapDescriptorFactory.HUE_VIOLET
        MarkerUiType.STAGE -> BitmapDescriptorFactory.HUE_MAGENTA
        MarkerUiType.PLAYGROUND -> BitmapDescriptorFactory.HUE_ORANGE
        MarkerUiType.WC -> BitmapDescriptorFactory.HUE_AZURE
        MarkerUiType.FIRST_AID -> BitmapDescriptorFactory.HUE_RED
        MarkerUiType.BUS_STOP -> BitmapDescriptorFactory.HUE_BLUE
    }
}