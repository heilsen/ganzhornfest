package de.heilsen.ganzhornfest.countdown

sealed interface CountdownModel {
    data class Before(
        val days: Long,
        val hours: Long,
        val minutes: Long,
        val seconds: Long,
    ) : CountdownModel

    data object During : CountdownModel
    data object After : CountdownModel
}
