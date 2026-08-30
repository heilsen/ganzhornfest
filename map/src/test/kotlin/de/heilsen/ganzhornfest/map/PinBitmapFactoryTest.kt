package de.heilsen.ganzhornfest.map

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.maps.shouldContainExactly
import io.kotest.matchers.shouldBe

class PinBitmapFactoryTest :
    StringSpec({
        "maps each marker type to the semantic festival hue" {
            val expected =
                mapOf(
                    MarkerUiType.CLUB to 32f,
                    MarkerUiType.EVENT_LOCATION to 275f,
                    MarkerUiType.PLAYGROUND to 330f,
                    MarkerUiType.ATTRACTION to 55f,
                    MarkerUiType.WC to 180f,
                    MarkerUiType.FIRST_AID to 0f,
                    MarkerUiType.BUS_STOP to 220f,
                )

            val actual = MarkerUiType.entries.associateWith(PinBitmapFactory::hueFor)

            actual shouldContainExactly expected
            expected.keys shouldBe MarkerUiType.entries.toSet()
        }
    })
