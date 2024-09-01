package de.heilsen.ganzhornfest.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@PreviewDefault
@Composable
fun VerticalCards(
    @PreviewParameter(SampleProvider::class) messages: ImmutableList<String>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
}

@PreviewDefault
@Composable
fun HorizontalCards(
    @PreviewParameter(SampleProvider::class) messages: ImmutableList<String>,
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        horizontalArrangement = Arrangement.Start,
        verticalArrangement = Arrangement.Top
    ) {
        items(messages) { message ->
            Card(
                modifier = Modifier.wrapContentHeight(Alignment.Top),
                onClick = {}
            ) {
                Column() {
                    Text(message, style = MaterialTheme.typography.headlineSmall)
                    Text(message)
                }
            }
        }
    }
}

class SampleProvider : PreviewParameterProvider<ImmutableList<String>> {
    override val values = sequenceOf(persistentListOf("Item 1", "Item 2"))
}