package de.heilsen.ganzhornfest.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.map.MapScreen
import de.heilsen.ganzhornfest.theme.component.GanzhornfestScaffold
import timber.log.Timber

@Composable
fun DetailScreen(
    model: DetailModel,
    onBackClick: () -> Unit,
    onItemClicked: (String, DetailType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Timber.tag("DetailScreen").i("Got model: $model")
    //TODO: handle DetailModel Loading
    if (model !is DetailModel.Success) return
    GanzhornfestScaffold(
        title = { Text(text = model.title) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
            }
        }
    ) {
        val mapModel = model.mapModel
        MapScreen(
            modifier = Modifier.weight(1f),
            mapModel = mapModel,
            onMarkerSelected = { title, type ->
                //TODO: implement Details
//                                println("onMarkerSelected: $title (type: $type)")
//                                if (type == MarkerUiType.CLUB) {
//                                    navController.navigate(Destination.Detail(title, type.toString()))
//                                }
            }
        )
        val scrollState = rememberScrollState()
        LazyColumn(
            modifier = Modifier
                .scrollable(
                    state = scrollState,
                    orientation = Orientation.Vertical
                )
                .weight(1f),
        ) {
            stickyHeader {
                val sectionTitle = when (model.type) {
                    DetailType.Club -> "Angebot"
                    DetailType.Offer -> "Vereine"
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(12.dp)
                        .padding(start = 12.dp) // align text begin with regular items
                    ,
                    text = sectionTitle,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            items(items = model.items, itemContent = { card: String ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    onClick = {
                        when (model.type) {
                            DetailType.Club -> onItemClicked(card, DetailType.Offer)
                            DetailType.Offer -> onItemClicked(card, DetailType.Club)
                        }
                    }) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = card,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            })
        }
    }
}