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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.ResourcesProvider
import de.heilsen.ganzhornfest.core.compose.preview.DefaultPreviews
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
@DefaultPreviews
// The real component, not a preview-only wrapper. It stays public for :app to call, so the
// preview-must-be-private rule does not apply here.
@Suppress("ComposePreviewPublic")
@Composable
fun MapSearchBar(
    modifier: Modifier = Modifier,
    searchModel: SearchModel =
        SearchModel.Data(
            "Search Term",
            persistentListOf(Category.Food, Category.Drink, Category.Club),
            persistentSetOf(Category.Food, Category.Drink),
            persistentListOf(
                SearchModel.Result(1, "Result Header 1", "Result Description 1", Category.Food),
                SearchModel.Result(2, "Result Header 2", "", Category.Drink),
            ),
        ),
    onEvent: (SearchEvent) -> Unit = {},
    onSearchResultClicked: (Long, Category) -> Unit = { _, _ -> },
) {
    val entryPoint: EntryPoint by rememberAppGraph()
    val resourcesProvider = entryPoint.resourcesProvider
    val context = LocalContext.current

    val restoredQuery = (searchModel as? SearchModel.Data)?.query.orEmpty()
    val restoredExpanded = (searchModel as? SearchModel.Data)?.expanded == true
    // Local copies so typing stays snappy. The presenter model is the source of truth across
    // Map leaving composition (search result -> detail -> back). remember resets then.
    var expanded by remember { mutableStateOf(restoredExpanded) }
    var query by remember { mutableStateOf(restoredQuery) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) { onEvent(SearchEvent.UiReady) }

    BackHandler(enabled = expanded) {
        expanded = false
        onEvent(SearchEvent.SetExpanded(false))
    }

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
                    onEvent(SearchEvent.SetExpanded(true))
                }
        }

    SearchBar(
        // Expanded, the bar fills the pane and the result list runs under the keyboard. The
        // collapsed bar is top aligned, so this is a no-op there.
        modifier = modifier.imePadding(),
        // The map surface hands the status bar inset in through modifier. SearchBarDefaults
        // would add its own on top again as the bar expands, and asPaddingValues() inside the
        // component is not consumption aware, so the caller's consumeWindowInsets cannot cancel
        // it.
        windowInsets = WindowInsets(0, 0, 0, 0),
        colors =
            SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
            ),
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
                onExpandedChange = {
                    expanded = it
                    onEvent(SearchEvent.SetExpanded(it))
                },
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
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
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
        onExpandedChange = {
            expanded = it
            onEvent(SearchEvent.SetExpanded(it))
        },
    ) {
        when (searchModel) {
            is SearchModel.Data -> {
                SearchResults(
                    searchModel,
                    onEvent,
                    onSearchResultClicked = { id, category ->
                        onEvent(SearchEvent.OpenResult)
                        keyboardController?.hide()
                        onSearchResultClicked(id, category)
                    },
                    resourcesProvider,
                )
            }

            SearchModel.Loading -> LoadingScreen()
        }
    }
}

@Composable
private fun SearchResults(
    searchModel: SearchModel.Data,
    onEvent: (SearchEvent) -> Unit,
    onSearchResultClicked: (Long, Category) -> Unit,
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
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(searchModel.results) { result ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onSearchResultClicked(result.id, result.category) },
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp),
                        ) {
                            Text(
                                result.header,
                                style = MaterialTheme.typography.titleMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                            if (result.description.isNotEmpty()) {
                                Text(
                                    result.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                            if (result.clubs.isNotEmpty()) {
                                Text(
                                    formatClubList(
                                        result.clubs,
                                        resourcesProvider.getString(R.string.several_clubs),
                                    ),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
