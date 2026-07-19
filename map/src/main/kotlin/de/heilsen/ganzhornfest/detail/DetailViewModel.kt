package de.heilsen.ganzhornfest.detail

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.Flow

class DetailViewModel
    @Inject
    constructor(
        private val detailPresenter: DetailPresenter,
    ) : MoleculeViewModel<DetailEvent, DetailModel>() {
        @Composable
        override fun models(events: Flow<DetailEvent>): DetailModel = detailPresenter.present(events)
    }
