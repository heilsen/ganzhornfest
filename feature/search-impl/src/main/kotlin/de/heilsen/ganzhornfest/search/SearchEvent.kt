package de.heilsen.ganzhornfest.search

sealed interface SearchEvent {
    data class Search(
        val query: String,
    ) : SearchEvent

    data class ToggleCategory(
        val category: Category,
    ) : SearchEvent

    data object Clear : SearchEvent
}
