package de.heilsen.ganzhornfest.map

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PinActionTest :
    StringSpec({
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
