package de.heilsen.ganzhornfest.countdown

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

class CountdownViewModel
    @Inject
    constructor(
        private val presenter: CountdownPresenter,
    ) : MoleculeViewModel<Unit, CountdownModel>() {
        @Composable
        override fun models(events: Flow<Unit>): CountdownModel = presenter.present(events)
    }
