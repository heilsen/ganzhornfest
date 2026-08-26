package de.heilsen.ganzhornfest.detail

import app.cash.turbine.test
import de.heilsen.ganzhornfest.club.data.ClubRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class GetClubDetailUseCaseTest : StringSpec({
    "returns offers without a map model" {
        val clubRepository =
            mockk<ClubRepository> {
                every { getOffersByClub("Sängerbund") } returns
                    flowOf(listOf("Bier" to "vom Fass", "Cola" to null))
            }
        val useCase = GetClubDetailUseCase(clubRepository)

        useCase("Sängerbund").test {
            awaitItem() shouldBe
                DetailModel.Success(
                    title = "Sängerbund",
                    type = DetailType.Club,
                    items =
                        listOf(
                            DetailItem("Bier", "vom Fass"),
                            DetailItem("Cola", null),
                        ),
                )
            awaitComplete()
        }
    }
})
