package de.heilsen.ganzhornfest.search

import de.heilsen.ganzhornfest.core.ConfigurationProvider
import de.heilsen.ganzhornfest.offer.data.OfferRepository
import de.heilsen.ganzhornfest.poi.PoiRepository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import dev.zacsweers.metro.Inject

@ContributesBinding(AppScope::class)
class ShowSearchResultsUseCaseImpl @Inject constructor(
    private val offerRepository: OfferRepository,
    private val poiRepository: PoiRepository,
    private val configurationProvider: ConfigurationProvider
): ShowSearchResultsUseCase {
    override operator fun invoke(
        searchTerm: String,
        category: Category
    ): Flow<PersistentList<SearchModel.Result>> {
        Timber.tag("ShowSearchResults").i("searchTerm: $searchTerm")
        Timber.tag("ShowSearchResults").i("category: $category")
        val resultFlow = when (category) {
            Category.Food -> {
                if (searchTerm.isEmpty()) {
                    offerRepository.getAllFood()
                } else {
                    offerRepository.selectFoodByName(searchTerm)
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
                    offerRepository.getAllDrinks()
                } else {
                    offerRepository.selectDrinkByName(searchTerm)
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