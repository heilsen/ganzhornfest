package de.heilsen.ganzhornfest.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.map.MapScreen
import de.heilsen.ganzhornfest.theme.component.GanzhornfestScaffold

@Composable
fun DetailScreen(model: DetailModel) {
    println("Got model: $model")
    GanzhornfestScaffold(
        title = { Text("TODO") },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "zurück")
            }
        }
    ) {
        if (model is DetailModel.Club) {
            Box {
                Text(modifier = Modifier.background(Color.Magenta), text = model.clubName)
            }
        }
    }
    Column(Modifier.fillMaxSize()) {
        MapScreen(modifier = Modifier.weight(1f))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        ) {
            item {
                Text(text = "Angebot", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(items = listOf("TODO"), itemContent = { card: String ->
                Card(onClick = {}) {
                    Column {
                        Text(card, style = MaterialTheme.typography.headlineSmall)
                        Text(card)
                    }
                }
                //TODO: use VerticalCards(card)
            })
        }
    }
}