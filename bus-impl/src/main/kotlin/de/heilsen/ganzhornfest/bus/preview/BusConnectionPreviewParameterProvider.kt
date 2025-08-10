package de.heilsen.ganzhornfest.bus.preview

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import de.heilsen.ganzhornfest.bus.BusConnection
import de.heilsen.ganzhornfest.bus.Busline
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

class BusConnectionPreviewParameterProvider : CollectionPreviewParameterProvider<BusConnection>(
    persistentListOf(
        BusConnection(
            Busline(
                "Sonderbus",
                "Obereisesheim",
                persistentListOf("Dahenfeld", "Amorbach")
            ),
            day3.atTime(23, 45)
        ),
        BusConnection(
            Busline("692/92", "Amorbach"),
            day1.atTime(19, 34)
        ),
        BusConnection(
            Busline("624", "Dahenfeld", persistentListOf("Amorbach")),
            day2.atTime(19, 0)
        ),
        BusConnection(
            Busline("92", "Neuberg"),
            day3.atTime(1, 0)
        )
    )
)