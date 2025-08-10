package de.heilsen.ganzhornfest.bus.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import de.heilsen.ganzhornfest.bus.BusConnection

class BusTicketPreviewParameterProvider : PreviewParameterProvider<Pair<BusConnection, Boolean>> {
    override val values: Sequence<Pair<BusConnection, Boolean>>
        get() {
            val busConnections = BusConnectionPreviewParameterProvider().values
            return sequenceOf(
                true,
                false
            ).flatMap { connection -> busConnections.map { it to connection } }
        }
}