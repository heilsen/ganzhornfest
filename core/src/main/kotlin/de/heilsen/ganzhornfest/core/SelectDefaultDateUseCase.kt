package de.heilsen.ganzhornfest.core

import com.squareup.anvil.annotations.ContributesBinding
import de.heilsen.ganzhornfest.di.AppScope
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import javax.inject.Inject

fun interface SelectDefaultDateUseCase {
    operator fun invoke(): LocalDate
}

@ContributesBinding(AppScope::class)
class SelectDefaultDate @Inject constructor(private val getOpeningDays: GetOpeningDaysUseCase):
    SelectDefaultDateUseCase {
    override fun invoke(): LocalDate {
        val (first, _, last) = getOpeningDays()
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        if (today < first) return first
        if (today > last) return last
        return today
    }
}