package de.heilsen.ganzhornfest.bus.preview

import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import de.heilsen.ganzhornfest.bus.BusModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn


val day1 = LocalDate(2024,8,31)
val day2 = LocalDate(2024,9,1)
val day3 = LocalDate(2024,9,2)
val days = persistentListOf(day1, day2, day3)

class BusModelPreviewParameterProvider : CollectionPreviewParameterProvider<BusModel>(
    listOf(
        BusModel.Data(
            "Amorbach",
            persistentListOf("Amorbach", "Dahenfeld", "Neuberg"),
            day1,
            days,
            BusConnectionPreviewParameterProvider().values.toPersistentList()
        ),
        BusModel.Data(
            "Amorbach",
            persistentListOf("Amorbach", "Dahenfeld", "Neuberg"),
            day1,
            persistentListOf(),
            persistentListOf()
        ),
        BusModel.Data(
            "",
            persistentListOf(),
            day1,
            persistentListOf(),
            persistentListOf()
        ),
        BusModel.Loading
    )
)