package de.heilsen.ganzhornfest.core

import kotlinx.datetime.LocalDate

object FestivalEdition {
    val year = 2026
    val editionNumber = 45
    val days: List<LocalDate> =
        listOf(
            LocalDate(2026, 9, 5),
            LocalDate(2026, 9, 6),
            LocalDate(2026, 9, 7),
        )
}
