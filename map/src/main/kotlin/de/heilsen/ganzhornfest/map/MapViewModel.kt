package de.heilsen.ganzhornfest.map

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val presenter: MapPresenter
) : MoleculeViewModel<MapEvent, MapModel>() {

    @Composable
    override fun models(events: Flow<MapEvent>): MapModel {
        return presenter.present(events)
    }
}