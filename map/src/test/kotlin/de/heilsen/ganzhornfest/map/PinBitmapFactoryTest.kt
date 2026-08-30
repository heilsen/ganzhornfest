package de.heilsen.ganzhornfest.map

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.floats.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

// AndroidColor.HSVToColor returns 0 under unit tests (isReturnDefaultValues), so every
// assertion works on the HSV float array, never on a packed colour int.
class PinBitmapFactoryTest :
    StringSpec({
        "no resting hue sits in the arc the aerial basemap owns" {
            for (type in MarkerUiType.entries) {
                val hue = PinBitmapFactory.hsvFor(type, PinEmphasis.Default)[0]
                (hue in 85f..160f) shouldBe false
            }
        }

        "highlighting a slate WC clamps saturation to the floor" {
            PinBitmapFactory.hsvFor(MarkerUiType.WC, PinEmphasis.Highlighted)[1] shouldBe 0.75f
        }

        "highlighting a dark slate bus stop clamps value to the floor" {
            PinBitmapFactory.hsvFor(MarkerUiType.BUS_STOP, PinEmphasis.Highlighted)[2] shouldBe 0.80f
        }

        "highlighting first aid clamps saturation to the cap" {
            PinBitmapFactory.hsvFor(MarkerUiType.FIRST_AID, PinEmphasis.Highlighted)[1] shouldBe 1.0f
        }

        "every type gets strictly decreasing saturation across emphasis" {
            for (type in MarkerUiType.entries) {
                val highlighted = PinBitmapFactory.hsvFor(type, PinEmphasis.Highlighted)[1]
                val default = PinBitmapFactory.hsvFor(type, PinEmphasis.Default)[1]
                val dimmed = PinBitmapFactory.hsvFor(type, PinEmphasis.Dimmed)[1]
                highlighted shouldBeGreaterThan default
                default shouldBeGreaterThan dimmed
            }
        }
    })
