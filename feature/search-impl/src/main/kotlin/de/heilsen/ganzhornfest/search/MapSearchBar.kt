package de.heilsen.ganzhornfest.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.ResourcesProvider
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.di.getValue
import de.heilsen.ganzhornfest.di.rememberAppGraph
import de.heilsen.ganzhornfest.search.impl.R
import de.heilsen.ganzhornfest.theme.component.EmptyScreen
import de.heilsen.ganzhornfest.theme.component.LoadingScreen
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf

interface EntryPoint {
    val resourcesProvider: ResourcesProvider
}

@OptIn(ExperimentalMaterial3Api::class)
@PreviewDefault
@Composable
fun MapSearchBar(
    modifier: Modifier = Modifier,
    searchModel: SearchModel =
        SearchModel.Data(
            "Search Term",
            persistentListOf(Category.Food, Category.Drink, Category.Club),
            persistentSetOf(Category.Food, Category.Drink),
            persistentListOf(
                SearchModel.Result("Result Header 1", "Result Description 1", Category.Food),
                SearchModel.Result("Result Header 2", "", Category.Drink),
            ),
        ),
    onEvent: (SearchEvent) -> Unit = {},
    onSearchResultClicked: (String, Category) -> Unit = { _, _ -> },
) {
    val entryPoint: EntryPoint by rememberAppGraph()
    val resourcesProvider = entryPoint.resourcesProvider

    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler(enabled = expanded) { expanded = false }

    val query = if (searchModel is SearchModel.Data) searchModel.query else ""

    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.focusRequester(focusRequester),
                query = query,
                onQueryChange = { onEvent(SearchEvent.Search(it)) },
                onSearch = { keyboardController?.hide() },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text(resourcesProvider.getString(R.string.empty_search)) },
                leadingIcon = {
                    if (expanded) {
                        IconButton(onClick = {
                            expanded = false
                            onEvent(SearchEvent.Clear)
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = {
                            onEvent(SearchEvent.Search(""))
                            focusRequester.requestFocus()
                        }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = resourcesProvider.getString(R.string.clear_search),
                            )
                        }
                    }
                },
            )
        },
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        when (searchModel) {
            is SearchModel.Data -> {
                SearchResults(searchModel, onEvent, onSearchResultClicked, resourcesProvider)
            }

            SearchModel.Loading -> LoadingScreen()
        }
    }
}

@Composable
private fun SearchResults(
    searchModel: SearchModel.Data,
    onEvent: (SearchEvent) -> Unit,
    onSearchResultClicked: (String, Category) -> Unit,
    resourcesProvider: ResourcesProvider,
) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(
            modifier =
                Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            searchModel.categories.forEach { category ->
                FilterChip(
                    selected = category in searchModel.selectedCategories,
                    onClick = { onEvent(SearchEvent.ToggleCategory(category)) },
                    label = {
                        Text(
                            when (category) {
                                Category.Food -> resourcesProvider.getString(R.string.food)
                                Category.Drink -> resourcesProvider.getString(R.string.drink)
                                Category.Club -> resourcesProvider.getString(R.string.club)
                            },
                        )
                    },
                )
            }
        }
        if (searchModel.results.isEmpty()) {
            EmptyScreen {
                Text(
                    text =
                        when {
                            searchModel.selectedCategories.isEmpty() ->
                                resourcesProvider.getString(R.string.no_category_selected)

                            searchModel.query.isNotEmpty() ->
                                resourcesProvider.getString(R.string.no_results_for_query)

                            else -> resourcesProvider.getString(R.string.no_results)
                        },
                )
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                items(searchModel.results) { result ->
                    Card(onClick = { onSearchResultClicked(result.header, result.category) }) {
                        Column(Modifier.padding(8.dp)) {
                            Text(result.header, style = MaterialTheme.typography.headlineSmall)
                            Text(result.description)
                        }
                    }
                }
            }
        }
    }
}
