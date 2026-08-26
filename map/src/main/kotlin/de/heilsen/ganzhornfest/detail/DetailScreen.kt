package de.heilsen.ganzhornfest.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
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
import de.heilsen.ganzhornfest.theme.component.GanzhornfestScaffold
import timber.log.Timber

@Composable
fun DetailScreen(
    model: DetailModel,
    onBackClick: () -> Unit,
    onItemClicked: (String, DetailType) -> Unit,
    modifier: Modifier = Modifier,
    title: String = (model as? DetailModel.Success)?.title.orEmpty(),
) {
    Timber.tag("DetailScreen").i("Got model: $model")
    GanzhornfestScaffold(
        title = { Text(text = title) },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
            }
        },
    ) {
        if (model !is DetailModel.Success) return@GanzhornfestScaffold
        val scrollState = rememberScrollState()
        LazyColumn(
            modifier =
                Modifier
                    .scrollable(
                        state = scrollState,
                        orientation = Orientation.Vertical,
                    ).weight(1f),
        ) {
            stickyHeader {
                val sectionTitle =
                    when (model.type) {
                        DetailType.Club -> "Angebot"
                        DetailType.Offer -> "Vereine"
                    }
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(12.dp)
                            .padding(start = 12.dp), // align text begin with regular items
                    text = sectionTitle,
                    style = MaterialTheme.typography.headlineMedium,
                )
            }
            items(items = model.items, itemContent = { item ->
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    onClick = {
                        when (model.type) {
                            DetailType.Club -> onItemClicked(item.name, DetailType.Offer)
                            DetailType.Offer -> onItemClicked(item.name, DetailType.Club)
                        }
                    },
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        val description = item.description
                        if (!description.isNullOrBlank()) {
                            Text(
                                text = description,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            })
        }
    }
}
