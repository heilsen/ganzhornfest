package de.heilsen.ganzhornfest.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentHashMap
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val showResults: ShowSearchResultsUseCase,
) {
    @Composable
    fun present(events: Flow<SearchEvent>): SearchModel {
        var category by remember { mutableStateOf(Category.Club) }
        var currentQuery by remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            events.collect { event ->
                when (event) {
                    is SearchEvent.Search -> {
                        currentQuery = event.query
                    }

                    is SearchEvent.ChangeCategory -> {
                        category = event.category
                    }

                    SearchEvent.Clear -> {
                        currentQuery = ""
                        category = Category.Club
                    }
                }
            }
        }

        return SearchModel.Data(
            currentQuery,
            Category.entries.toPersistentList(),
            selectedCategory = category,
            showResults(currentQuery, category)
                .collectAsState(initial = persistentListOf()).value
        )
    }
}