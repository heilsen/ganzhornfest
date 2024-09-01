package de.heilsen.ganzhornfest.bus

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BusViewModel @Inject constructor(
    private val busPresenter: BusPresenter
) : MoleculeViewModel<BusEvent, BusModel>() {

    @Composable
    override fun models(events: Flow<BusEvent>): BusModel {
        return busPresenter.present(events)
    }

}