package de.heilsen.ganzhornfest.search

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet

@Immutable
sealed interface SearchModel {
    data class Data(
        val query: String,
        val categories: ImmutableList<Category>,
        val selectedCategories: PersistentSet<Category>,
        val results: PersistentList<Result>,
        val expanded: Boolean = false,
    ) : SearchModel

    data object Loading : SearchModel

    data class Result(
        val header: String,
        val description: String,
        val category: Category,
        val clubs: String = "",
    )
}
