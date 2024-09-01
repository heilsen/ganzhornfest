package de.heilsen.ganzhornfest.theme.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun SelectionCard(
    selectionConfigs: List<SelectionConfig<*>>,
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {},
) {
    Card(modifier = modifier) {
        Column(Modifier.padding(8.dp)) {
            header()
            val columnCount = when (LocalConfiguration.current.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> 1
                else /*Configuration.ORIENTATION_LANDSCAPE*/ -> 2
            }
            LazyVerticalGrid(columns = GridCells.Fixed(columnCount), content = {
                items(selectionConfigs.size) { index ->
                    val selectionConfig: SelectionConfig<*> = selectionConfigs[index]
                    Selection(selectionConfig = selectionConfig)
                }
            })
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun <T> Selection(
    selectionConfig: SelectionConfig<T>,
    modifier: Modifier = Modifier,
) {
    val selectedItem = selectionConfig.selectedItem
    val selectedItemLabel = selectionConfig.selectedItemLabel
    val items = selectionConfig.items
    val formatItem = selectionConfig.formatItem
    val onItemSelected = selectionConfig.onItemSelected

    var expanded: Boolean by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field to handle
            // expanding/collapsing the menu on click. A read-only text field has
            // the anchor type `PrimaryNotEditable`.
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = formatItem(selectedItem),
            onValueChange = { /*no-op*/ },
            readOnly = true,
            singleLine = true,
            label = { Text(selectedItemLabel) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    onClick = {
                        onItemSelected(items[index])
                        expanded = false
                    },
                    text = {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = formatItem(item),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                )
            }
        }
    }
}

data class SelectionConfig<T>(
    val selectedItem: T,
    val selectedItemLabel: String,
    val items: List<T>,
    val onItemSelected: (T) -> Unit,
    val formatItem: (T) -> String = Any?::toString,
)

