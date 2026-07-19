package de.heilsen.ganzhornfest.program

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

class ProgramViewModel
    @Inject
    constructor(
        private val programPresenter: ProgramPresenter,
    ) : MoleculeViewModel<ProgramEvent, ProgramModel>() {
        @Composable
        override fun models(events: Flow<ProgramEvent>): ProgramModel = programPresenter.present(events)
    }
