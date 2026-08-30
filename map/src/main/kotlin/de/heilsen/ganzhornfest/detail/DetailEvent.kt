package de.heilsen.ganzhornfest.detail

sealed interface DetailEvent {
    data class Open(
        val target: DetailTarget,
    ) : DetailEvent

    data object Init : DetailEvent
}
