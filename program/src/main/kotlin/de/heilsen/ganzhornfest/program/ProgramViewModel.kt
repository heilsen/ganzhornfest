package de.heilsen.ganzhornfest.program

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProgramViewModel @Inject constructor(
    private val programPresenter: ProgramPresenter
) : MoleculeViewModel<ProgramEvent, ProgramModel>() {

    @Composable
    override fun models(events: Flow<ProgramEvent>): ProgramModel {
        return programPresenter.present(events)
    }

}