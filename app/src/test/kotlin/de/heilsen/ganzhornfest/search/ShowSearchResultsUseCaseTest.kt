package de.heilsen.ganzhornfest.search

import app.cash.turbine.test
import de.heilsen.ganzhornfest.database.Club
import de.heilsen.ganzhornfest.database.Drink
import de.heilsen.ganzhornfest.database.Food
import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.core.ConfigurationProvider
import de.heilsen.ganzhornfest.drink.data.DrinkRepository
import de.heilsen.ganzhornfest.food.data.FoodRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.flowOf
import java.util.Locale

class ShowSearchResultsUseCaseTest : DescribeSpec({
    val foodRepository = mockk<FoodRepository> {
        every { getAll() } returns flowOf(
            listOf(
                Food(1, "eins", null),
                Food(2, "zwei", "ein Essen")
            )
        )
        every { selectByName(any()) } returns flowOf(listOf(Food(1, "eins", null)))
    }
    val drinkRepository = mockk<DrinkRepository> {
        every { getAll() } returns flowOf(
            listOf(
                Drink(1, "eins", null, 0),
                Drink(2, "zwei", "ein alkholisches Getränk", 1)
            )
        )
        every { selectByName(any()) } returns flowOf(
            listOf(
                Drink(2, "zwei", "ein alkholisches Getränk", 1)
            )
        )
    }
    val clubRepository = mockk<ClubRepository> {
        every { getAll() } returns flowOf(listOf(Club(1, "eins"), Club(2, "zwei")))
        every { selectByName(any()) } returns flowOf(listOf(Club(1, "eins")))
    }
    val configurationProvider: ConfigurationProvider = mockk {
        every { getLocale() } returns Locale.GERMAN
    }
    val showSearchResults = ShowSearchResultsUseCase(
        foodRepository,
        drinkRepository,
        clubRepository,
        configurationProvider
    )


    describe("showSearchResults") {
        it("with no-arg") {
            showSearchResults().test {
                awaitItem() shouldBe persistentListOf(
                    "eins", "eins", "eins", "zwei", "zwei", "zwei"
                )
                awaitComplete()
            }
        }
        it("with query") {
            showSearchResults("foobar").test {
                awaitItem() shouldBe persistentListOf(
                    "eins", "eins", "zwei"
                )
                awaitComplete()
            }
        }
        it("with query") {
            showSearchResults("foobar", setOf(Category.Club)).test {
                awaitItem() shouldBe persistentListOf("eins")
                awaitComplete()
            }
        }
    }
})
