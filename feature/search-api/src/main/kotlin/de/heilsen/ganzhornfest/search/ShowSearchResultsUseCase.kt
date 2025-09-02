package de.heilsen.ganzhornfest.search

import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow

interface ShowSearchResultsUseCase  {
    operator fun invoke(
        searchTerm: String,
        category: Category
    ): Flow<PersistentList<SearchModel.Result>>
}
