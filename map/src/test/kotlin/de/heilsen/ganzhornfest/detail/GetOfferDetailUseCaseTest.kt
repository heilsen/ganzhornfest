package de.heilsen.ganzhornfest.detail

import app.cash.turbine.test
import de.heilsen.ganzhornfest.club.data.ClubRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf

class GetOfferDetailUseCaseTest : StringSpec({
    "returns clubs without a map model" {
        val clubRepository =
            mockk<ClubRepository> {
                every { getClubsByOffer("Bier") } returns
                    flowOf(listOf("Sportverein", "Sängerbund"))
            }
        val useCase = GetOfferDetailUseCase(clubRepository)

        useCase("Bier").test {
            val item = awaitItem()
            item.title shouldBe "Bier"
            item.type shouldBe DetailType.Offer
            item.items.map { it.name } shouldBe listOf("Sängerbund", "Sportverein")
            awaitComplete()
        }
    }
})
