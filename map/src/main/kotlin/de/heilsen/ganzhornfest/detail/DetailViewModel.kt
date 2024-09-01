package de.heilsen.ganzhornfest.detail

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val detailPresenter: DetailPresenter
): MoleculeViewModel<DetailEvent, DetailModel>() {

    @Composable
    override fun models(events: Flow<DetailEvent>): DetailModel {
        return detailPresenter.present(events)
    }
}

