package de.heilsen.ganzhornfest.detail

import androidx.compose.foundation.layout.Box
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
import de.heilsen.ganzhornfest.theme.component.ErrorScreen
import de.heilsen.ganzhornfest.theme.component.LoadingScreen

@Composable
fun DetailScreen(
    model: DetailModel,
    onBackClick: () -> Unit,
    onItemClicked: (DetailTarget) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
            }
            Text(
                text = (model as? DetailModel.Success)?.title.orEmpty(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 16.dp),
            )
        }
        // LoadingScreen and ErrorScreen fillMaxSize, and in a Column that means the full
        // incoming height, not what is left under the header. The weighted Box bounds them.
        Box(Modifier.weight(1f)) {
            when (model) {
                DetailModel.Loading -> LoadingScreen()
                DetailModel.Error -> ErrorScreen()
                is DetailModel.Success -> DetailContent(model, onItemClicked)
            }
        }
    }
}

@Composable
private fun DetailContent(
    model: DetailModel.Success,
    onItemClicked: (DetailTarget) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxSize()) {
        val sectionTitle =
            when (model.target) {
                is DetailTarget.Club -> "Angebot"
                is DetailTarget.Offer -> "Vereine"
                is DetailTarget.Poi -> "Kategorie"
                is DetailTarget.Category -> "Standorte"
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
                    onClick = { onItemClicked(item.target) },
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
