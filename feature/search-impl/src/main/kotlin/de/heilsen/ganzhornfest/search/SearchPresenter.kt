package de.heilsen.ganzhornfest.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlin.time.Duration.Companion.milliseconds

class SearchPresenter
    @Inject
    constructor(
        private val showResults: ShowSearchResultsUseCase,
    ) {
        @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
        @Composable
        fun present(events: Flow<SearchEvent>): SearchModel {
            var selectedCategories by remember { mutableStateOf(persistentSetOf(Category.Club)) }
            var currentQuery by remember { mutableStateOf("") }
            var expanded by remember { mutableStateOf(false) }
            var suppressTeardown by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                events.collect { event ->
                    when (event) {
                        is SearchEvent.Search -> {
                            if (suppressTeardown && event.query.isEmpty()) return@collect
                            currentQuery = event.query
                        }

                        is SearchEvent.ToggleCategory -> {
                            selectedCategories =
                                if (event.category in selectedCategories) {
                                    selectedCategories.remove(event.category)
                                } else {
                                    selectedCategories.add(event.category)
                                }
                        }

                        SearchEvent.Clear -> {
                            suppressTeardown = false
                            currentQuery = ""
                            expanded = false
                            selectedCategories = persistentSetOf(Category.Club)
                        }

                        is SearchEvent.SetExpanded -> {
                            if (suppressTeardown && !event.expanded) return@collect
                            expanded = event.expanded
                        }

                        SearchEvent.OpenResult -> {
                            expanded = true
                            suppressTeardown = true
                        }

                        SearchEvent.UiReady -> {
                            suppressTeardown = false
                        }
                    }
                }
            }

            val results by
                remember {
                    snapshotFlow { currentQuery to selectedCategories }
                        .debounce(300.milliseconds)
                        .flatMapLatest { (query, categories) -> showResults(query, categories) }
                }.collectAsState(initial = persistentListOf())

            return SearchModel.Data(
                currentQuery,
                Category.entries.toPersistentList(),
                selectedCategories = selectedCategories,
                results = results,
                expanded = expanded,
            )
        }
    }
