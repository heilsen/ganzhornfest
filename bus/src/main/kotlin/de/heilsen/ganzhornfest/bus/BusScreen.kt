package de.heilsen.ganzhornfest.bus

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.core.datetime.formatToLocalDate
import de.heilsen.ganzhornfest.bus.preview.BusModelPreviewParameterProvider
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme
import de.heilsen.ganzhornfest.theme.component.EmptyScreen
import de.heilsen.ganzhornfest.theme.component.GanzhornfestScaffold
import de.heilsen.ganzhornfest.theme.component.LoadingScreen
import de.heilsen.ganzhornfest.theme.component.SelectionCard
import de.heilsen.ganzhornfest.theme.component.SelectionConfig
import kotlinx.datetime.LocalDate
import java.text.DateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusScreen(
    busModel: BusModel,
    onEvent: (BusEvent) -> Unit = {},
) {
    GanzhornfestScaffold(
        title = { Text(stringResource(id = R.string.bus_screen_title)) }
    ) {
        Box {
            Column {
                when (busModel) {
                    BusModel.Loading -> LoadingScreen()
                    is BusModel.Data -> BusScreenSuccess(busModel, onEvent)
                }
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(MaterialTheme.colorScheme.surface),
                textAlign = TextAlign.End,
                text = "Angaben ohne Gewähr",
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun BusScreenSuccess(busModel: BusModel.Data, onEvent: (BusEvent) -> Unit) {
    val destinations = busModel.destinations
    val selectedDestination = busModel.destination
    val selectedDate = busModel.departureDate
    val availableDates = busModel.departureDates

    Column(modifier = Modifier.padding(8.dp, 8.dp, 8.dp)) {
        BusLineSelectionCard(
            destinations,
            selectedDestination,
            { onEvent(BusEvent.ChangeDestination(it)) },
            availableDates,
            selectedDate,
            { onEvent(BusEvent.ChangeDeparture(it)) }
        )
        Row(
            modifier = Modifier
                .padding(4.dp, 8.dp, 0.dp, 8.dp)
                .fillMaxWidth()
        ) {
            Text("Verbindungen (ab 19 Uhr)")
            HorizontalDivider(Modifier.align(Alignment.CenterVertically))
        }
        Connections(busModel.connections, selectedDate)
    }

}


@Composable
private fun Connections(connections: List<BusConnection>, selectedDate: LocalDate) {
    if (connections.isEmpty()) {
        EmptyScreen {
            Text(
                text = "Bitte die Auswahl oben ändern",
                textAlign = TextAlign.Center
            )
        }
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = connections.size,
                key = { index -> connections[index].key }
            ) { index ->
                val busConnection = connections[index]
                val showDay by remember { derivedStateOf { selectedDate != busConnection.departureAt.date } }
                BusTicket(busConnection = busConnection, showDay = showDay)
            }
        }
    }
}


@Composable
private fun BusLineSelectionCard(
    destinations: List<String>,
    selectedDestination: String,
    onDestinationSelected: (String) -> Unit,
    dates: List<LocalDate>,
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectionConfigs: List<SelectionConfig<*>> = listOf(
        SelectionConfig(
            selectedItem = selectedDestination,
            selectedItemLabel = "Nach",
            items = destinations,
            onItemSelected = onDestinationSelected
        ),
        SelectionConfig(
            selectedItem = selectedDate,
            selectedItemLabel = "Abfahrt",
            items = dates,
            onItemSelected = onDateSelected
        ) { formatToLocalDate(it, DateFormat.FULL) }
    )
    SelectionCard(
        selectionConfigs = selectionConfigs,
        modifier = modifier,
        header = {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                value = "Haltestelle ZOB/Ballei",
                onValueChange = { },
                readOnly = true,
                singleLine = true,
                label = { Text("Von") },
            )
        }
    )
}


@PreviewDefault
@Composable
fun BusScreenPreview(@PreviewParameter(provider = BusModelPreviewParameterProvider::class) busModel: BusModel) {
    GanzhornfestTheme {
        BusScreen(busModel)
    }
}