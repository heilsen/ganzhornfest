package de.heilsen.ganzhornfest.navigation

import de.heilsen.ganzhornfest.detail.DetailTarget
import de.heilsen.ganzhornfest.map.MarkerUiType
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class DetailRoutesTest :
    StringSpec({
        val targets =
            listOf(
                DetailTarget.Club(poiId = 7),
                DetailTarget.Offer(offerId = 42),
                DetailTarget.Poi(poiId = 13),
                DetailTarget.Category(type = MarkerUiType.WC),
            )

        "every DetailTarget round trips through its Destination" {
            targets.forEach { target ->
                val restored =
                    when (val destination = target.toDestination()) {
                        is Destination.Detail -> destination.toTarget()
                        is Destination.CategoryDetail -> destination.toTarget()
                        else -> error("unexpected destination $destination")
                    }
                restored shouldBe target
            }
        }
    })
