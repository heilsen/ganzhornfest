package de.heilsen.ganzhornfest.detail

import de.heilsen.ganzhornfest.map.MarkerUiType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.persistentSetOf

class DetailHighlightKtTest :
    StringSpec({
        "club detail highlights the club title" {
            val model =
                DetailModel.Success(
                    title = "Sängerbund",
                    target = DetailTarget.Club(1),
                    items = listOf(DetailItem("Bier", target = DetailTarget.Offer(10))),
                )
            model.highlightTitles() shouldBe persistentSetOf("Sängerbund")
        }

        "poi detail highlights the poi title" {
            val model =
                DetailModel.Success(
                    title = "WC",
                    target = DetailTarget.Poi(2),
                    items = listOf(DetailItem("WC", target = DetailTarget.Category(MarkerUiType.WC))),
                )
            model.highlightTitles() shouldBe persistentSetOf("WC")
        }

        "offer detail highlights every club in the list" {
            val model =
                DetailModel.Success(
                    title = "Bier",
                    target = DetailTarget.Offer(10),
                    items =
                        listOf(
                            DetailItem("Sängerbund", target = DetailTarget.Club(1)),
                            DetailItem("Sportverein", target = DetailTarget.Club(2)),
                        ),
                )
            model.highlightTitles() shouldBe persistentSetOf("Sängerbund", "Sportverein")
        }

        "poi category detail highlights every poi in the list" {
            val model =
                DetailModel.Success(
                    title = "Attraktion",
                    target = DetailTarget.Category(MarkerUiType.ATTRACTION),
                    items =
                        listOf(
                            DetailItem("Karussell", target = DetailTarget.Poi(20)),
                            DetailItem("Blumentombola", target = DetailTarget.Poi(21)),
                        ),
                )
            model.highlightTitles() shouldBe persistentSetOf("Karussell", "Blumentombola")
        }
    })
