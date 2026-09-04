package de.heilsen.ganzhornfest.detail

import app.cash.turbine.test
import de.heilsen.ganzhornfest.club.data.ClubOfferRow
import de.heilsen.ganzhornfest.club.data.ClubRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class GetClubDetailUseCaseTest :
    StringSpec({
        "returns offers without a map model" {
            val clubRepository =
                mockk<ClubRepository> {
                    every { getClubName(1) } returns flowOf("Sängerbund")
                    every { getOffersByClub(1) } returns
                        flowOf(
                            listOf(
                                ClubOfferRow(offerId = 10, name = "Bier", description = "vom Fass"),
                                ClubOfferRow(offerId = 11, name = "Cola", description = null),
                            ),
                        )
                }
            val useCase = GetClubDetailUseCase(clubRepository)

            useCase(1).test {
                awaitItem() shouldBe
                    DetailModel.Success(
                        title = "Sängerbund",
                        target = DetailTarget.Club(1),
                        items =
                            listOf(
                                DetailItem("Bier", "vom Fass", DetailTarget.Offer(10)),
                                DetailItem("Cola", null, DetailTarget.Offer(11)),
                            ),
                    )
                awaitComplete()
            }
        }
    })
