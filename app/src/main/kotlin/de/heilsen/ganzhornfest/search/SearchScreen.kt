package de.heilsen.ganzhornfest.search

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.R
import de.heilsen.ganzhornfest.core.ResourcesProvider
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.theme.component.LoadingScreen
import de.heilsen.ganzhornfest.theme.component.SelectionCard
import de.heilsen.ganzhornfest.theme.component.SelectionConfig
import kotlinx.collections.immutable.persistentListOf

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // empty lambda in Scaffold, due to SearchBar
@OptIn(ExperimentalMaterial3Api::class)
@PreviewDefault
@Composable
fun SearchScreen(
    searchModel: SearchModel = SearchModel.Data(
        "Search Term",
        persistentListOf(Category.Food, Category.Drink),
        Category.Food,
        persistentListOf(
            SearchModel.Result("Result Header 1", "Result Description 1"),
            SearchModel.Result("Result Header 2", "")
        )
    ),
    onEvent: (SearchEvent) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            val resourcesProvider = ResourcesProvider(LocalContext.current)
            var query by remember { mutableStateOf("") }

            SearchBar(
                leadingIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
                    }
                },
                query = query,
                active = true,
                onActiveChange = { /* no-op */ },
                onQueryChange = {
                    query = it
                    onEvent(SearchEvent.Search(it))
                },
                onSearch = { onEvent(SearchEvent.Search(it)) },
                placeholder = { Text(resourcesProvider.getString(R.string.empty_search)) },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            query = ""
                            onEvent(SearchEvent.Clear)
                        },
                        imageVector = Icons.Default.Clear,
                        contentDescription = resourcesProvider.getString(R.string.clear_search),
                    )
                },
            ) {
                when (searchModel) {
                    is SearchModel.Data -> {
                        SearchScreenSuccess(searchModel, onEvent, resourcesProvider)
                    }

                    SearchModel.Loading -> LoadingScreen()
                }
            }
        },
        content = {}
    )
}

@Composable
private fun SearchScreenSuccess(
    searchModel: SearchModel.Data,
    onEvent: (SearchEvent) -> Unit,
    resourcesProvider: ResourcesProvider
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {

        val selectionConfigs: List<SelectionConfig<*>> = listOf(
            SelectionConfig(
                selectedItem = searchModel.selectedCategory,
                selectedItemLabel = "Kategorie",
                items = searchModel.categories,
                onItemSelected = { onEvent(SearchEvent.ChangeCategory(it)) },
                formatItem = { item ->
                    when (item) {
                        Category.Food -> resourcesProvider.getString(R.string.food)
                        Category.Drink -> resourcesProvider.getString(R.string.drink)
                        Category.Club -> resourcesProvider.getString(R.string.club)
                    }
                }
            ),
        )
        SelectionCard(selectionConfigs)
        Spacer(Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(searchModel.results) { (message, description) ->
                Card(onClick = { /* TODO: onClick(message) */ }) {
                    Column(Modifier.padding(8.dp)) {
                        Text(message, style = MaterialTheme.typography.headlineSmall)
                        Text(description)
                    }
                }
            }
        }
    }
}
