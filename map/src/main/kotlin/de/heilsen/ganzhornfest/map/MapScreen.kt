package de.heilsen.ganzhornfest.map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
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
import com.google.maps.android.compose.rememberUpdatedMarkerState
import de.heilsen.ganzhornfest.theme.isSidePanelLayout
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.toPersistentSet

// Wide enough for the Standort dropdown and the apply row without crowding the map.
private val EDITOR_PANE_WIDTH = 360.dp

// Room for the search bar that floats over the map, so Maps centres the camera below it.
private val SEARCH_BAR_CAMERA_INSET = 72.dp

// Small enough that the tap target stays close to the dot you see. A wider pin covers
// neighbouring stands, which sit a median 11m apart.
private val PIN_DIAMETER = 16.dp

// Highlighted pins are the few you asked for and zIndex 2 already wins any overlap, so the
// crowding that sizes PIN_DIAMETER does not apply to them.
private val PIN_DIAMETER_HIGHLIGHTED = 22.dp
private val PIN_DIAMETER_DIMMED = 14.dp

// newLatLngBounds throws when this padding leaves no room in the view, which happens whenever
// the GoogleMap view is still 0-sized (first frame, mid rotation). The padding is clamped to the
// live view size below, so this is only the preferred value when the map is big enough for it.
private const val HIGHLIGHT_BOUNDS_PADDING_PX = 160

