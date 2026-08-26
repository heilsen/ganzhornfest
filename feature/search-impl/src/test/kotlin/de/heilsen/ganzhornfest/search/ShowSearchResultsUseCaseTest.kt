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
                            Offer(1, 0, "eins", null),
                            Offer(2, 0, "zwei", "ein Essen"),
                        ),
                    )
                every { selectFoodByName(any()) } returns flowOf(listOf(Offer(1, 0, "eins", null)))
                every { getAllDrinks() } returns
                    flowOf(
                        listOf(
                            Offer(1, 1, "eins", null),
                            Offer(2, 1, "zwei", "ein alkoholisches Getränk"),
                        ),
                    )
                every { selectDrinkByName(any()) } returns
                    flowOf(
                        listOf(
                            Offer(2, 1, "zwei", "ein alkoholisches Getränk"),
                        ),
                    )
            }
        val poiRepository =
            mockk<PoiRepository> {
                every { getAll() } returns flowOf(listOf(Poi(1, "eins", 0), Poi(2, "zwei", 0)))
                every { selectByName(any()) } returns flowOf(listOf(Poi(1, "eins", 0)))
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
                            SearchModel.Result("eins", "", Category.Food),
                            SearchModel.Result("zwei", "ein Essen", Category.Food),
                        )
                    awaitComplete()
                }
            }
            it("returns filtered drinks when category is Drink and query is not empty") {
                showSearchResults("foobar", persistentSetOf(Category.Drink)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("zwei", "ein alkoholisches Getränk", Category.Drink),
                        )
                    awaitComplete()
                }
            }
            it("returns filtered clubs when category is Club and query is not empty") {
                showSearchResults("foobar", persistentSetOf(Category.Club)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("eins", "", Category.Club),
                        )
                    awaitComplete()
                }
            }
            it("merges and sorts results across multiple selected categories") {
                showSearchResults("", persistentSetOf(Category.Food, Category.Drink)).test {
                    awaitItem() shouldBe
                        persistentListOf(
                            SearchModel.Result("eins", "", Category.Food),
                            SearchModel.Result("eins", "", Category.Drink),
                            SearchModel.Result("zwei", "ein Essen", Category.Food),
                            SearchModel.Result("zwei", "ein alkoholisches Getränk", Category.Drink),
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
