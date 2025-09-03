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
import kotlinx.coroutines.flow.flowOf
import java.util.Locale

class ShowSearchResultsUseCaseTest : DescribeSpec({
    val offerRepository = mockk<OfferRepository> {
        every { getAllFood() } returns flowOf(
            listOf(
                Offer(1, 0, "eins", null),
                Offer(2, 0, "zwei", "ein Essen")
            )
        )
        every { selectFoodByName(any()) } returns flowOf(listOf(Offer(1, 0, "eins", null)))
        every { getAllDrinks() } returns flowOf(
            listOf(
                Offer(1, 1, "eins", null),
                Offer(2, 1, "zwei", "ein alkoholisches Getränk")
            )
        )
        every { selectDrinkByName(any()) } returns flowOf(
            listOf(
                Offer(2, 1, "zwei", "ein alkoholisches Getränk")
            )
        )
    }
    val poiRepository = mockk<PoiRepository> {
        every { getAll() } returns flowOf(listOf(Poi(1, "eins", 0), Poi(2, "zwei", 0)))
        every { selectByName(any()) } returns flowOf(listOf(Poi(1, "eins", 0)))
    }
    val configurationProvider: ConfigurationProvider = mockk {
        every { getLocale() } returns Locale.GERMAN
    }
    val showSearchResults: ShowSearchResultsUseCase = ShowSearchResultsUseCaseImpl(
        offerRepository,
        poiRepository,
        configurationProvider
    )

    describe("showSearchResults") {
        it("returns food when category is Food and query is empty") {
            showSearchResults("", Category.Food).test {
                awaitItem() shouldBe persistentListOf(
                    SearchModel.Result("eins", ""),
                    SearchModel.Result("zwei", "ein Essen")
                )
                awaitComplete()
            }
        }
        it("returns filtered drinks when category is Drink and query is not empty") {
            showSearchResults("foobar", Category.Drink).test {
                awaitItem() shouldBe persistentListOf(
                    SearchModel.Result("zwei", "ein alkoholisches Getränk")
                )
                awaitComplete()
            }
        }
        it("returns filtered clubs when category is Club and query is not empty") {
            showSearchResults("foobar", Category.Club).test {
                awaitItem() shouldBe persistentListOf(
                    SearchModel.Result("eins", "")
                )
                awaitComplete()
            }
        }
    }
})