@PreviewLightDark
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    mapModel: MapModel = MapModel.Loading(),
    highlightedTitles: Set<String>? = null,
    onMarkerSelected: (MarkerUi) -> Unit = {},
    onEvent: (MapEvent) -> Unit = {},
    showPinEditorToggle: Boolean = false,
    mapBottomPadding: Dp = 8.dp,
    // Hoisted so the host can hide chrome that would otherwise sit over the editor panel.
    pinEditorOpen: Boolean = false,
    onPinEditorOpenChange: (Boolean) -> Unit = {},
) {
    when (mapModel) {
        is MapModel.Data -> {
            var selectedPoiId by remember { mutableStateOf<Long?>(null) }
            var selectedCoordinateId by remember { mutableStateOf<Long?>(null) }
            val selectedPin =
                mapModel.pins.firstOrNull { pin ->
                    pin.poiId == selectedPoiId && pin.coordinateId == selectedCoordinateId
                } ?: mapModel.pins.firstOrNull()
            val pinEditor =
                if (pinEditorOpen && showPinEditorToggle) {
                    PinEditorModel(pins = mapModel.pins, selected = selectedPin)
                } else {
                    null
                }
            LaunchedEffect(showPinEditorToggle) {
                if (!showPinEditorToggle) {
                    onPinEditorOpenChange(false)
                }
            }
            val center = LatLng(49.191669847836216, 9.222756134219502)
            val cameraPositionState =
                rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(center, 18f)
                }
            // Zero until the GoogleMap view below reports its first layout. newLatLngBounds
            // needs this to clamp its padding, so the effect below waits for it too.
            var mapSizePx by remember { mutableStateOf(IntSize.Zero) }
            LaunchedEffect(pinEditor?.selected?.poiId, pinEditor?.selected?.coordinateId) {
                val target = pinEditor?.selected?.latLng ?: return@LaunchedEffect
                cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(target, 19f))
            }
            LaunchedEffect(highlightedTitles, mapModel.markers, mapSizePx) {
                val titles = highlightedTitles ?: return@LaunchedEffect
                if (titles.isEmpty() || pinEditor != null) return@LaunchedEffect
                if (mapSizePx.width == 0 || mapSizePx.height == 0) return@LaunchedEffect
                val targets = mapModel.markers.filter { it.title in titles }
                if (targets.isEmpty()) return@LaunchedEffect
                val update =
                    if (targets.size == 1) {
                        CameraUpdateFactory.newLatLngZoom(targets.first().latLng, 18f)
                    } else {
                        val bounds = LatLngBounds.Builder()
                        targets.forEach { bounds.include(it.latLng) }
                        // Padding must leave at least a sliver of view on the smaller axis or
                        // newLatLngBounds throws, so clamp it to the view size we actually have.
                        val padding =
                            (minOf(mapSizePx.width, mapSizePx.height) / 2 - 1)
                                .coerceIn(0, HIGHLIGHT_BOUNDS_PADDING_PX)
                        CameraUpdateFactory.newLatLngBounds(bounds.build(), padding)
                    }
                cameraPositionState.animate(update)
            }
            // The editor panel needs about 260dp, which a short window cannot spare under the
            // map. A wide window has room beside it either way.
            val sidePanel = isSidePanelLayout()

            MapEditorLayout(
                sideBySide = sidePanel,
                showPanel = pinEditor != null,
                modifier = modifier,
                panel = { panelModifier ->
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
                            onClose = { onPinEditorOpenChange(false) },
                            modifier = panelModifier,
                        )
                    }
                },
            ) { mapModifier ->
                Box(mapModifier) {
                    // The east edge runs well past the stands to keep the ZOB (Ballei) bus stop
                    // reachable. It sits about 100m east of the festival core at lng 9.227315, so
                    // a tighter bound clamped the camera short of it and clipped the pin at every
                    // zoom except the two most zoomed out. This value is the stop plus a margin.
                    val ganzhornfestArea =
                        LatLngBounds(
                            LatLng(49.18859845006538, 9.219649084689227),
                            LatLng(49.19498798073398, 9.228),
                        )
                    // The only marker whose info window is open, identified by its LatLng since
                    // a club with two stands, such as DLRG, has two markers under one title.
                    var openInfoMarker by remember { mutableStateOf<LatLng?>(null) }
                    LaunchedEffect(highlightedTitles, mapModel.markers) {
                        val titles = highlightedTitles
                        openInfoMarker =
                            if (titles == null) {
                                null
                            } else {
                                // Only auto-open when exactly one marker matches. Several markers
                                // sharing a highlighted title (an offer's clubs, a club with two
                                // stands) stay unopened instead of racing for the one window.
                                mapModel.markers.singleOrNull { it.title in titles }?.latLng
                            }
                    }
                    val (highlightedPx, defaultPx, dimmedPx) =
                        with(LocalDensity.current) {
                            Triple(
                                PIN_DIAMETER_HIGHLIGHTED.roundToPx(),
                                PIN_DIAMETER.roundToPx(),
                                PIN_DIAMETER_DIMMED.roundToPx(),
                            )
                        }
                    GoogleMap(
                        modifier = Modifier.fillMaxSize().onSizeChanged { mapSizePx = it },
                        cameraPositionState = cameraPositionState,
                        // Maps centres the camera target in the non-padded region, but the
                        // crosshair is drawn at the geometric centre. Any padding would offset
                        // the applied coordinate from what the crosshair points at.
                        contentPadding =
                            if (pinEditor != null) {
                                PaddingValues(0.dp)
                            } else {
                                PaddingValues(
                                    top = topInsetHeight() + SEARCH_BAR_CAMERA_INSET,
                                    bottom = mapBottomPadding,
                                )
                            },
                        properties =
                            MapProperties(
                                mapType = MapType.HYBRID,
                                minZoomPreference = 16f,
                                latLngBoundsForCameraTarget = ganzhornfestArea,
                            ),
                        onMapClick = { openInfoMarker = null },
                    ) {
                        for (marker in mapModel.markers) {
                            val markerState = rememberUpdatedMarkerState(position = marker.latLng)
                            val emphasis = resolvePinEmphasis(marker.title, highlightedTitles)
                            val pinSizePx =
                                when (emphasis) {
                                    PinEmphasis.Highlighted -> highlightedPx
                                    PinEmphasis.Default -> defaultPx
                                    PinEmphasis.Dimmed -> dimmedPx
                                }
                            LaunchedEffect(openInfoMarker, marker.latLng) {
                                if (openInfoMarker == marker.latLng) {
                                    markerState.showInfoWindow()
                                } else {
                                    markerState.hideInfoWindow()
                                }
                            }
                            Marker(
                                state = markerState,
                                title = marker.title,
                                icon = PinBitmapFactory.icon(marker.markerUiType, emphasis, pinSizePx),
                                anchor = Offset(0.5f, 0.5f),
                                alpha = if (emphasis == PinEmphasis.Dimmed) 0.5f else 1f,
                                zIndex =
                                    when (emphasis) {
                                        PinEmphasis.Highlighted -> 2f
                                        PinEmphasis.Default -> 1f
                                        PinEmphasis.Dimmed -> 0f
                                    },
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
                                    } else {
                                        // Dimmed pins stay tappable. Dimming says "not part of
                                        // what you asked for", not "unavailable", and zIndex
                                        // already lets a highlighted pin win an overlap.
                                        openInfoMarker = marker.latLng
                                    }
                                    true
                                },
                                onInfoWindowClick = {
                                    if (pinEditor == null) {
                                        onMarkerSelected(marker)
                                    }
                                },
                                onInfoWindowClose = {
                                    if (openInfoMarker == marker.latLng) {
                                        openInfoMarker = null
                                    }
                                },
                            )
                        }
                    }
                    if (pinEditor != null) {
                        Crosshair(modifier = Modifier.align(Alignment.Center))
                    }
                    // Overlaid rather than stacked above the map. In a Column it cost the map
                    // its own height, which leaves almost nothing on a compact height window.
                    if (showPinEditorToggle && pinEditor == null) {
                        Button(
                            onClick = { onPinEditorOpenChange(true) },
                            modifier =
                                Modifier
                                    .align(Alignment.TopEnd)
                                    .windowInsetsPadding(topInset())
                                    .padding(top = SEARCH_BAR_CAMERA_INSET)
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                        ) {
                            Text("Standorte korrigieren")
                        }
                    }
                    if (mapModel.showLegend && pinEditor == null) {
                        val highlightedTypes =
                            highlightedTitles?.let { titles ->
                                mapModel.markers
                                    .filter { resolvePinEmphasis(it.title, titles) == PinEmphasis.Highlighted }
                                    .map { it.markerUiType }
                                    .toPersistentSet()
                            }
                        Legend(
                            types = mapModel.markers.map { it.markerUiType }.toPersistentSet(),
                            highlightedTypes = highlightedTypes,
                            modifier =
                                if (highlightedTitles != null) {
                                    Modifier
                                        .align(Alignment.TopStart)
                                        .windowInsetsPadding(topInset())
                                        .padding(top = 80.dp, start = 4.dp)
                                } else {
                                    Modifier
                                        .padding(4.dp)
                                        .align(Alignment.BottomStart)
                                },
                        )
                    }
                }
            }
        }

        is MapModel.Loading -> {
            // TODO("implement loading")
        }
    }
}

