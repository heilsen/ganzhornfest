package de.heilsen.ganzhornfest.core

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.LocalDate

@ContributesBinding(AppScope::class)
class FestivalOpeningDays @Inject constructor() : GetOpeningDaysUseCase {
    override fun invoke(): PersistentList<LocalDate> = FestivalEdition.days.toPersistentList()
}
