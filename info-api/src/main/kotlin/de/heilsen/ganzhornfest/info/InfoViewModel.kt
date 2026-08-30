package de.heilsen.ganzhornfest.info

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

class InfoViewModel
    @Inject
    constructor(
        private val presenter: InfoPresenter,
    ) : MoleculeViewModel<Unit, InfoModel>() {
        @Composable
        override fun models(events: Flow<Unit>): InfoModel = presenter.present()
    }
