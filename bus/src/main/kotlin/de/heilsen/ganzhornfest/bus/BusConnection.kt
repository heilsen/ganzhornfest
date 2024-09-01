package de.heilsen.ganzhornfest.bus

import androidx.compose.runtime.Immutable
import de.heilsen.ganzhornfest.core.datetime.dayOfTheWeek
import de.heilsen.ganzhornfest.core.datetime.formatToLocalDate
import de.heilsen.ganzhornfest.core.datetime.formatToLocalTime
import kotlinx.datetime.LocalDateTime
import java.text.DateFormat

@Immutable
data class BusConnection(
    val busLine: Busline,
    val departureAt: LocalDateTime
) {
    fun formattedDepartureAt(showDay: Boolean) = buildString {
        if (showDay) {
            append(dayOfTheWeek(departureAt))
            append(", ")
        }
        append(formatToLocalTime(departureAt, DateFormat.SHORT))
    }

    val key = "$busLine$departureAt"
}