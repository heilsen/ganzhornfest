package de.heilsen.ganzhornfest.core

import kotlinx.datetime.LocalDate

fun interface SelectDefaultDateUseCase {
    operator fun invoke(): LocalDate
}
