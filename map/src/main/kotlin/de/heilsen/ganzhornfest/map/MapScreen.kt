package de.heilsen.ganzhornfest.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@PreviewLightDark
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapModel: MapModel = MapModel.Loading(),
    onMarkerSelected: (String, MarkerUiType) -> Unit = { _, _ -> },
    onEvent: (MapEvent) -> Unit = {},
    showPinEditorToggle: Boolean = false,
) {
    when (mapModel) {
        is MapModel.Data -> {
            var editorOpen by remember { mutableStateOf(false) }
            var selectedPoiId by remember { mutableStateOf<Long?>(null) }
            var selectedCoordinateId by remember { mutableStateOf<Long?>(null) }
            val selectedPin =
                mapModel.pins.firstOrNull { pin ->
                    pin.poiId == selectedPoiId && pin.coordinateId == selectedCoordinateId
                } ?: mapModel.pins.firstOrNull()
            val pinEditor =
                if (editorOpen) {
                    PinEditorModel(pins = mapModel.pins, selected = selectedPin)
                } else {
                    null
                }
            val center = LatLng(49.191669847836216, 9.222756134219502)
            val cameraPositionState =
                rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(center, 18f)
                }
            LaunchedEffect(pinEditor?.selected?.poiId, pinEditor?.selected?.coordinateId) {
                val target = pinEditor?.selected?.latLng ?: return@LaunchedEffect
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(target, 19f))
            }
            LaunchedEffect(mapModel.markers) {
                if (mapModel.isFullscreen || mapModel.markers.isEmpty() || pinEditor != null) return@LaunchedEffect
                val update =
                    if (mapModel.markers.size == 1) {
                        CameraUpdateFactory.newLatLngZoom(mapModel.markers.first().latLng, 18f)
                    } else {
                        val bounds = LatLngBounds.Builder()
                        mapModel.markers.forEach { bounds.include(it.latLng) }
                        CameraUpdateFactory.newLatLngBounds(bounds.build(), 100)
                    }
                cameraPositionState.move(update)
            }
            Column(modifier = modifier.fillMaxSize()) {
                if (showPinEditorToggle && pinEditor == null) {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(top = if (mapModel.isFullscreen) 72.dp else 0.dp)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(Modifier.weight(1f))
                        Button(onClick = { editorOpen = true }) {
                            Text("Standorte korrigieren")
                        }
                    }
                }
                Box(Modifier.weight(1f).fillMaxWidth()) {
                    val ganzhornfestArea =
                        LatLngBounds(
                            LatLng(49.18859845006538, 9.219649084689227),
                            LatLng(49.19498798073398, 9.225975728423913),
                        )
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState,
                        contentPadding =
                            if (mapModel.isFullscreen) {
                                PaddingValues(top = 72.dp, bottom = 8.dp)
                            } else {
                                PaddingValues(0.dp)
                            },
                        properties =
                            MapProperties(
                                mapType = MapType.HYBRID,
                                minZoomPreference = 16f,
                                latLngBoundsForCameraTarget = ganzhornfestArea,
                            ),
                    ) {
                        for (marker in mapModel.markers) {
                            val markerState = rememberMarkerState(position = marker.latLng)
                            Marker(
                                state = markerState,
                                title = marker.title,
                                icon = marker.icon,
                                onClick = {
                                    if (pinEditor != null) {
                                        val pin =
                                            mapModel.pins.firstOrNull { candidate ->
                                                candidate.latLng == marker.latLng
                                            }
                                        if (pin != null) {
                                            selectedPoiId = pin.poiId
                                            selectedCoordinateId = pin.coordinateId
                                        }
                                        true
                                    } else {
                                        false
                                    }
                                },
                                onInfoWindowClick = {
                                    if (pinEditor == null) {
                                        onMarkerSelected(
                                            marker.title,
                                            marker.markerUiType,
                                        )
                                    }
                                },
                                onInfoWindowClose = { },
                            )
                            if (mapModel.showWindowInfo) markerState.showInfoWindow()
                        }
                    }
                    if (pinEditor != null) {
                        Crosshair(modifier = Modifier.align(Alignment.Center))
                    }
                    if (mapModel.showLegend && pinEditor == null) {
                        Legend(
                            modifier =
                                Modifier
                                    .padding(4.dp)
                                    .align(Alignment.BottomStart),
                        )
                    }
                }
                if (pinEditor != null) {
                    PinEditorPanel(
                        pinEditor = pinEditor,
                        previewLatLng = cameraPositionState.position.target,
                        onSelectPin = { pin ->
                            selectedPoiId = pin.poiId
                            selectedCoordinateId = pin.coordinateId
                        },
                        onApply = { coordinateId ->
                            val target = cameraPositionState.position.target
                            onEvent(
                                MapEvent.ApplyLatLng(
                                    coordinateId,
                                    target.latitude,
                                    target.longitude,
                                ),
                            )
                        },
                        onClose = { editorOpen = false },
                    )
                }
            }
        }

        is MapModel.Loading -> {
            // TODO("implement loading")
        }
    }
}

@Composable
private fun Crosshair(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            Modifier
                .width(24.dp)
                .height(2.dp)
                .background(Color.White),
        )
        Box(
            Modifier
                .width(2.dp)
                .height(24.dp)
                .background(Color.White),
        )
    }
}

@Composable
fun Legend(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(Modifier.padding(4.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFFFF08F2)),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Veranstaltungsort", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFF9C2CF3)),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Stand", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFF00C853)),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Attraktion", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFF0092F1)),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "WC", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFFFF0827)),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Erste Hilfe", style = MaterialTheme.typography.labelSmall)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(8.dp)
                        .background(Color(0xFF3535F3)),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Bus", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}
