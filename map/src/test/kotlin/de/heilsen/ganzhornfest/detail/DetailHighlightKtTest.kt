package de.heilsen.ganzhornfest.detail

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentSetOf

class DetailHighlightKtTest : StringSpec({
    "club detail highlights the club title" {
        val model =
            DetailModel.Success(
                title = "Sängerbund",
                type = DetailType.Club,
                items = listOf(DetailItem("Bier")),
            )
        model.highlightTitles() shouldBe persistentSetOf("Sängerbund")
    }

    "offer detail highlights every club in the list" {
        val model =
            DetailModel.Success(
                title = "Bier",
                type = DetailType.Offer,
                items = listOf(DetailItem("Sängerbund"), DetailItem("Sportverein")),
            )
        model.highlightTitles() shouldBe persistentSetOf("Sängerbund", "Sportverein")
    }
})
