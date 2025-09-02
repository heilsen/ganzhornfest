package de.heilsen.ganzhornfest.search

import de.heilsen.ganzhornfest.core.ConfigurationProvider
import de.heilsen.ganzhornfest.database.Offer
import de.heilsen.ganzhornfest.database.Poi
import de.heilsen.ganzhornfest.drink.data.DrinkRepository
import de.heilsen.ganzhornfest.food.data.FoodRepository
import de.heilsen.ganzhornfest.poi.PoiRepository
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf
import java.util.Locale

class ShowSearchResultsUseCaseTest : DescribeSpec({
    val foodRepository = mockk<FoodRepository> {
        every { FoodRepository.getAll() } returns flowOf(
            listOf(
                Offer(1, 0, "eins", null),
                Offer(2, 0, "zwei", "ein Essen")
            )
        )
        every { FoodRepository.selectByName(any()) } returns flowOf(listOf(Offer(1, 0, "eins", null)))
    }
    val drinkRepository = mockk<DrinkRepository> {
        every { DrinkRepository.getAll() } returns flowOf(
            listOf(
                Offer(1, 1, "eins", null),
                Offer(2, 1, "zwei", "ein alkoholisches Getränk")
            )
        )
        every { DrinkRepository.selectByName(any()) } returns flowOf(
            listOf(
                Offer(2, 1, "zwei", "ein alkoholisches Getränk")
            )
        )
    }
    val poiRepository = mockk<PoiRepository> {
        every { PoiRepository.getAll() } returns flowOf(listOf(Poi(1, "eins", 0), Poi(2, "zwei", 0)))
        every { PoiRepository.selectByName(any()) } returns flowOf(listOf(Poi(1, "eins", 0)))
    }
    val configurationProvider: ConfigurationProvider = mockk {
        every { ConfigurationProvider.getLocale() } returns Locale.GERMAN
    }
    val showSearchResults = ShowSearchResultsUseCase(
        foodRepository,
        drinkRepository,
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