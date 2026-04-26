package de.heilsen.ganzhornfest.core

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetOpeningDaysFor2026 @Inject constructor() : GetOpeningDaysUseCase {
    override fun invoke(): PersistentList<LocalDate> {
        val first = LocalDate(2026, 9, 5)
        val second = LocalDate(2026, 9, 6)
        val last = LocalDate(2026, 9, 7)
        return persistentListOf(first, second, last)
    }
}
