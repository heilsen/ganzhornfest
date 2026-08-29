package de.heilsen.ganzhornfest.core

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class FestivalEditionTest :
    DescribeSpec({
        describe("FestivalEdition") {
            it("has one day per opening-hours string used by Info's DateChipRow") {
                // DateChipRow zips FestivalEdition.days against a fixed list of three
                // opening_hours_* strings. A day added here without a matching string
                // added there truncates the date chips instead of crashing. This test
                // is the tripwire for that mismatch.
                FestivalEdition.days.size shouldBe 3
            }
        }
    })
