package de.heilsen.ganzhornfest.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.filled.Mic
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
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
import timber.log.Timber

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
    val context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BackHandler(enabled = expanded) { expanded = false }

    // Driven locally rather than from searchModel.query: events round-trip through the
    // presenter's MutableSharedFlow and a separate collecting coroutine, so the model's query
    // lags a recomposition behind each keystroke. Feeding that lag back into the text field's
    // own value desyncs its internal edit buffer and snaps the cursor backward.
    var query by remember { mutableStateOf("") }

    val speechRecognizerAvailable =
        remember {
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).resolveActivity(context.packageManager) != null
        }
    val speechLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()
                ?.let { spoken ->
                    query = spoken
                    onEvent(SearchEvent.Search(spoken))
                    expanded = true
                }
        }

    SearchBar(
        modifier = modifier,
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier.focusRequester(focusRequester),
                query = query,
                onQueryChange = {
                    query = it
                    onEvent(SearchEvent.Search(it))
                },
                onSearch = { keyboardController?.hide() },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                placeholder = { Text(resourcesProvider.getString(R.string.empty_search)) },
                leadingIcon = {
                    if (expanded) {
                        IconButton(onClick = {
                            expanded = false
                            query = ""
                            onEvent(SearchEvent.Clear)
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                trailingIcon = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (speechRecognizerAvailable) {
                            IconButton(onClick = {
                                val intent =
                                    Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                        putExtra(
                                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
                                        )
                                        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "de-DE")
                                        putExtra(
                                            RecognizerIntent.EXTRA_PROMPT,
                                            resourcesProvider.getString(R.string.voice_search_prompt),
                                        )
                                    }
                                try {
                                    speechLauncher.launch(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Timber.tag("MapSearchBar").w(e, "No speech recognizer available")
                                }
                            }) {
                                Icon(
                                    Icons.Default.Mic,
                                    contentDescription = resourcesProvider.getString(R.string.voice_search),
                                )
                            }
                        }
                        if (query.isNotEmpty()) {
                            IconButton(onClick = {
                                query = ""
                                onEvent(SearchEvent.Search(""))
                                focusRequester.requestFocus()
                            }) {
                                Icon(
                                    Icons.Default.Clear,
                                    contentDescription = resourcesProvider.getString(R.string.clear_search),
                                )
                            }
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
                            if (result.description.isNotEmpty()) {
                                Text(result.description)
                            }
                            if (result.clubs.isNotEmpty()) {
                                Text(result.clubs, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
