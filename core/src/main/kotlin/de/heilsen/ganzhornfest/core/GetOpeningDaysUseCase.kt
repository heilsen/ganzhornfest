package de.heilsen.ganzhornfest.core

import com.squareup.anvil.annotations.ContributesBinding
import de.heilsen.ganzhornfest.di.AppScope
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate
import javax.inject.Inject

fun interface GetOpeningDaysUseCase {
    operator fun invoke(): PersistentList<LocalDate>
}

@ContributesBinding(AppScope::class)
class GetOpeningDaysFor2024 @Inject constructor() : GetOpeningDaysUseCase {
    override fun invoke(): PersistentList<LocalDate> {
        val first = LocalDate(2024, 8, 31)
        val second = LocalDate(2024, 9, 1)
        val last = LocalDate(2024, 9, 2)
        return persistentListOf(first, second, last)
    }
}