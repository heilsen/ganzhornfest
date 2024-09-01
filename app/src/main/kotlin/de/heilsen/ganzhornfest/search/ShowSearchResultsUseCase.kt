package de.heilsen.ganzhornfest.search

import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.core.ConfigurationProvider
import de.heilsen.ganzhornfest.drink.data.DrinkRepository
import de.heilsen.ganzhornfest.food.data.FoodRepository
import de.heilsen.ganzhornfest.poi.PoiRepository
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShowSearchResultsUseCase @Inject constructor(
    private val foodRepository: FoodRepository,
    private val drinkRepository: DrinkRepository,
    private val poiRepository: PoiRepository,
    private val configurationProvider: ConfigurationProvider
) {
    operator fun invoke(
        searchTerm: String,
        category: Category
    ): Flow<PersistentList<SearchModel.Result>> {
        println("searchTerm: $searchTerm")
        println("category: $category")
        val resultFlow = when (category) {
            Category.Food -> {
                if (searchTerm.isEmpty()) {
                    foodRepository.getAll()
                } else {
                    foodRepository.selectByName(searchTerm)
                }.map { list ->
                    list.map { item ->
                        SearchModel.Result(
                            item.name,
                            item.description ?: ""
                        )
                    }
                }
            }

            Category.Drink -> {
                if (searchTerm.isEmpty()) {
                    drinkRepository.getAll()
                } else {
                    drinkRepository.selectByName(searchTerm)
                }.map { list ->
                    list.map { item ->
                        SearchModel.Result(
                            item.name,
                            item.description ?: ""
                        )
                    }
                }
            }

            Category.Club -> {
                if (searchTerm.isEmpty()) {
                    poiRepository.getAll()
                } else {
                    poiRepository.selectByName(searchTerm)
                }.map { list -> list.map { item -> SearchModel.Result(item.name, "") } }
            }
        }


        return resultFlow.map { resultList ->
            resultList
                .sortedBy { result -> result.header.lowercase(configurationProvider.getLocale()) }
                .toPersistentList()
        }
    }
}