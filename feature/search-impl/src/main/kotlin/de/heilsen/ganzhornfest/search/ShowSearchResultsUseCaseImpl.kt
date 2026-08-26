package de.heilsen.ganzhornfest.search

import de.heilsen.ganzhornfest.core.ConfigurationProvider
import de.heilsen.ganzhornfest.core.germanAlphaComparator
import de.heilsen.ganzhornfest.offer.data.OfferRepository
import de.heilsen.ganzhornfest.poi.PoiRepository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import timber.log.Timber

@ContributesBinding(AppScope::class)
class ShowSearchResultsUseCaseImpl
    @Inject
    constructor(
        private val offerRepository: OfferRepository,
        private val poiRepository: PoiRepository,
        private val configurationProvider: ConfigurationProvider,
    ) : ShowSearchResultsUseCase {
        override operator fun invoke(
            searchTerm: String,
            categories: PersistentSet<Category>,
        ): Flow<PersistentList<SearchModel.Result>> {
            Timber.tag("ShowSearchResults").i("searchTerm: $searchTerm")
            Timber.tag("ShowSearchResults").i("categories: $categories")

            if (categories.isEmpty()) return flowOf(persistentListOf())

            return combine(categories.map { category -> resultsForCategory(searchTerm, category) }) { resultsByCategory ->
                resultsByCategory
                    .flatMap { it }
                    .sortedWith(compareBy(germanAlphaComparator(), SearchModel.Result::header))
                    .toPersistentList()
            }
        }

        private fun resultsForCategory(
            searchTerm: String,
            category: Category,
        ): Flow<List<SearchModel.Result>> =
            when (category) {
                Category.Food -> {
                    if (searchTerm.isEmpty()) {
                        offerRepository.getAllFood()
                    } else {
                        offerRepository.selectFoodByName(searchTerm)
                    }.map { list ->
                        list.map { item ->
                            SearchModel.Result(
                                item.name,
                                item.description ?: "",
                                Category.Food,
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
                                item.description ?: "",
                                Category.Drink,
                            )
                        }
                    }
                }

                Category.Club -> {
                    if (searchTerm.isEmpty()) {
                        poiRepository.getAll()
                    } else {
                        poiRepository.selectByName(searchTerm)
                    }.map { list -> list.map { item -> SearchModel.Result(item.name, "", Category.Club) } }
                }
            }
    }
