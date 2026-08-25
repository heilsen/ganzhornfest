package de.heilsen.ganzhornfest.map

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewModelScope
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MapViewModel
    @Inject
    constructor(
        private val presenter: MapPresenter,
    ) : MoleculeViewModel<MapEvent, MapModel>() {
        fun onEvent(event: MapEvent) {
            if (event is MapEvent.ApplyLatLng) {
                viewModelScope.launch(Dispatchers.IO) {
                    presenter.applyLatLng(event.coordinateId, event.lat, event.lng)
                }
                return
            }
            take(event)
        }

        @Composable
        override fun models(events: Flow<MapEvent>): MapModel = presenter.present()
    }
