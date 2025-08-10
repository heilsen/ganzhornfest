package de.heilsen.ganzhornfest.core

import kotlin.time.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

@OptIn(kotlin.time.ExperimentalTime::class)
class SelectDefaultDateUseCaseImpl @Inject constructor(private val getOpeningDays: GetOpeningDaysUseCase):
    SelectDefaultDateUseCase {
    override fun invoke(): LocalDate {
        val (first, _, last) = getOpeningDays()
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        if (today < first) return first
        if (today > last) return last
        return today
    }
}