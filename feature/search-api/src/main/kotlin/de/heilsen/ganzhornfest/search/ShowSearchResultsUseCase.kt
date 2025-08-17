package de.heilsen.ganzhornfest.search

import de.heilsen.ganzhornfest.core.ConfigurationProvider
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

interface ShowSearchResultsUseCase  {
    operator fun invoke(
        searchTerm: String,
        category: Category
    ): Flow<PersistentList<SearchModel.Result>>
}
