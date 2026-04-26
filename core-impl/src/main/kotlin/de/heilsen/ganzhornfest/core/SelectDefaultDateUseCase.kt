package de.heilsen.ganzhornfest.core

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import dev.zacsweers.metro.Inject
import kotlin.time.Clock

@OptIn(kotlin.time.ExperimentalTime::class)
@ContributesBinding(AppScope::class)
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