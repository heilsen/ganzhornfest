package de.heilsen.ganzhornfest.search

import app.cash.turbine.test
import de.heilsen.ganzhornfest.core.ConfigurationProvider
import de.heilsen.ganzhornfest.database.Offer
import de.heilsen.ganzhornfest.database.Poi
import de.heilsen.ganzhornfest.offer.data.OfferRepository
import de.heilsen.ganzhornfest.poi.PoiRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.flowOf
import java.util.Locale

class ShowSearchResultsUseCaseTest :
    DescribeSpec({
        val offerRepository =
            mockk<OfferRepository> {
                every { getAllFood() } returns
                    flowOf(
                        listOf(
                            Offer(1, 0, "Apfelküchle", null),
                            Offer(2, 0, "Pommes", "mit Mayo"),
                        ),
                    )
                every { getAllDrinks() } returns
                    flowOf(
                        listOf(
                            Offer(1, 1, "Weißbier", null),
                            Offer(2, 1, "Cola", "ein alkoholfreies Getränk"),
                        ),
                    )
            }
        val poiRepository =
            mockk<PoiRepository> {
                every { getAll() } returns
                    flowOf(
                        listOf(
                            Poi(1, "Sängerbund", 0),
                            Poi(2, "Sportverein", 0),
                            Poi(3, "Arbeiter-Samariter-Bund", 0),
                        ),
                    )
            }
        val configurationProvider: ConfigurationProvider =
            mockk {
                every { getLocale() } returns Locale.GERMAN
            }
        val showSearchResults: ShowSearchResultsUseCase =
            ShowSearchResultsUseCaseImpl(
                offerRepository,
                poiRepository,
                configurationProvider,
            )

        describe("showSearchResults") {
            it("returns an empty list when no category is selected") {
                showSearchResults("", persistentSetOf()).test {
                    awaitItem() shouldBe persistentListOf()
                    awaitComplete()
                }
            }
            it("returns food when category is Food and query is empty") {
                showSearchResults("", persistentSetOf(Category.Food)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Apfelküchle", "", Category.Food),
                            SearchModel.Result("Pommes", "mit Mayo", Category.Food),
                        )
                    awaitComplete()
                }
            }
            it("returns filtered drinks when category is Drink and query matches the description") {
                showSearchResults("alkoholfrei", persistentSetOf(Category.Drink)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Cola", "ein alkoholfreies Getränk", Category.Drink),
                        )
                    awaitComplete()
                }
            }
            it("returns filtered clubs when category is Club and query matches the name") {
                showSearchResults("sport", persistentSetOf(Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Sportverein", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
            it("merges and sorts results across multiple selected categories") {
                showSearchResults("", persistentSetOf(Category.Food, Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Apfelküchle", "", Category.Food),
                            SearchModel.Result("Arbeiter-Samariter-Bund", "", Category.Club),
                            SearchModel.Result("Pommes", "mit Mayo", Category.Food),
                            SearchModel.Result("Sängerbund", "", Category.Club),
                            SearchModel.Result("Sportverein", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
            it("matches an umlaut word when searching its ASCII digraph 'ue'") {
                showSearchResults("ue", persistentSetOf(Category.Food)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Apfelküchle", "", Category.Food),
                        )
                    awaitComplete()
                }
            }
            it("matches an umlaut word when searching the bare vowel 'u'") {
                showSearchResults("u", persistentSetOf(Category.Food)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Apfelküchle", "", Category.Food),
                        )
                    awaitComplete()
                }
            }
            it("matches an umlaut word when searching the umlaut character 'ü' itself") {
                showSearchResults("ü", persistentSetOf(Category.Food)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Apfelküchle", "", Category.Food),
                        )
                    awaitComplete()
                }
            }
            it("folds ß to 'ss' so 'weiss' matches 'Weißbier'") {
                showSearchResults("weiss", persistentSetOf(Category.Drink)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Weißbier", "", Category.Drink),
                        )
                    awaitComplete()
                }
            }
            it("folds ä to 'ae' so 'saenger' matches 'Sängerbund'") {
                showSearchResults("saenger", persistentSetOf(Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Sängerbund", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
            it("matches a club by the initials of its hyphenated name") {
                showSearchResults("ASB", persistentSetOf(Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Arbeiter-Samariter-Bund", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
            it("matches a club by a partial acronym while typing") {
                showSearchResults("AS", persistentSetOf(Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Arbeiter-Samariter-Bund", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
            it("sorts ae umlaut as a") {
                val umlautPoiRepository =
                    mockk<PoiRepository> {
                        every { getAll() } returns
                            flowOf(
                                listOf(
                                    Poi(1, "SC Amorbach", 0),
                                    Poi(2, "Sängerbund 1830", 0),
                                    Poi(3, "Samstagsverein", 0),
                                ),
                            )
                    }
                val umlautShowSearchResults: ShowSearchResultsUseCase =
                    ShowSearchResultsUseCaseImpl(
                        offerRepository,
                        umlautPoiRepository,
                        configurationProvider,
                    )
                umlautShowSearchResults("", persistentSetOf(Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Samstagsverein", "", Category.Club),
                            SearchModel.Result("Sängerbund 1830", "", Category.Club),
                            SearchModel.Result("SC Amorbach", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
        }
    })
