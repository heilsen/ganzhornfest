package de.heilsen.ganzhornfest.search

import app.cash.turbine.test
import de.heilsen.ganzhornfest.core.ConfigurationProvider
import de.heilsen.ganzhornfest.database.OfferAlias
import de.heilsen.ganzhornfest.database.Poi
import de.heilsen.ganzhornfest.offer.data.OfferRepository
import de.heilsen.ganzhornfest.offer.data.OfferSearchResult
import de.heilsen.ganzhornfest.poi.PoiRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.flowOf
import timber.log.Timber
import java.util.Locale

class ShowSearchResultsUseCaseTest :
    DescribeSpec({
        val offerRepository =
            mockk<OfferRepository> {
                every { getAllFood() } returns
                    flowOf(
                        listOf(
                            OfferSearchResult(1, "Apfelküchle", null, ""),
                            OfferSearchResult(2, "Pommes", "mit Mayo", ""),
                        ),
                    )
                every { getAllDrinks() } returns
                    flowOf(
                        listOf(
                            OfferSearchResult(1, "Weißbier", null, ""),
                            OfferSearchResult(2, "Cola", "ein alkoholfreies Getränk", ""),
                        ),
                    )
                every { getAliases() } returns flowOf(emptyList())
            }
        val poiRepository =
            mockk<PoiRepository> {
                every { getAll() } returns
                    flowOf(
                        listOf(
                            Poi(1, "Sängerbund", 0),
                            Poi(2, "Sportverein", 0),
                            Poi(3, "Arbeiter-Samariter-Bund", 0),
                            Poi(4, "Förderverein der Feuerwehr NSU", 0),
                            Poi(5, "Sport-Union Neckarsulm - Tischtennis", 0),
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
                            SearchModel.Result("Sport-Union Neckarsulm - Tischtennis", "", Category.Club),
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
                            SearchModel.Result("Förderverein der Feuerwehr NSU", "", Category.Club),
                            SearchModel.Result("Pommes", "mit Mayo", Category.Food),
                            SearchModel.Result("Sängerbund", "", Category.Club),
                            SearchModel.Result("Sport-Union Neckarsulm - Tischtennis", "", Category.Club),
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
            it("matches a club by the initials of a name compounded onto 'verein'") {
                showSearchResults("FV", persistentSetOf(Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Förderverein der Feuerwehr NSU", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
            it("matches each word of a multi-word query independently, by acronym or substring") {
                showSearchResults("sun tennis", persistentSetOf(Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Sport-Union Neckarsulm - Tischtennis", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
            it("matches a multi-word query across an offer's name and description") {
                showSearchResults("cola alkoholfrei", persistentSetOf(Category.Drink)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Cola", "ein alkoholfreies Getränk", Category.Drink),
                        )
                    awaitComplete()
                }
            }
            it("matches an offer via a German synonym alias") {
                val aliasOfferRepository =
                    mockk<OfferRepository> {
                        every { getAllFood() } returns
                            flowOf(listOf(OfferSearchResult(105, "Bratwurst", null, "ASB")))
                        every { getAliases() } returns
                            flowOf(listOf(OfferAlias(1, 105, "Grillwurst")))
                    }
                val aliasShowSearchResults: ShowSearchResultsUseCase =
                    ShowSearchResultsUseCaseImpl(
                        aliasOfferRepository,
                        poiRepository,
                        configurationProvider,
                    )
                aliasShowSearchResults("Grillwurst", persistentSetOf(Category.Food)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Bratwurst", "", Category.Food, "ASB"),
                        )
                    awaitComplete()
                }
            }
            it("strips spoken German phrasing before matching an alias") {
                val aliasOfferRepository =
                    mockk<OfferRepository> {
                        every { getAllFood() } returns
                            flowOf(listOf(OfferSearchResult(105, "Bratwurst", null, "ASB")))
                        every { getAliases() } returns
                            flowOf(listOf(OfferAlias(1, 105, "Grillwurst")))
                    }
                val aliasShowSearchResults: ShowSearchResultsUseCase =
                    ShowSearchResultsUseCaseImpl(
                        aliasOfferRepository,
                        poiRepository,
                        configurationProvider,
                    )
                aliasShowSearchResults("Wo gibt es eine Grillwurst?", persistentSetOf(Category.Food)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("Bratwurst", "", Category.Food, "ASB"),
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

            // The search term is user input and can arrive from voice dictation. Timber trees are
            // process global, so anything logged here reaches every planted tree, release ones
            // included. This asserts the term reaches no tree at all rather than checking the
            // shape of the code, so it still fires if the logging comes back somewhere else on
            // the flow's construction path.
            it("never logs the search term") {
                val messages = mutableListOf<String>()
                val recordingTree =
                    object : Timber.Tree() {
                        override fun log(
                            priority: Int,
                            tag: String?,
                            message: String,
                            t: Throwable?,
                        ) {
                            messages += message
                        }
                    }
                Timber.plant(recordingTree)
                try {
                    showSearchResults("Apfelküchle", persistentSetOf(Category.Food)).test {
                        awaitItem()
                        awaitComplete()
                    }
                } finally {
                    Timber.uproot(recordingTree)
                }
                messages.any { it.contains("Apfelküchle") } shouldBe false
            }
        }
    })
