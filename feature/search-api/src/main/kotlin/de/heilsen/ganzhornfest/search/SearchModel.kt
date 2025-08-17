package de.heilsen.ganzhornfest.search

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList

@Immutable
sealed interface SearchModel {
    data class Data(
        val query: String,
        val categories: ImmutableList<Category>,
        val selectedCategory: Category,
        val results: PersistentList<Result>
    ): SearchModel
    data object Loading: SearchModel

    data class Result(
        val header: String, val description: String
    )
}