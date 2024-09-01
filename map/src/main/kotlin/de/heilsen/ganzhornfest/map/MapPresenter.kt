package de.heilsen.ganzhornfest.map

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapPresenter @Inject constructor(
    private val getMarkers: GetMarkersUseCase
) {

    @Composable
    fun present(events: Flow<MapEvent>): MapModel {
        val markers by getMarkers().collectAsState(initial = persistentSetOf())

        val event: MapEvent by events.collectAsState(initial = MapEvent.Init as MapEvent)

        when (val currentEvent = event) {
            MapEvent.Init -> {
                /*no-op*/
            }
        }
        return MapModel.Data(markers)
    }
}

