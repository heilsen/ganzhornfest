package de.heilsen.ganzhornfest.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailScreen(
    model: DetailModel,
    onBackClick: () -> Unit,
    onItemClicked: (String, DetailType) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (model !is DetailModel.Success) return
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
            }
            Text(
                text = model.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 16.dp),
            )
        }
        val sectionTitle =
            when (model.type) {
                DetailType.Club -> "Angebot"
                DetailType.Offer -> "Vereine"
                DetailType.Poi -> "Kategorie"
                DetailType.PoiCategory -> "Standorte"
            }
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            text = sectionTitle,
            style = MaterialTheme.typography.titleMedium,
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(items = model.items, itemContent = { item ->
                Card(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    onClick = {
                        when (model.type) {
                            DetailType.Club -> onItemClicked(item.routeKey, DetailType.Offer)
                            DetailType.Offer -> onItemClicked(item.routeKey, DetailType.Club)
                            DetailType.Poi -> onItemClicked(item.routeKey, DetailType.PoiCategory)
                            DetailType.PoiCategory -> onItemClicked(item.routeKey, DetailType.Poi)
                        }
                    },
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(text = item.name, style = MaterialTheme.typography.headlineSmall)
                        val description = item.description
                        if (!description.isNullOrBlank()) {
                            Text(text = description, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            })
        }
    }
}
