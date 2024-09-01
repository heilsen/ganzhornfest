package de.heilsen.ganzhornfest.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@PreviewLightDark
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapModel: MapModel = MapModel.Loading,
    onEvent: (MapEvent) -> Unit = {},
    onMarkerSelected: (String, MarkerUiType) -> Unit = { _, _ -> }
) {
    val center = LatLng(49.191669847836216, 9.222756134219502)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(center, 18f)
    }
    Box {
        // Google Maps wants to also be able show Points on the Bound in the center, therefore the area must be very small around the center
        val ganzhornfestArea = LatLngBounds(
            LatLng(49.18859845006538, 9.219649084689227), // SW:Bahnhof
            LatLng(49.19498798073398, 9.225975728423913) // NE:Frauenkirche
        )
        GoogleMap(
            modifier = Modifier.align(Alignment.TopStart),
            cameraPositionState = cameraPositionState,
            contentPadding = PaddingValues(bottom = 70.dp),
            properties = MapProperties(
                mapType = MapType.HYBRID,
                minZoomPreference = 16f,
                latLngBoundsForCameraTarget = ganzhornfestArea
            )
        ) {
            if (mapModel is MapModel.Data) {
                for (marker in mapModel.markers) {
                    val markerState = rememberMarkerState(position = marker.latLng)
                    Marker(
                        state = markerState,
                        title = marker.title,
                        icon = marker.icon,
                        onInfoWindowClick = {
                            onMarkerSelected(
                                marker.title,
                                marker.markerUiType
                            )
                        },
                        onInfoWindowClose = { }
                    )
                }
            }
        }
        Legend(
            modifier = Modifier
                .padding(4.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun Legend(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column(Modifier.padding(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFFFF08F2))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Bühne", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFF9C2CF3))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Stand", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFFF98A03))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Kinder", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFF0092F1))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "WC", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFFFF0827))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Erste Hilfe", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFF3535F3))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Bus", style = MaterialTheme.typography.labelSmall)
            }
        }

    }

}