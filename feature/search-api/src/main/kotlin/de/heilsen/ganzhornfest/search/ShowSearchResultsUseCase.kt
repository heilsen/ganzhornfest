package de.heilsen.ganzhornfest.search

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.coroutines.flow.Flow

interface ShowSearchResultsUseCase {
    operator fun invoke(
        searchTerm: String,
        categories: PersistentSet<Category>,
    ): Flow<PersistentList<SearchModel.Result>>
}
