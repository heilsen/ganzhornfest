package de.heilsen.ganzhornfest.core

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetOpeningDaysFor2025 @Inject constructor() : GetOpeningDaysUseCase {
    override fun invoke(): PersistentList<LocalDate> {
        val first = LocalDate(2025, 9, 6)
        val second = LocalDate(2025, 9, 7)
        val last = LocalDate(2025, 9, 8)
        return persistentListOf(first, second, last)
    }
}