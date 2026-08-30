package de.heilsen.ganzhornfest.detail

import app.cash.turbine.test
import de.heilsen.ganzhornfest.club.data.ClubRepository
import de.heilsen.ganzhornfest.club.data.ClubRow
import de.heilsen.ganzhornfest.offer.data.OfferRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class GetOfferDetailUseCaseTest :
    StringSpec({
        "returns clubs without a map model" {
            val clubRepository =
                mockk<ClubRepository> {
                    every { getClubsByOffer(5) } returns
                        flowOf(
                            listOf(
                                ClubRow(poiId = 2, name = "Sportverein"),
                                ClubRow(poiId = 1, name = "Sängerbund"),
                            ),
                        )
                }
            val offerRepository =
                mockk<OfferRepository> {
                    every { getOfferName(5) } returns flowOf("Bier")
                }
            val useCase = GetOfferDetailUseCase(clubRepository, offerRepository)

            useCase(5).test {
                val item = awaitItem()
                item.title shouldBe "Bier"
                item.target shouldBe DetailTarget.Offer(5)
                item.items shouldBe
                    listOf(
                        DetailItem("Sängerbund", target = DetailTarget.Club(1)),
                        DetailItem("Sportverein", target = DetailTarget.Club(2)),
                    )
                awaitComplete()
            }
        }
    })
