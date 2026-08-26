package de.heilsen.ganzhornfest.search

sealed interface SearchEvent {
    data class Search(
        val query: String,
    ) : SearchEvent

    data class ToggleCategory(
        val category: Category,
    ) : SearchEvent

    data object Clear : SearchEvent

    data class SetExpanded(
        val expanded: Boolean,
    ) : SearchEvent

    data object OpenResult : SearchEvent

    data object UiReady : SearchEvent
}
