package de.heilsen.ganzhornfest.program

import kotlinx.datetime.LocalDateTime

data class Program(
    val name: String,
    val description: String?,
    val start: LocalDateTime,
    val end: LocalDateTime?,
    val stage: String
) {
    val isOpenEnd: Boolean = end != null
    val hasDescription: Boolean = description != null
    val key: String = name+start
}