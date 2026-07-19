package de.heilsen.ganzhornfest.map

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

class MapViewModel
    @Inject
    constructor(
        private val presenter: MapPresenter,
    ) : MoleculeViewModel<MapEvent, MapModel>() {
        @Composable
        override fun models(events: Flow<MapEvent>): MapModel = presenter.present(events)
    }
