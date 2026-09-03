package de.heilsen.ganzhornfest.search

import de.heilsen.ganzhornfest.core.ConfigurationProvider
import de.heilsen.ganzhornfest.core.germanAlphaComparator
import de.heilsen.ganzhornfest.database.OfferAlias
import de.heilsen.ganzhornfest.offer.data.OfferRepository
import de.heilsen.ganzhornfest.offer.data.OfferSearchResult
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
import java.util.Locale

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
            if (categories.isEmpty()) return flowOf(persistentListOf())

            val locale = configurationProvider.getLocale()
            val queryTokens = GermanQueryNormalizer.normalize(searchTerm).normalizedForSearch(locale).tokens()
            val aliasesFlow = offerRepository.getAliases()

            return combine(
                categories.map { category -> resultsForCategory(category, queryTokens, locale, aliasesFlow) },
            ) { resultsByCategory ->
                resultsByCategory
                    .flatMap { it }
                    .sortedWith(compareBy(germanAlphaComparator(), SearchModel.Result::header))
                    .toPersistentList()
            }
        }

        private fun resultsForCategory(
            category: Category,
            queryTokens: List<String>,
            locale: Locale,
            aliasesFlow: Flow<List<OfferAlias>>,
        ): Flow<List<SearchModel.Result>> =
            when (category) {
                Category.Food ->
                    combine(offerRepository.getAllFood(), aliasesFlow) { list, aliases ->
                        val aliasesByOffer = aliases.groupBy({ it.offerId }, { it.alias })
                        list
                            .filter { item -> item.matches(queryTokens, locale, aliasesByOffer[item.id].orEmpty()) }
                            .map { item ->
                                SearchModel.Result(item.name, item.description ?: "", Category.Food, item.clubs)
                            }
                    }

                Category.Drink ->
                    combine(offerRepository.getAllDrinks(), aliasesFlow) { list, aliases ->
                        val aliasesByOffer = aliases.groupBy({ it.offerId }, { it.alias })
                        list
                            .filter { item -> item.matches(queryTokens, locale, aliasesByOffer[item.id].orEmpty()) }
                            .map { item ->
                                SearchModel.Result(item.name, item.description ?: "", Category.Drink, item.clubs)
                            }
                    }

                Category.Club ->
                    poiRepository.getAll().map { list ->
                        list
                            .filter { item -> queryTokens.all { token -> item.name.matchesSearch(token, locale) } }
                            .map { item -> SearchModel.Result(item.name, "", Category.Club) }
                    }
            }

        // Each word of the query is matched independently, so "sun tennis" also finds
        // "Sport-Union Neckarsulm - Tischtennis": "sun" via its acronym, "tennis" by substring.
        private fun OfferSearchResult.matches(
            queryTokens: List<String>,
            locale: Locale,
            aliases: List<String>,
        ): Boolean =
            queryTokens.all { token ->
                name.matchesSearch(token, locale) ||
                    description?.matchesSearch(token, locale) == true ||
                    aliases.any { alias -> alias.matchesSearch(token, locale) }
            }
    }

// Folds German umlauts and ß to their ASCII digraph so "u"/"ue" also match "ü" and so on.
// SQLite's own LOWER()/LIKE only case-fold ASCII, which is why this runs in Kotlin instead.
private fun String.normalizedForSearch(locale: Locale): String =
    lowercase(locale)
        .replace("ü", "ue")
        .replace("ö", "oe")
        .replace("ä", "ae")
        .replace("ß", "ss")

private fun String.tokens(): List<String> = split(Regex("\\s+")).filter { it.isNotEmpty() }

// Matches by substring or by the initials of each word, so "ASB" also finds
// "Arbeiter-Samariter-Bund".
private fun String.matchesSearch(
    normalizedTerm: String,
    locale: Locale,
): Boolean {
    val normalized = normalizedForSearch(locale)
    return normalized.contains(normalizedTerm) || normalized.initials().startsWith(normalizedTerm)
}

// German club names are conventionally compounded onto "verein" without a separator
// (Sportverein, Förderverein, Ortsverein), so that suffix counts as its own word too.
private fun String.initials(): String =
    replace(Regex("(?<=\\p{L})verein"), " verein")
        .split(Regex("[^\\p{L}]+"))
        .filter { it.isNotEmpty() }
        .joinToString("") { word -> word.first().toString() }
