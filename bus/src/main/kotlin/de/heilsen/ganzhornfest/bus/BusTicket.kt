package de.heilsen.ganzhornfest.bus

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import de.heilsen.ganzhornfest.bus.preview.BusTicketPreviewParameterProvider
import de.heilsen.ganzhornfest.theme.component.ticket.Ticket
import de.heilsen.ganzhornfest.core.compose.preview.PreviewDefault
import de.heilsen.ganzhornfest.theme.GanzhornfestTheme

@Composable
fun BusTicket(
    busConnection: BusConnection,
    showDay: Boolean
) {
    Ticket(
        label = {
            Text(
                text = "Busfahrt Richtung",
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 4.dp
                ),
                style = MaterialTheme.typography.bodyMedium
            )
        }, header = {
            Text(
                text = busConnection.busLine.destination.toUpperCase(Locale.current),
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
                style = MaterialTheme.typography.titleLarge
            )
        }, sideBar = {
            val line = busConnection.busLine.line
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.CenterHorizontally),
                text = line.replace("/", "/\n"),
                style = MaterialTheme.typography.titleLarge
                    .copy(lineBreak = LineBreak.Paragraph)
            )
        }, description = {
            val stops = busConnection.busLine.stops
            Column {
                Text(
                    text = "Abfahrt",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = busConnection.formattedDepartureAt(showDay = showDay),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            if (stops.isNotEmpty()) {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Text(
                        text = "über",
                        style = MaterialTheme.typography.bodySmall
                    )
                    stops.forEach { stop ->
                        Text(
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            text = stop,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    )
}

@Composable
@PreviewDefault
fun BusTicketPreview(
    @PreviewParameter(BusTicketPreviewParameterProvider::class)
    busConnectionToShowDate: Pair<BusConnection, Boolean>
) {
    GanzhornfestTheme {
        val (busConnection, showDay) = busConnectionToShowDate
        BusTicket(busConnection = busConnection, showDay = showDay)
    }
}