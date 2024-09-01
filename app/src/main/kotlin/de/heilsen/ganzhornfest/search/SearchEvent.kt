package de.heilsen.ganzhornfest.search

sealed interface SearchEvent {
    data class Search(val query: String) : SearchEvent
    data class ChangeCategory(val category: Category) : SearchEvent
    data object Clear : SearchEvent
}