package de.heilsen.ganzhornfest.map

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PinActionTest :
    StringSpec({
        "club, stage, playground, and bus are actionable" {
            MarkerUiType.CLUB.isActionable() shouldBe true
            MarkerUiType.EVENT_LOCATION.isActionable() shouldBe true
            MarkerUiType.PLAYGROUND.isActionable() shouldBe true
            MarkerUiType.BUS_STOP.isActionable() shouldBe true
        }

        "attraction, wc, and first aid are informational" {
            MarkerUiType.ATTRACTION.isActionable() shouldBe false
            MarkerUiType.WC.isActionable() shouldBe false
            MarkerUiType.FIRST_AID.isActionable() shouldBe false
        }

        "no highlight set means default emphasis" {
            resolvePinEmphasis("Sängerbund", highlightedTitles = null) shouldBe PinEmphasis.Default
            resolvePinEmphasis("Sängerbund", highlightedTitles = emptySet()) shouldBe PinEmphasis.Default
        }

        "titles in the highlight set are highlighted, others dimmed" {
            val highlighted = setOf("Sängerbund", "Sportverein")
            resolvePinEmphasis("Sängerbund", highlighted) shouldBe PinEmphasis.Highlighted
            resolvePinEmphasis("WC", highlighted) shouldBe PinEmphasis.Dimmed
        }
    })