// The map draws behind the status bar, so the chrome floating over it re-applies that inset.
// Only the top: the bottom is the navigation bar, which the map surface already applied.
@Composable
private fun topInset(): WindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top)

// Camera padding takes a Dp rather than a WindowInsets, and asPaddingValues() is not consumption
// aware, so this reads the raw top inset on purpose. Nothing consumed it.
@Composable
private fun topInsetHeight(): Dp = topInset().asPaddingValues().calculateTopPadding()

// Two slots so the map keeps its enclosing scope. Extracting it would mean threading about
// ten parameters through just to reuse it in both arrangements.
@Composable
private fun MapEditorLayout(
    sideBySide: Boolean,
    showPanel: Boolean,
    panel: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier,
    map: @Composable (Modifier) -> Unit,
) {
    if (sideBySide && showPanel) {
        BoxWithConstraints(modifier.fillMaxSize()) {
            // Keep the map at least half the width on a narrow two pane window.
            val paneWidth = minOf(EDITOR_PANE_WIDTH, maxWidth / 2)
            Row(Modifier.fillMaxSize()) {
                map(Modifier.weight(1f).fillMaxHeight())
                panel(Modifier.width(paneWidth).fillMaxHeight())
            }
        }
    } else {
        Column(modifier.fillMaxSize()) {
            map(Modifier.weight(1f).fillMaxWidth())
            if (showPanel) {
                panel(Modifier.fillMaxWidth())
            }
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
fun Legend(
    types: ImmutableSet<MarkerUiType>,
    modifier: Modifier = Modifier,
    // Null while no detail is open, every swatch rests. Otherwise the swatch tracks the pin,
    // bright for a highlighted category and faded for one the map has dimmed.
    highlightedTypes: ImmutableSet<MarkerUiType>? = null,
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(Modifier.padding(4.dp)) {
            for (type in MarkerUiType.entries) {
                if (type !in types) continue
                val emphasis =
                    when {
                        highlightedTypes == null -> PinEmphasis.Default
                        type in highlightedTypes -> PinEmphasis.Highlighted
                        else -> PinEmphasis.Dimmed
                    }
                val swatch = Color(PinBitmapFactory.swatchColor(type, emphasis))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        Modifier
                            .size(10.dp)
                            .alpha(if (emphasis == PinEmphasis.Dimmed) 0.5f else 1f)
                            .background(swatch, CircleShape)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape),
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = type.germanLabel(), style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
