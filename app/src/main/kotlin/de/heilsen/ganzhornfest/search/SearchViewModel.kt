package de.heilsen.ganzhornfest.search

import androidx.compose.runtime.Composable
import de.heilsen.ganzhornfest.core.MoleculeViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val searchPresenter: SearchPresenter
): MoleculeViewModel<SearchEvent, SearchModel>() {

    @Composable
    override fun models(events: Flow<SearchEvent>): SearchModel {
        return searchPresenter.present(events)
    }
}