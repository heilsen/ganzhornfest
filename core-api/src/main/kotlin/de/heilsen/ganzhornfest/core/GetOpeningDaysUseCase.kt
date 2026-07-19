package de.heilsen.ganzhornfest.core

import kotlinx.collections.immutable.PersistentList
import kotlinx.datetime.LocalDate

fun interface GetOpeningDaysUseCase {
    operator fun invoke(): PersistentList<LocalDate>
}
